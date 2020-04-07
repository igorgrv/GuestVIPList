package com.vipguestlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vipguestlist.emailSender.emailSender.EmailService;
import com.vipguestlist.model.Guest;
import com.vipguestlist.service.GuestService;

@Controller
public class GuestController {

	@Autowired
	private GuestService service;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("guestlist")
	public ModelAndView guestList() {
		ModelAndView mv = new ModelAndView("guestlist");
		Iterable<Guest> guests = service.findAll();
		mv.addObject("guests", guests);
		return mv;
	}
	
	@PostMapping("save")
	public ModelAndView save(Guest guest) {
		service.save(guest);
		EmailService emailService = new EmailService();
		emailService.sendEmail(guest.getName(), guest.getEmail());
		return new ModelAndView("redirect:guestlist");
	}
}
