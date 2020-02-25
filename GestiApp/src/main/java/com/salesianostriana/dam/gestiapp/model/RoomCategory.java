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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase modelo de las categorías
 * @author José Manuel
 *
 */

@Data
@NoArgsConstructor
@Entity
public class RoomCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String categoryName;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "roomCategory")
	private List<Room> roomList = new ArrayList <>();
	
	/**
	 * 
	 * @param id Id de la categoría
	 * @param categoryName Nombre de la categoría
	 */
	
	public RoomCategory(long id, @NotNull String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}
	

	
	
}
