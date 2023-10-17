package com.example.demo.bean;

import lombok.Data;

@Data
public class User {

	private String userID;
	private String userName;
	private String password;
	private String email;
	private String role;
	private String lastLoginDate;
	private String delFlg;
	private String delFlgText;
	private String createDate;
	private String createUser;
	private String updateDate;
	private String updateUser;
	
}