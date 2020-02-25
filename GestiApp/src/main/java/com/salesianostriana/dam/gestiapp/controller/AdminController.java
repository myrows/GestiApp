package com.salesianostriana.dam.gestiapp.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.gestiapp.formbeans.UserFormBean;
import com.salesianostriana.dam.gestiapp.model.AppUser;
import com.salesianostriana.dam.gestiapp.model.Pager;
import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.model.ReserveChecker;
import com.salesianostriana.dam.gestiapp.model.ReservedDate;
import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.model.RoomCategory;
import com.salesianostriana.dam.gestiapp.model.TimeZone;
import com.salesianostriana.dam.gestiapp.service.AppUserService;
import com.salesianostriana.dam.gestiapp.service.ReserveCheckerService;
import com.salesianostriana.dam.gestiapp.service.ReserveService;
import com.salesianostriana.dam.gestiapp.service.ReservedDateService;
import com.salesianostriana.dam.gestiapp.service.RoomCategoryService;
import com.salesianostriana.dam.gestiapp.service.RoomService;
import com.salesianostriana.dam.gestiapp.service.SchoolService;
import com.salesianostriana.dam.gestiapp.service.TimeZoneService;

/**
 * Clase que contiene todos los métodos que controlan las acciones llevadas a cabo por un admin, así como la impresión de listas para los mismos
 * @author José María, Jesús Ceacero, José Manuel y Daniel Troncoso
 *
 */

@Controller
public class AdminController {

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 5, 10, 20, 50 };
	

	@Autowired
	private AppUserService userService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private RoomCategoryService roomCategoryService;

	@Autowired
	private ReserveService reserveService;
	
	@Autowired
	private ReserveCheckerService reserveCheckerService;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private ReservedDateService reservedDateService;
	
/* VALIDACIÓN */
	
	/**
	 * Lista de usuarios sin validar
	 * @param model
	 * @return lista de usuarios
	 */
	
	@GetMapping("/admin/validate")
	public String showFormValidated(Model model) {

		model.addAttribute("users", userService.searchByValitadedFalse());
		return "userstovalidate";
	}
	
	/**
	 * Validación de usuario
	 * @param id
	 * @param model
	 * @return lista de usuarios
	 */
	
	@GetMapping("/admin/validate/{id}")
	public String FormValidatedEdit(@PathVariable("id") long id, Model model) {

		boolean validate = true;
		AppUser u = userService.findById(id);

		if (u != null) {

			// Set de true a un usuario y lo guarda
			
			model.addAttribute("user", u);
			u.setValidated(validate);
			userService.save(u);

			return "redirect:/admin/validate";

		} else {

			return "redirect:/admin/validate";
		}
	}

/** LISTA DE USUARIOS **/

	/**
	 * Lista de usuarios
	 * @param model
	 * @return lista de usuarios
	 */
	
	@GetMapping("/admin/users")
	public String showUsers(Model model) {

		List<AppUser> users = userService.findAll();
		model.addAttribute("users", users);
		return "userAdministration";
	}
	
	/**
	 * Elimina el usuario elegido de la lista de usuarios
	 * @param id
	 * @param model
	 * @return lista de usuarios
	 */
	
	@GetMapping("/admin/delUser/{id}")
	public String delUsers(@PathVariable("id") long id, Model model) {
		userService.deleteById(id);
		model.addAttribute("users", userService.findAll());
		return "redirect:/admin/users";
	}
	
	/**
	 * Confirma la edición del usuario que hemos elegido con anterioridad de la lista
	 * @param userFormBean
	 * @param passwordEncoder
	 * @return lista de usuarios
	 */
	
	@PostMapping("/admin/editUser/submit")
	public String editUserSubmit(@ModelAttribute("userFormBean") UserFormBean userFormBean,
			BCryptPasswordEncoder passwordEncoder) {

		AppUser u = userService.findById(userFormBean.getId());
		u.setName(userFormBean.getName());
		u.setSurname(userFormBean.getSurname());
		u.setUserEmail(userFormBean.getUserEmail());
		if(!userFormBean.getPassword().equals("")) {
			u.setPassword(passwordEncoder.encode(userFormBean.getPassword()));
		}else {
			u.setPassword(u.getPassword());
		}
		u.setAdmin(userFormBean.getAdmin());
		u.setSchool(userFormBean.getSchool());

		userService.edit(u);

		return "redirect:/admin/users";
	}
	
	/**
	 * Nos abre el formulario de edición para el usuario elegido de la lista
	 * @param id
	 * @param model
	 * @return formulario de edición o lista de usuarios
	 */
	
	@GetMapping("/admin/editUser/{id}")
	public String editUser(@PathVariable("id") long id, Model model) {
		model.addAttribute("listSchool", schoolService.findAll());

		AppUser editedAppUser = userService.findById(id);

		if (editedAppUser != null) {
			model.addAttribute("userFormBean", editedAppUser);
			return "editUser";
		} else {
			return "redirect:/admin/users";
		}

	}

	

