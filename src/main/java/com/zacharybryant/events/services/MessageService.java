package com.zacharybryant.events.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zacharybryant.events.models.Message;
import com.zacharybryant.events.repositories.MessageRepository;

@Service
public class MessageService {
	private MessageRepository mesRepo;
	
	public MessageService(MessageRepository mesRepo) {
		this.mesRepo = mesRepo;
	}
	
	public List<Message> allMessages() {
		return mesRepo.findAll();
	}
	
	public Message createMessage(Message m) {
		return mesRepo.save(m);
	}
	
	public List<Message> findByEvent(String event) {
		return mesRepo.findAllByEvent(event);
	}
}
