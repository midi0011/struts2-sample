package com.hamidi.struts.rest;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.hamidi.struts.enums.TaskStatus;
import com.hamidi.struts.exception.ExceptionError;
import com.hamidi.struts.model.Todolist;
import com.hamidi.struts.repository.TodolistRepository;
import com.opensymphony.xwork2.ModelDriven;

public class TodolistController implements ModelDriven<Object> {

	public Todolist todolist = new Todolist();
	private Integer id;
	private Object todolists;
	private TodolistRepository repo = new TodolistRepository();

	public class ErrorResponse {
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
		todolists = repo.listAll();
		System.out.println("GET \t /todos");
		return new DefaultHttpHeaders("index");
	}

	// GET /api/todos/1
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
			String statusStr = todolist.getStatus().toString().toUpperCase();
			
			if (!TaskStatus.isValidStatus(statusStr)) {
	            throw new ExceptionError("Invalid status: " + statusStr);
	        }
			
			TaskStatus status = TaskStatus.valueOf(statusStr);
			todolists = repo.addTask(todolist.getTitle(), todolist.getDescription(), status);

			DefaultHttpHeaders headers = new DefaultHttpHeaders("create");
			headers.setStatus(201);
			return headers;
			
		} catch (IllegalArgumentException e) {
			System.out.println("Error: Invalid status provided.");
			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(400); // Bad Request for invalid input
			return headers;
			
		} catch (Exception e) {
			System.out.println("Unexpected Error: " + e.getMessage());
			DefaultHttpHeaders headers = new DefaultHttpHeaders("error");
			headers.setStatus(500); // Internal Server Error
			return headers;
			
		}
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

	public Object getModel() {
		if (todolists == null) {
			return todolist; // Return the list if it exists
		} else {
			return todolists;
		}
	}

}
