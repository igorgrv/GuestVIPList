package com.vipguestlist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vipguestlist.model.Guest;
import com.vipguestlist.repository.GuestRepository;

@Service
public class GuestService {

	@Autowired
	private GuestRepository guestRepository;
	
	public Iterable<Guest> findAll(){
		return guestRepository.findAll();
	}
	
	public void save(Guest guest) {
		guestRepository.save(guest);
	}
}
