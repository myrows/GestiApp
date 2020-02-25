/**
 * 
 */
package com.salesianostriana.dam.gestiapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase modelo de aula.
 * 
 * @author José Manuel
 *
 */

@Data
@NoArgsConstructor
@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	private School school;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	private RoomCategory roomCategory;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy="reservedRoom", cascade = CascadeType.ALL)
	private List<Reserve> listaReservas = new ArrayList <>();

	/**
	 * 
	 * @param id           Id de la sala reservada.
	 * @param name         Nombre de la sala reservada.
	 * @param roomNum      Numero del aula de escuela.
	 * @param school       Escuela a la que le pertenece el aula.
	 * @param roomCategory Categoría del aula.
	 */
	public Room(long id, @NotNull String name, School school, RoomCategory roomCategory) {
		super();
		this.id = id;
		this.name = name;
		this.school = school;
		this.roomCategory = roomCategory;
	}

}
