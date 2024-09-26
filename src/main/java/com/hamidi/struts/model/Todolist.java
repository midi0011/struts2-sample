package com.hamidi.struts.model;

import com.hamidi.struts.enums.TaskStatus;

public class Todolist {
	private Integer id;
	private String title;
	private String description;
	private TaskStatus status;
	
	public Todolist() {
	}
	
	public Todolist(Integer id, String title, String description, TaskStatus status) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	
}
