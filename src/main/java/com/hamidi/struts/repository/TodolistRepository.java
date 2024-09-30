package com.hamidi.struts.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.hamidi.struts.enums.TaskStatus;
import com.hamidi.struts.exception.ExceptionError;
import com.hamidi.struts.model.Todolist;
import com.hamidi.struts.util.ResponseMessage;

public class TodolistRepository {
	private static List<Todolist> todolists = new ArrayList<Todolist>();
	private static Random rand = new Random();

	static {
		todolists.add(new Todolist(rand.nextInt(1000), "Sample Title1", "Sample Description1", "pending"));
		todolists.add(new Todolist(rand.nextInt(1000), "Sample Title2", "Sample Description2", "pending"));
		todolists.add(new Todolist(rand.nextInt(1000), "Sample Title3", "Sample Description3", "pending"));
	}

	public List<Todolist> listAll() {
		return todolists;
	}

	public Todolist getById(Integer id) throws ExceptionError {
		for (Todolist todo : todolists) {
			if (todo.getId().equals(id)) {
				return todo;
			}
		}
		throw new ExceptionError("Todolist with ID " + id + " not found.");
	}

	public Todolist addTask(String title, String description, String status) {
		if (title == null || title.isEmpty()){
			throw new ExceptionError("Title must not be empty");
		}

		if (status.isEmpty()){
			throw new ExceptionError("status cannot be empty");
		}

		if (!status.equals("pending") && !status.equals("completed")) {
			throw new ExceptionError("Invalid status: " + status + ". Status must be either 'pending' or 'completed'.");
		}

		Todolist newTask = new Todolist(rand.nextInt(1000), title, description, status);

		todolists.add(newTask);
		return newTask;
	}

	public Todolist updateTask(Integer id, String title, String description, String status) throws  ExceptionError{
		for (Todolist todo : todolists) {
			if (todo.getId().equals(id)) {
				if (title != null && !title.isEmpty()) {
					todo.setTitle(title);
				} else {
					throw new ExceptionError("Title should not be empty");
				}
				if (status != null && (status.equals("pending") || status.equals("completed"))) {
					todo.setStatus(status);
				} else if (status != null) {
					throw new ExceptionError("Invalid status: " + status + ". Status must be either 'pending' or 'completed'.");
				}
				todo.setDescription(description);
				return todo;
			}
		}
		throw new ExceptionError("Todolist with ID " + id + " not found.");
	}

	public ResponseMessage removeTask(Integer id) throws ExceptionError{
		boolean removed = false;

		Iterator<Todolist> iterator = todolists.iterator();
		while (iterator.hasNext()) {
			Todolist todo = iterator.next();
			if (todo.getId().equals(id)) {
				iterator.remove(); // Remove the todo item from the list
				removed = true;
				break; // Exit the loop after removal
			}
		}

		if (!removed) {
			throw new ExceptionError("Todolist with ID " + id + " was not found.");
		}

		return new ResponseMessage("Task ID " + id + " was succesfully deleted");
	}
}
