package com.zacharybryant.events.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zacharybryant.events.models.Event;
import com.zacharybryant.events.models.User;
import com.zacharybryant.events.repositories.EventRepository;
import com.zacharybryant.events.repositories.UserRepository;

@Service
public class EventService {
	private final UserService userServ;
	private final EventRepository eventRepo;
	private final UserRepository userRepo;
	
	public EventService(EventRepository eventRepo, UserRepository userRepo, UserService userServ) {
		this.eventRepo = eventRepo;
		this.userRepo = userRepo;
		this.userServ = userServ;
	}
	
	public List<Event> allEvents() {
		return eventRepo.findAll();
	}
	
	public Event createEvent(Event e) {
		return eventRepo.save(e);
	}
	
	public Event findEvent(Long id) {
		Optional<Event> optionalEvent = eventRepo.findById(id);
		if(optionalEvent.isPresent()) {
			return optionalEvent.get();
		}
		else {
			return null;
		}
	}
	
	public List<Event> findByState(String state) {
		return eventRepo.findAllByState(state);
	}
	
	public List<Event> findNotInState(String state) {
		return eventRepo.findByStateNotContains(state);
	}
	
	public Event updateEvent(Long id, String name, Date date, String city, String state) {
		Event event = findEvent(id);
		event.setName(name);
		event.setDate(date);
		event.setCity(city);
		event.setState(state);
		return eventRepo.save(event);
	}
	
	public void deleteEvent(Long id) {
		eventRepo.deleteById(id);
	}
	
	public void addAttendeeToEve(Long aId, Long eId) {
		User user = userServ.findUserById(aId);
		
		Event event = findEvent(eId);
	    
		event.getAttendees().add(user);
		
		eventRepo.save(event);
	}
	
	public void removeAttendeeToEve(Long aId, Long eId) {
		User user = userServ.findUserById(aId);
		
		Event event = findEvent(eId);
	    
		event.getAttendees().remove(user);
		
		eventRepo.save(event);
	}
}
