package com.salesianostriana.dam.gestiapp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.model.ReservedDate;
import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.model.RoomCategory;
import com.salesianostriana.dam.gestiapp.model.School;
import com.salesianostriana.dam.gestiapp.model.TimeZone;
import com.salesianostriana.dam.gestiapp.repository.ReserveCheckerRepository;
import com.salesianostriana.dam.gestiapp.service.ReserveCheckerService;
import com.salesianostriana.dam.gestiapp.service.ReserveService;
import com.salesianostriana.dam.gestiapp.service.ReservedDateService;

@ExtendWith(MockitoExtension.class)
public class ReserveCheckerServiceTest {

	@InjectMocks
	private ReserveCheckerService rCService;
	
	@Mock
	ReserveCheckerRepository rCRepository;

	@Mock
	ReserveService reserveService;

	@Mock
	ReservedDateService reserveDateService;

	private List<Reserve> reserveData = Arrays.asList(new Reserve(), new Reserve());

	// new ArrayList<Reserve>();

	private List<ReservedDate> reservedDateData = Arrays.asList(new ReservedDate(1, LocalDate.of(2019, 10, 31)),
			new ReservedDate(1, LocalDate.of(2019, 10, 28)), new ReservedDate(2, LocalDate.of(2019, 10, 21)));

	private List<Reserve> emptyReserveData = new ArrayList<Reserve>();

	private  Room room = new Room(1L, "Aula", new School(), new RoomCategory());

	private  TimeZone timeZone = new TimeZone(1L, "Octava", LocalTime.of(20, 00));



	@Test
	public final void isFreeDateTest() {
		when(reserveDateService.findAll()).thenReturn(reservedDateData);
		boolean resultado = rCService.checkFreeDate(LocalDate.of(2019, 10, 29));
		assertFalse(resultado);
	}

	@Test
	public final void notFreeDateTest() {
		when(reserveDateService.findAll()).thenReturn(reservedDateData);
		boolean result = rCService.checkFreeDate(LocalDate.of(2019, 10, 28));
		assertFalse(result);
	}

	@Test
	public final void isWeekendTest() {
		when(rCRepository.findAll().get(0).getWeekendOn()).thenReturn(true);
		boolean result = rCService.checkWeekend(LocalDate.of(2019, 10, 26));
		assertTrue(result);

	}

	@Test
	public final void notWeekendTest() {
		when(rCRepository.findAll().get(0).getWeekendOn()).thenReturn(true);
		boolean result = rCService.checkWeekend(LocalDate.of(2019, 10, 28));
		assertTrue(result);

	}

	@Test
	public final void deactivateWeekendTest() {
		when(rCService.findAll().get(0).getWeekendOn()).thenReturn(false);
		boolean result = rCService.checkWeekend(LocalDate.of(2019, 10, 26));
		assertFalse(result);

	}

	@Test
	public final void checkReserveDateAndEmptyTest() {
		when(reserveService.findAll()).thenReturn(emptyReserveData);
		boolean result = rCService.checkReservedDate(LocalDate.of(2019, 10, 29), timeZone, room);
		assertTrue(result);
	}

	@Test
	public final void checkReserveDateAndNotReservedTest() {
		when(reserveService.findAll()).thenReturn(emptyReserveData);
		when(reserveService.findAll()).thenReturn(reserveData);
		boolean result = rCService.checkReservedDate(LocalDate.of(2019, 10, 29), timeZone, room);
		assertTrue(result);
	}

	@Test
	public final void checkReserveDateAndReservedTest() {
		when(reserveService.findAll()).thenReturn(emptyReserveData);
		boolean result = rCService.checkReservedDate(LocalDate.of(2019, 10, 29), timeZone, room);
		assertTrue(result);
	}

	@Test
	public final void checkReserveTrueAndAllFreeTest() {
		LocalDate actualDate = LocalDate.of(2019, 10, 29);
		LocalDate selectedDate = LocalDate.of(2019, 10, 30);
		when(LocalDate.now()).thenReturn(selectedDate);
		when(LocalTime.now()).thenReturn(LocalTime.of(22, 00));
		when(rCService.checkFreeDate(actualDate)).thenReturn(false);
		when(rCService.checkWeekend(actualDate)).thenReturn(true);
		when(rCService.checkReservedDate(actualDate, timeZone, room)).thenReturn(true);
		boolean result = rCService.checkReserve(actualDate, timeZone, room);
		assertTrue(result);
	}

	@Test
	public final void checkReserveFalseAndNotFreeDateTest() {
		LocalDate actualDate = LocalDate.of(2019, 10, 29);
		LocalDate selectedDate = LocalDate.of(2019, 10, 31);
		when(LocalDate.now()).thenReturn(selectedDate);
		when(LocalTime.now()).thenReturn(LocalTime.of(22, 00));
		when(rCService.checkFreeDate(selectedDate)).thenReturn(true);
		when(rCService.checkWeekend(selectedDate)).thenReturn(true);
		when(rCService.checkReservedDate(selectedDate, timeZone, room)).thenReturn(true);
		boolean result = rCService.checkReserve(actualDate, timeZone, room);
		assertTrue(result);
	}

}
