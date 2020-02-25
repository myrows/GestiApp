/**
 * 
 */
package com.salesianostriana.dam.gestiapp.model;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase modelo de las zonas horarias
 * @author José Manuel
 *
 */

@Data
@NoArgsConstructor
@Entity
public class TimeZone {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	@NotNull
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime time;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "timeZone")
	private List <Reserve> reserveList;

	/**
	 * 
	 * @param id   Id del tramo horario.
	 * @param name Nombre del tramo horario.
	 * @param time Time del tramo horario.
	 */
	public TimeZone(long id, @NotNull String name, @NotNull LocalTime time) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
	}

}
