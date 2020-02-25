package com.salesianostriana.dam.gestiapp.formbeans;

import com.salesianostriana.dam.gestiapp.model.RoomCategory;
import com.salesianostriana.dam.gestiapp.model.School;

public class RoomFormBean {
	
	private Long id;
	private String name;
	private School school;
	private RoomCategory roomCategory;

	public RoomFormBean() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomCategory getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(RoomCategory roomCategory) {
		this.roomCategory = roomCategory;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
