/**
 * 
 */
package com.salesianostriana.dam.gestiapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase modelo de escuela.
 * 
 * @author José Manuel
 *
 */
@Data
@NoArgsConstructor
@Entity
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private long id;

	@NotNull
	private String name, address;

	@NotNull
	@Column(unique = true)
	private String schoolCode;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
	private List<Room> roomList = new ArrayList<>();
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
	private List<AppUser> userList = new ArrayList<>();
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
	private List<Reserve> reserveList = new ArrayList<>();

	/**
	 * @param id          Id de la escuela.
	 * @param name        Nombre de la escuela.
	 * @param address     Direccion de la escuela.
	 * @param schoolCode  Codigo de identificacion de la escuela.
	 * @param roomList    Lista de aulas en la escuela.
	 * @param userList    Lista de usuarios de la escuela.
	 * @param reserveList Lista de reserva de aulas en la escuela.
	 */
	public School(long id, String name, String address, @NotNull String schoolCode, List<Room> roomList,
			List<AppUser> userList, List<Reserve> reserveList) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.schoolCode = schoolCode;
		this.roomList = roomList;
		this.userList = userList;
		this.reserveList = reserveList;
	}

}
