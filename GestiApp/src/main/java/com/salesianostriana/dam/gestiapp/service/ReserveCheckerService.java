package com.salesianostriana.dam.gestiapp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.gestiapp.model.ReserveChecker;
import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.model.ReservedDate;
import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.model.TimeZone;
import com.salesianostriana.dam.gestiapp.repository.ReserveCheckerRepository;
import com.salesianostriana.dam.gestiapp.service.base.BaseService;

/**
 * Clase servicio que gestiona el comprobador
 * 
 * @author José María, Jesús Ceacero, José Manuel, Daniel Troncoso
 */

@Service
public class ReserveCheckerService extends BaseService<ReserveChecker, Long, ReserveCheckerRepository> {

	@Autowired
	ReserveCheckerRepository reserveCheckerRepository;

	@Autowired
	ReservedDateService reservedDateService;

	@Autowired
	ReserveService reserveService;

	@Autowired
	ReservedDateService reserveDateService;
	
	// No usar este metodo. checkea fines de semana. Si devuelve true, significa que
	// el weekend esta activo, se puede reservar.
	public Boolean checkWeekend(LocalDate localDate) {
		int dayOfWeek = localDate.getDayOfWeek().getValue();

		// Si el dia es sabado o domingo
		if (dayOfWeek == 6 || dayOfWeek == 7) {
			// Si el fin de semana esta activo
			if (reserveCheckerRepository.findAll().get(0).getWeekendOn() == true) {
				return true;
			} else {
				return false;
			}
		}
		return true;

	}

	// No usar este metodo. Checkea que el administrador, no haya bloqueado la
	// fecha. True, significa bloqueada
	public Boolean checkFreeDate(LocalDate ldate) {
		boolean result = false;

		for (ReservedDate rDate : reservedDateService.findAll()) {
			if (rDate.getDate().equals(ldate)) {
				
				result = true;
			}
		}

		return result;

	}

	// No usar este metodo. Chequea dia, hora y clase. Si todo esta libre, devuelve
	// true, es decir, se puede reservar.
	public Boolean checkReservedDate(LocalDate ldate, TimeZone timeZone, Room room) {

		boolean result = false;

		if (reserveService.findAll().isEmpty()) {
			result = true;
		} else {
			for (Reserve reserve : reserveService.findAll()) {
				// Comprueba fecha
				if (reserve.getDate().equals(ldate)) {
					// Comprueba hora
					if (!reserve.getTimeZone().getTime().equals(timeZone.getTime())) {
						result = true;
					} else {
						if (room.getId() == reserve.getReservedRoom().getId()) {
							result = false;
						} else {
							result = true;
						}
					}

				} else {
					result = true;
				}
			}
		}
		return result;

	}

	// Este es el método final que envuelve a los anteriores, ademas de comprobar
	// que no sea ni fecha anterior, ni hora anterior. Si devuelve true, se puede
	// reservar.
	public Boolean checkReserve(LocalDate localDate, TimeZone timeZone, Room room) {

		boolean result = false;
		LocalDate actualDate = LocalDate.now();
		LocalTime actualTime = LocalTime.now();

		

		// Si el dia que le pasamos no es anterior al de hoy
		if (!localDate.isBefore(actualDate)) {
			// Si estamos en el mismo dia, que compruebe la hora
			System.out.println(localDate.isEqual(actualDate));
			if (localDate.isEqual(actualDate)) {
				// Si la hora de la reserva no es anterior a la actual, entonces sigue el flujo
				if (!timeZone.getTime().isBefore(actualTime)) {
					// Si todo ok, chequea
					if (!this.checkFreeDate(localDate) && this.checkWeekend(localDate)
							&& this.checkReservedDate(localDate, timeZone, room)) {
						result = true;
					}
				}

			} else {
				// Si no es el mismo dia, checkea
				if (!this.checkFreeDate(localDate) && this.checkWeekend(localDate)
						&& this.checkReservedDate(localDate, timeZone, room)) {
					result = true;

				}
			}
		}

		return result;

	}
	
	@Override
	public ReserveChecker save(ReserveChecker t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public ReserveChecker edit(ReserveChecker t) {
		// TODO Auto-generated method stub
		return super.edit(t);
	}

	@Override
	public void delete(ReserveChecker t) {
		// TODO Auto-generated method stub
		super.delete(t);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
	}

	@Override
	public ReserveChecker findById(Long id) {
		// TODO Auto-generated method stub
		return super.findById(id);
	}

	@Override
	public List<ReserveChecker> findAll() {
		// TODO Auto-generated method stub
		return super.findAll();
	}
	
	public Page<ReserveChecker> findAllPageable(Pageable pageable) {
        
		 return reserveCheckerRepository.findAll(pageable);
	}

}