/** AULAS **/

	/**
	 * Lista de aulas
	 * @param pageSize
	 * @param page
	 * @param model
	 * @return lista de aulas
	 */
	
	@GetMapping("/admin/rooms")
	public String showRooms(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Model model) {

		// Evalúa el tamaño de página. Si el parámetro es "nulo", devuelve
		// el tamaño de página inicial.
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);

		// Calcula qué página se va a mostrar. Si el parámetro es "nulo" o menor
		// que 0, se devuelve el valor inicial. De otro modo, se devuelve el valor
		// del parámetro decrementado en 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		// Obtenemos la página definida por evalPage y evalPageSize de objetos de
		// nuestro modelo
		Page<Room> rooms = roomService.findAllPageable(PageRequest.of(evalPage, evalPageSize));
		// Creamos el objeto Pager (paginador) indicando los valores correspondientes.
		// Este sirve para que la plantilla sepa cuantas páginas hay en total, cuantos
		// botones
		// debe mostrar y cuál es el número de objetos a dibujar.
		Pager pager = new Pager(rooms.getTotalPages(), rooms.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("rooms", rooms);
		model.addAttribute("room", new Room());
		model.addAttribute("roomCategories", roomCategoryService.findAll());
		model.addAttribute("schools", schoolService.findAll());
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		return "roomAdministration";
	}
	
	/**
	 * Elimina el aula seleccionada de la lista
	 * @param id
	 * @param model
	 * @return lista de aulas
	 */
	
	@GetMapping("/admin/delRoom/{id}")
	public String delRooms(@PathVariable("id") long id, Model model) {
		roomService.deleteById(id);
		model.addAttribute("rooms", roomService.findAll());
		return "redirect:/admin/rooms";
	}
	
	/**
	 * Muestra el formulario de edición del aula seleccionada de la lista
	 * @param id
	 * @param model
	 * @return formulario de edición
	 */
	
	@GetMapping("/admin/editRoom/{id}")
	public String editRoom(@PathVariable("id") long id, Model model) {

		model.addAttribute("room", roomService.findById(id));
		model.addAttribute("roomCategories", roomCategoryService.findAll());
		model.addAttribute("schools", schoolService.findAll());
		return "formRoomEdit";

	}
	
	/**
	 * Confirma la edición del aula seleccionada
	 * @param room
	 * @return lista de aulas
	 */
	@PostMapping("/admin/editRoom/submit")
	public String editRoomSubmit(@ModelAttribute("room") Room room) {

		roomService.edit(room);
		return "redirect:/admin/rooms";
	}
	
	/**
	 * Añade un aula nueva
	 * @param room
	 * @param model
	 * @return lista de aulas
	 */
	@PostMapping("/admin/roomSave")
	public String addCerveza(@ModelAttribute("room") Room room, Model model) {

		roomService.save(room);

		return "redirect:/admin/rooms";
	}

/** LISTA DE CATEGORíAS **/
	
	/**
	 * Lista de categorías
	 * @param model
	 * @return lista de categorías
	 */
	
	@GetMapping("/admin/roomCategories")
	public String roomCategory(Model model) {

		List<RoomCategory> categories = roomCategoryService.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("category", new RoomCategory());
		return "roomCategories";
	}
	
	/**
	 * Muestra el formulario de edición de categoría
	 * @param id
	 * @param model
	 * @return formulario de edición
	 */
	@GetMapping("/admin/editCategory/{id}")
	public String editCategory(@PathVariable("id") long id, Model model) {

		RoomCategory editCategory = roomCategoryService.findById(id);
		model.addAttribute("category", editCategory);

		return "roomCategoryEdit";
	}
	
	/**
	 * Confirma la edición de la categoría
	 * @param c
	 * @return lista de categorías
	 */
	@PostMapping("/admin/categorySubmit")
	public String procesarCerveza(@ModelAttribute("category") RoomCategory c) {
		roomCategoryService.edit(c);
		return "redirect:/admin/roomCategories";
	}
	
	/**
	 * Elimina la categoría que elijamos de la lista
	 * @param id
	 * @return lista de categorías
	 */
	@GetMapping("/admin/categoryDel/{id}")
	public String roomCategoryDel(@PathVariable("id") long id) {
		roomCategoryService.deleteById(id);
		return "redirect:/admin/roomCategories";
	}
	
	/**
	 * Añade una categoría nueva
	 * @param category
	 * @param model
	 * @return lista de categorías
	 */
	@PostMapping("/admin/categorySave")
	public String addCategory(@ModelAttribute("category") RoomCategory category, Model model) {
		RoomCategory catego = new RoomCategory();

		catego.setCategoryName(category.getCategoryName());

		roomCategoryService.save(catego);

		return "redirect:/admin/roomCategories";
	}

