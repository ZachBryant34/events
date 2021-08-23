package com.zacharybryant.events.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zacharybryant.events.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findAll();
	
	List<Message> findAllByEvent(String event);
}
