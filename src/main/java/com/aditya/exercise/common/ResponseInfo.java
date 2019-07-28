package com.aditya.exercise.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseInfo implements Serializable {
	
    public ResponseInfo(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
    
	private String status;
    private String message;
}
