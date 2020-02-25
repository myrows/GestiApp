/**
 * 
 */
package com.salesianostriana.dam.gestiapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase modelo de fecha reservada, esta clase permite definir días festivos
 * para reservar o no las aulas
 * 
 * @author José Manuel
 *
 */
@Data
@NoArgsConstructor
@Entity
public class ReservedDate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	/**
	 * @param id
	 * @param date
	 */
	public ReservedDate(long id, @NotNull LocalDate date) {
		super();
		this.id = id;
		this.date = date;
	}
	




}
