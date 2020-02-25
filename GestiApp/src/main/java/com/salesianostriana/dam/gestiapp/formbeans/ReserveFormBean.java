package com.salesianostriana.dam.gestiapp.formbeans;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.salesianostriana.dam.gestiapp.model.AppUser;
import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.model.RoomCategory;
import com.salesianostriana.dam.gestiapp.model.School;
import com.salesianostriana.dam.gestiapp.model.TimeZone;

public class ReserveFormBean {

	private Room reservedRoom;
	private AppUser reserveUser;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private TimeZone timeZone;
	private RoomCategory roomCategory;

	private School school;

	public ReserveFormBean() {
		super();
	}

	public Room getReservedRoom() {
		return reservedRoom;
	}

	public void setReservedRoom(Room reservedRoom) {
		this.reservedRoom = reservedRoom;
	}

	public AppUser getReserveUser() {
		return reserveUser;
	}

	public void setReserveUser(AppUser reserveUser) {
		this.reserveUser = reserveUser;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone time) {
		this.timeZone = time;
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

}
