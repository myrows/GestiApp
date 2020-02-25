/**
 * 
 */
package com.salesianostriana.dam.gestiapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase modelo de las reservas.
 * 
 * @author José Manuel
 *
 */

@Data
@NoArgsConstructor
@Entity
public class Reserve {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@NotNull
	@ManyToOne
	private Room reservedRoom;
	@NotNull
	@ManyToOne
	private AppUser reserveUser;
	@NotNull
	private LocalDate date;
	@NotNull
	@ManyToOne
	private TimeZone timeZone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	private School school;

	/**
	 * @param id
	 * @param reservedRoom
	 * @param reserveUser
	 * @param date
	 * @param dateTime
	 * @param school
	 */
	public Reserve(long id, @NotNull Room reservedRoom, @NotNull AppUser reserveUser, @NotNull LocalDate date,
			@NotNull TimeZone dateTime, School school) {
		super();
		this.id = id;
		this.reservedRoom = reservedRoom;
		this.reserveUser = reserveUser;
		this.date = date;
		this.timeZone = dateTime;
		this.school = school;
	}

}
