package com.hamidi.struts.rest;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.hamidi.struts.exception.ExceptionError;
import com.hamidi.struts.model.Todolist;
import com.hamidi.struts.repository.TodolistRepository;
import com.opensymphony.xwork2.ModelDriven;

public class TodolistController implements ModelDriven<Object> {

	public Todolist todolist = new Todolist();
	private Integer id;
	private Object todolists;
	private TodolistRepository repo = new TodolistRepository();

	public static class ErrorResponse {
		private String message;

		public ErrorResponse(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	// GET /api/todos
	public HttpHeaders index() {
		try {
			todolists = repo.listAll(); // Fetch all tasks
			System.out.println("GET \t /todos");
//			return new DefaultHttpHeaders("index"); // Return the index result
			return new DefaultHttpHeaders("index");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(500); // Internal Server Error
			todolists = new ErrorResponse("An unexpected error occurred.");
			return headers;
		}
	}

	// GET /api/todo/{id}
	public HttpHeaders show() {
		try {
			todolists = repo.getById(id);
			System.out.println("GET \t /todos/" + id);
			return new DefaultHttpHeaders("show");
		} catch (ExceptionError e) {
			System.out.println("Error: " + e.getMessage());
			DefaultHttpHeaders headers = new DefaultHttpHeaders("notFound");
			headers.setStatus(404);
			todolists = new ErrorResponse(e.getMessage());
			return headers;
		}
	}

	// POST /api/todos
	public HttpHeaders create() {
		try {
			// Correctly retrieve and parse the status from the request
			Todolist addedTask = repo.addTask(todolist.getTitle(), todolist.getDescription(), todolist.getStatus());
			System.out.println("Created task with ID: " + addedTask.getId());
			todolists = addedTask; // Set the created task as the model

			DefaultHttpHeaders headers = new DefaultHttpHeaders("create");
			headers.setStatus(201); // Set the status code to 201 Created
			return headers;
		} catch (ExceptionError e) {
			todolists = new ErrorResponse(e.getMessage());

			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(400); // Bad Request for validation errors
			return headers;
		} catch (Exception e) {
			todolists = new ErrorResponse("An unexpected error occurred: " + e.getMessage());

			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(500); // Internal Server Error for other errors
			return headers;
		}
	};

	// PUT	/api/todos
	public HttpHeaders update(){
		try {
			todolists = repo.updateTask(id, todolist.getTitle(), todolist.getDescription(), todolist.getStatus());

			DefaultHttpHeaders headers = new DefaultHttpHeaders("update");
			headers.setStatus(200);
			return headers;
		} catch (ExceptionError e){
			todolists = new ErrorResponse(e.getMessage());

			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(404); // Bad Request for validation errors
			return headers;
		} catch (Exception e){
			todolists = new ErrorResponse("An unexpected error occurred: " + e.getMessage());

			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(500); // Internal Server Error for unexpected exceptions
			return headers;
		}
	}

	// DELETE	/api/users/{id}
	public HttpHeaders destroy(){
		try {
			todolists = repo.removeTask(id);

			DefaultHttpHeaders headers = new DefaultHttpHeaders("destroy");
			headers.setStatus(200);
			return headers;
		} catch (ExceptionError e){
			todolists = new ErrorResponse(e.getMessage());

			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(404); // Bad Request for validation errors
			return headers;
		}catch (Exception e){
			todolists = new ErrorResponse("An unexpected error occurred: " + e.getMessage());

			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(500); // Internal Server Error for unexpected exceptions
			return headers;
		}
	}

	// Implement ModelDriven interface
	public Object getModel() {
		return todolists != null ? todolists : todolist; // Return the appropriate model
	}

	public Todolist getTodolist() {
		return todolist;
	}

	public void setTodolist(Todolist todolist) {
		this.todolist = todolist;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TodolistRepository getRepo() {
		return repo;
	}

	public void setRepo(TodolistRepository repo) {
		this.repo = repo;
	}

}
