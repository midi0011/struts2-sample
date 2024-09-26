package com.hamidi.struts.enums;

public enum TaskStatus {
	PENDING("pending"),
    COMPLETED("completed");

	private final String status;

	TaskStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}

	public static boolean isValidStatus(String status) {
		for (TaskStatus ts : TaskStatus.values()) {
			if (ts.status.equalsIgnoreCase(status)) {
				return true;
			}
		}
		return false;
	}
}
