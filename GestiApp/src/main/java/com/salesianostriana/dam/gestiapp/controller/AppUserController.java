package com.salesianostriana.dam.gestiapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.gestiapp.formbeans.UserFormBean;
import com.salesianostriana.dam.gestiapp.model.AppUser;
import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.service.AppUserService;
import com.salesianostriana.dam.gestiapp.service.ReserveService;
import com.salesianostriana.dam.gestiapp.service.SchoolService;

/**
 * Clase que contiene todos los métodos que controlan las acciones llevadas a cabo por un usuario de la aplicación
 * @author José María, Jesús Ceacero, José Manuel y Daniel Troncoso
 *
 */

@Controller
public class AppUserController {

	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private ReserveService reserveService;
	
	@Autowired
	private SchoolService schoolService;

	/**
	 * Devuelve un formulario de registro
	 * @param model
	 * @return formulario de registro
	 */
	@GetMapping("/formRegister")
	public String showFormRegister(Model model) {

		model.addAttribute("userFormBean", new UserFormBean());
		model.addAttribute("listSchool", schoolService.findAll());

		return "register";

	}
	
	/**
	 * Registra al usuario
	 * @param userFormBean
	 * @param passwordEncoder
	 * @return inicio
	 */
	@PostMapping("/formRegister/submit")
	public String showFormRegisterSubmit(@ModelAttribute("userFormBean") UserFormBean userFormBean, BCryptPasswordEncoder passwordEncoder) {

		AppUser u = new AppUser();

		u.setName(userFormBean.getName());
		u.setSurname(userFormBean.getSurname());
		u.setUserEmail(userFormBean.getUserEmail());
		u.setPassword(passwordEncoder.encode(userFormBean.getPassword()));
		u.setAdmin(false);
		u.setValidated(false);
		u.setSchool(userFormBean.getSchool());

		appUserService.save(u);

		return "redirect:/";
	}
	
	/**
	 * Perfil del usuario
	 * @param model
	 * @return perfil de usuario
	 */
	@GetMapping("/profile")
	public String showFormProfile(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) auth.getPrincipal();
		AppUser appUser = appUserService.searchByEmail(user.getUsername());

		model.addAttribute("userProfile", appUser);

		return "formProfileShow";
	}
	
	/**
	 * Edita los datos del perfil de usuario
	 * @param u
	 * @param model
	 * @return formulario de edición
	 */
	@GetMapping("/profile/{id}")
	public String showFormProfileEdit(@ModelAttribute("userProfile") AppUser u, Model model) {
		
		AppUser pEdit = appUserService.findById(u.getId());
		
		if(pEdit != null) {
			
			model.addAttribute("profileEdit", pEdit);
			
			return "formProfileEdit";//le paso el formulario de editar productos
			
		}else {
			
			return "redirect:/profile";
		}
		
	}
	
	/**
	 * Confirma los cambios de edición del perfil de usuario
	 * @param puser
	 * @param model
	 * @return perfil del usuario
	 */
	@PostMapping("/profile/submit")
	public String processFormProfileEdit(@ModelAttribute("profileEdit") AppUser puser, Model model) {
		
		String plantilla;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) auth.getPrincipal();
		
		if (!puser.getUserEmail().equals(user.getUsername())) {
			plantilla="redirect:/logout";
		} else {
			plantilla="redirect:/profile";
		}
		appUserService.edit(puser);
		
		return plantilla;
	}
	
	/**
	 * Lista de las reservas del usuario que tiene la sesión abierta
	 * @param model
	 * @return lista de reservas
	 */
	
	@GetMapping("/myreserves")
	public String showFormReserves(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) auth.getPrincipal();
		List <Reserve> reserve = reserveService.findAllByReserveUserId(appUserService.searchByEmail(user.getUsername()).getId());

		model.addAttribute("myReserve", reserve);

		return "yourreserves";
	}
	
	
	/**
	 * Elimina la reserva elegida
	 * @param id
	 * @return lista de reservas
	 */
	@GetMapping("/myreserves/{id}")
	public String myReservesDelete(@PathVariable("id") long id) {
		
		reserveService.deleteById(id);
		
		return "redirect:/myreserves";
		
	}
	
	/**
	 * Muestra todas las reservas
	 * @param model
	 * @return lista de reservas
	 */
	@GetMapping("/allreserves")
	public String showAllReserves(Model model) {
		
		model.addAttribute("reserves", reserveService.findAll());
		return "allreserves";
	}

}
