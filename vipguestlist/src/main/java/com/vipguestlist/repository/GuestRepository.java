package com.vipguestlist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vipguestlist.model.Guest;


public interface GuestRepository extends CrudRepository<Guest, Long>{

	List<Guest> findByName(String name); 
	
}
