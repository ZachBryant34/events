package com.zacharybryant.events.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zacharybryant.events.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>{
	List<Event> findAll();
	
	List<Event> findAllByState(String state);
	
	List<Event> findByStateNotContains(String state);
}
