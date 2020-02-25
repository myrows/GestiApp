/**
 * 
 */
package com.salesianostriana.dam.gestiapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * Clase modelo de usuario.
 * 
 * @author José Manuel
 *
 */
@Data
@NoArgsConstructor
@Entity
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name, surname;

	@NotNull
	@Column(unique = true)
	private String userEmail;

	private String password;

	private Boolean admin, validated;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	private School school;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy="reserveUser", cascade = CascadeType.ALL)
	private List<Reserve> reserveList;
	
	/**
	 * @param id        Id del usuario.
	 * @param name      Nombre del usuario.
	 * @param surname   Apellidos del usuario.
	 * @param userEmail Correo electronico del usuario.
	 * @param password  Password del usuario.
	 * @param admin     Booleano que define si el usuario es administrador o no.
	 * @param validated Booleando que define si el usuario esta validado para el uso
	 *                  de la plataforma o no.
	 */
	public AppUser(long id, String name, String surname, @NotNull String userEmail, @NotNull String password,
			Boolean admin, Boolean validated) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.userEmail = userEmail;
		this.password = password;
		this.admin = admin;
		this.validated = validated;
	}

}
