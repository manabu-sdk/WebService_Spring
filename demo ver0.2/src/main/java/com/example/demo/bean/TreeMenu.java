package com.example.demo.bean;

import java.util.Map;

import lombok.Data;

@Data
public class TreeMenu {

	private String id;
	private String parent;
	private String text;
	private String iconType;
	private String icon;
	private String position;
	private String action;
	private String target;
	private String type;
	private String level;
	private Map<String,String> a_attr;
	
}