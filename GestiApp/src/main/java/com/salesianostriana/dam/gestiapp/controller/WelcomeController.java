package com.salesianostriana.dam.gestiapp.controller;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.service.ReserveService;
import com.salesianostriana.dam.gestiapp.service.TimeZoneService;

/**
 * Esta clase devuelve la plantilla de inicio (calendario semanal)
 * @author Jose Manuel, Jose Maria
 *
 */

@Controller
public class WelcomeController {
	
	@Autowired
	private ReserveService reserveService;
	@Autowired
	private TimeZoneService timeZoneService;
	
	/** CALENDARIO **/

	private boolean isCurrentWeekDateSelect(Calendar yourSelectedDate) {
		Date ddd = yourSelectedDate.getTime();
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Date monday = c.getTime();
		Date nextMonday = new Date(monday.getTime() + 7 * 24 * 60 * 60 * 1000);
		
		return ddd.after(monday) ||ddd.equals(monday) && ddd.before(nextMonday);
	}
	
	/**
	 * Muestra el calendario con las reservas de esta semana
	 * @param model
	 * @return calendario semanal
	 */
	@GetMapping("/")
	public String getWeekCalendar(Model model) {
		model.addAttribute("timeZoneList", timeZoneService.findAll());
		
		Calendar calendar = Calendar.getInstance();
		
		List<Reserve> thisWeekReserves = new ArrayList<Reserve>();

		for (Reserve reserve : reserveService.findAll()) {
			Date date = Date.from(reserve.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			calendar.setTime(date);
			if (this.isCurrentWeekDateSelect(calendar)) {
				thisWeekReserves.add(reserve);
			}

		}
		model.addAttribute("reserveList", thisWeekReserves);
		return "calendar";
	}
}
