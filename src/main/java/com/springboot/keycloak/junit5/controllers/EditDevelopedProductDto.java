package com.springboot.keycloak.junit5.controllers;

public class EditDevelopedProductDto {

	private String id;
	private String iconName;
	private String name;

	public EditDevelopedProductDto(String id, String iconName, String name) {
		super();
		this.id = id;
		this.iconName = iconName;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getIconName() {
		return iconName;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "EditDevelopedProductDto [id=" + id + ", iconName=" + iconName + ", name=" + name + "]";
	}

}
