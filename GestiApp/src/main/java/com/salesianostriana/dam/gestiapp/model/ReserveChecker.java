/**
 * 
 */
package com.salesianostriana.dam.gestiapp.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Clase modelo para el chequeador de fines de semana
 * @author José Manuel
 *
 */

@Data
@Entity
public class ReserveChecker {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	private Boolean weekendOn;

	
	
}
	