/** RESERVAS **/
	
	/**
	 * Lista de reservas
	 * @param model
	 * @return lista de reservas
	 */
	@GetMapping("/admin/reserves")
	public String showReserves(Model model) {

		List<Reserve> reserves = reserveService.findAll();
		model.addAttribute("reserves", reserves);
		return "reserveAdministration";
	}
	
	/**
	 * Elimina la reserva seleccionada de la lista
	 * @param id
	 * @return lista de reservas
	 */
	@GetMapping("/admin/reserveDel/{id}")
	public String reserveDel(@PathVariable("id") long id) {
		reserveService.deleteById(id);
		return "redirect:/admin/reserves";
	}
	
	/**
	 * Lista de zonas horarias
	 * @param model
	 * @return lista de horas
	 */
	@GetMapping("/admin/timeZone")
	public String timeZone(Model model) {
		
		model.addAttribute("timeZones", timeZoneService.findAll());
		model.addAttribute("timeZone", new TimeZone());
		return "timeZone";
	}
	
	/**
	 * Elimina la zona seleccionada
	 * @param id
	 * @return lista de horas
	 */
	@GetMapping("/admin/timeZoneDel/{id}")
	public String timeZoneDel(@PathVariable("id") long id) {
		timeZoneService.deleteById(id);
		return "redirect:/admin/timeZone";
	}
	
	/**
	 * Añadir zona horaria
	 * @param timeZone
	 * @param model
	 * @return lista de horas
	 */
	@PostMapping("/admin/timeZoneSave")
	public String addTimeZone(@ModelAttribute("timeZone") TimeZone timeZone, Model model) {
		TimeZone timeZo = timeZone;

		timeZoneService.save(timeZo);

		return "redirect:/admin/timeZone";
	}
	
	/**
	 * Muestra el formulario de edición para la zona seleccionada
	 * @param id
	 * @param model
	 * @return formulario de edición
	 */
	@GetMapping("/admin/timeZoneEdit/{id}")
	public String timeZoneEdit(@PathVariable("id") long id, Model model) {

		TimeZone timeZone = timeZoneService.findById(id);
		model.addAttribute("timeZone", timeZone);

		return "timeZoneEdit";
	}
	
	/**
	 * Confirma la edición de la zona seleccionada
	 * @param c
	 * @return lista de horas
	 */
	@PostMapping("/admin/timeZoneSubmit")
	public String timeZoneSubmit(@ModelAttribute("timeZone") TimeZone c) {
		System.out.println(c);
		TimeZone timeZone = timeZoneService.findById(c.getId());
		timeZone.setName(c.getName());
		timeZone.setTime(c.getTime());
		timeZoneService.edit(timeZone);
		return "redirect:/admin/timeZone";
	}

/** FINES DE SEMANA **/
	
	/**
	 * Panel de fines de semana
	 * @param model
	 * @return panel de findes
	 */
	@GetMapping("/admin/weekend")
	public String showWeekendPanel(Model model) {
		model.addAttribute("checker", reserveCheckerService.findAll().get(0));
		return "weekendAdministration";
	}
	
	/**
	 * Confirma la activación o desactivación de los fines de semana
	 * @param checker
	 * @return panel de findes
	 */
	@PostMapping("/admin/weekend/edit")
	public String weekendEdit(@ModelAttribute("checker") ReserveChecker checker) {

		reserveCheckerService.edit(checker);
		
		return "redirect:/admin/weekend";
	}
	
	/**
	 * Lista de días festivos
	 * @param model
	 * @return lista de días festivos
	 */
	@GetMapping("/admin/festive")
	public String showFestivePanel(Model model) {
		
		model.addAttribute("festives", reservedDateService.findAll());
		model.addAttribute("festive", new ReservedDate());
		return "festiveDays";
	}
	
	/**
	 * Guarda un día festivo nuevo
	 * @param festive
	 * @param model
	 * @return lista de días festivos
	 */
	@PostMapping("/admin/festiveSave")
	public String addFestiveDay(@ModelAttribute("festive") ReservedDate festive, Model model) {
		ReservedDate rd = new ReservedDate();

		rd.setDate(festive.getDate());

		reservedDateService.save(rd);
		return "redirect:/admin/festive";
	}
	
	/**
	 * Elimina un día festivo de la lista
	 * @param id
	 * @return lista de días festivos
	 */
	@GetMapping("/admin/festiveDel/{id}")
	public String festiveDel(@PathVariable("id") long id) {
		reservedDateService.deleteById(id);
		return "redirect:/admin/festive";
	}
	
}
