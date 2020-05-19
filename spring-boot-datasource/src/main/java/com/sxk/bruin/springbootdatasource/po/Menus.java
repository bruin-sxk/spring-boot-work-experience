package com.sxk.bruin.springbootdatasource.po;

import lombok.Data;

import java.util.Date;

@Data
public class Menus {

	private String id;
	private Date createTime;
	private Date updateTime;
	private String deleted;
	private String icon;
	private String name;
	private String parentId;
	private String priority;
	private String target;
	private String team;
	private String url;


}
