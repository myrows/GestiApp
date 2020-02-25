package com.salesianostriana.dam.gestiapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.salesianostriana.dam.gestiapp.model.AppUser;
import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.model.School;
import com.salesianostriana.dam.gestiapp.model.TimeZone;
import com.salesianostriana.dam.gestiapp.repository.ReserveRepository;

@DataJpaTest
@ActiveProfiles("test")
class ReserveRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	ReserveRepository reserveRepository;
	
	@BeforeEach
	public void cargar() {
		
		AppUser a= new AppUser();
		a.setName("Daniel");
		a.setId(1);
		a.setSurname("Troncoso Rubio");
		a.setPassword("1234");
		a.setUserEmail("buenas@gmail.com");
		a.setAdmin(true);
		a.setValidated(false);
		a.setReserveList(null);
		a.setSchool(null);
		
		Reserve r = new Reserve();
		r.setReserveUser(a);
		r.setReservedRoom(new Room());
		r.setSchool(new School());
		r.setDate(LocalDate.of(2019, 10, 28));
		r.setId(1);
		r.setTimeZone(new TimeZone());
		
		entityManager.persist(r);
	}
	
	@Test
	public void testEncontrarPorIdUsuario() {
	
		List<Reserve> reserver = reserveRepository.findAllByReserveUserId(1);
		
		assertThat(reserver).hasSize(1);
		
	}

}
