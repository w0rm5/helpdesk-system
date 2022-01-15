package com.kimpiv.helpdesk.model;

import java.util.HashMap;
import java.util.Map;

public enum Status {
	Pending(0),
	Accepted(1),
	Solving(2),
	Solved(3),
	Replied(4),
	Completed(5);
	
	private final int statusCode;

	private Status(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public boolean compareToInt(int status) {
		return statusCode == status;
	}
	
	public static Status fromInt(int x) {
		Status[] values = Status.values();
		int length = values.length;
		for(int i = 0; i < length; i++) {
			if(values[i].compareToInt(x)) {
				return values[i];
			}
		}
		return Status.Pending;
	}
	
	public static Map<Integer, String> getIntStringMap(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		Status[] values = Status.values();
		int length = values.length;
		for(int i = 0; i < length; i++) {			
			map.put(values[i].getStatusCode(), values[i].name());
		}
		return map;
	}
}
