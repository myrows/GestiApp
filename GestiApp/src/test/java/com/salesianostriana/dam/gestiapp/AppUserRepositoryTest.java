package com.salesianostriana.dam.gestiapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.salesianostriana.dam.gestiapp.model.AppUser;
import com.salesianostriana.dam.gestiapp.repository.AppUserRepository;

@DataJpaTest
@ActiveProfiles("test")
class AppUserRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@BeforeEach
	public void cargar() {
		
		AppUser a= new AppUser();
		a.setName("Daniel");
		a.setSurname("Troncoso Rubio");
		a.setPassword("1234");
		a.setUserEmail("buenas@gmail.com");
		a.setAdmin(true);
		a.setValidated(false);
		a.setReserveList(null);
		a.setSchool(null);
		entityManager.persist(a);
	}
	
	@Test
	public void testEncontrarPrimeroPorEmail() {
	
		AppUser user = appUserRepository.findFirstByUserEmail("buenas@gmail.com");
		
		assertThat(user.getName()).isEqualTo("Daniel");
		
	}
	
	@Test
	public void testEncontrarUsuarioValidatedFalse() {
		
		List<AppUser> user = appUserRepository.findAllByValidatedFalse();
		
		assertThat(user).hasSize(4);
	}

}
