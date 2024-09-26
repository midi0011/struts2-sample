package com.hamidi.struts.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hamidi.struts.enums.TaskStatus;
import com.hamidi.struts.exception.ExceptionError;
import com.hamidi.struts.model.Todolist;

public class TodolistRepository {
	private static List<Todolist> todolists = new ArrayList<Todolist>();
	private static Random rand = new Random();

	static {
		todolists.add(new Todolist(rand.nextInt(1000), "Sample Title1", "Sample Description1", TaskStatus.PENDING));
		todolists.add(new Todolist(rand.nextInt(1000), "Sample Title2", "Sample Description2", TaskStatus.PENDING));
		todolists.add(new Todolist(rand.nextInt(1000), "Sample Title3", "Sample Description3", TaskStatus.PENDING));
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

	public Todolist addTask(String title, String description, TaskStatus status) {

		if (title == null || title.trim().isEmpty()) {
			throw new ExceptionError("Title cannot be null or empty.");
		}
		if (status == null) {
			throw new ExceptionError("Status cannot be null or empty.");
		}

		int randomId;
		boolean isUnique;

		do {
			randomId = rand.nextInt(1000); // Generate a random ID
			isUnique = true;

			// Check if the generated ID already exists
			for (Todolist existingTodo : todolists) {
				if (existingTodo.getId().equals(randomId)) {
					isUnique = false; // ID already exists, regenerate
					break;
				}
			}
		} while (!isUnique);

		Todolist newTask = new Todolist(randomId, title, description, status);

		todolists.add(newTask);
		return newTask;
	}
}
