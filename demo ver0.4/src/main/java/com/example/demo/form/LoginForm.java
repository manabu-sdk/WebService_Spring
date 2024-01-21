package com.example.demo.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginForm implements Serializable {
	private static final long serialVersionUID = -5790257903137052623L;
	private String userID;
	private String password;
}