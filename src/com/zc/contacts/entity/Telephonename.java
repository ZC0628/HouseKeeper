package com.zc.contacts.entity;

import java.util.ArrayList;
/**
 * 用于展示TelephoneActivity中ListView的实体类
 * @author Administrator
 *
 */
public class Telephonename {
	public int id;
	public String number;
	public String name;
	
	public Telephonename(int _id, String number, String name) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
	}

	
	public Telephonename() {
		super();
	}
	public Telephonename(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Telephonename [id=" + id + ", number=" + number + ", name="
				+ name + "]";
	}
	
	
	
	
	
}
