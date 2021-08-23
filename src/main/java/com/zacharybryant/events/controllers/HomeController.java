package com.zacharybryant.events.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zacharybryant.events.models.Event;
import com.zacharybryant.events.models.LoginUser;
import com.zacharybryant.events.models.Message;
import com.zacharybryant.events.models.User;
import com.zacharybryant.events.services.EventService;
import com.zacharybryant.events.services.MessageService;
import com.zacharybryant.events.services.UserService;


@Controller
public class HomeController {
	@Autowired
    private UserService userServ;
	@Autowired
	private EventService eveServ;
	@Autowired
	private MessageService messServ;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "login.jsp";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, 
            BindingResult result, Model model, HttpSession session) {
        userServ.register(newUser, result);
        if(result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "login.jsp";
        }
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/events";
    }
    
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {
        User user = userServ.login(newLogin, result);
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "login.jsp";
        }
        session.setAttribute("user_id", user.getId());
        return "redirect:/events";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
    
    @RequestMapping("/events")
    public String index(HttpSession session, Model model, @ModelAttribute("event") Event event) {
    	if(session.getAttribute("user_id") == null) {
    		return "redirect:/";
    	}
    	else {
    		Long id = (Long) session.getAttribute("user_id");
    		User user = userServ.findUserById(id);
    		List<Event> eventsI = eveServ.findByState(user.getState());
    		List<Event> eventsO = eveServ.findNotInState(user.getState());
    		model.addAttribute("user", user);
    		model.addAttribute("eveIn", eventsI);
    		model.addAttribute("eveOut", eventsO);
    		return "index.jsp";
    	}
    }
    
    @RequestMapping(value="/events", method=RequestMethod.POST)
    public String createEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, HttpSession session) {
    	if (result.hasErrors()) {
    		System.out.print(result.getFieldErrors());
    		return "index.jsp";
    	}
    	else {
    		Long id = (Long) session.getAttribute("user_id");
    		User user = userServ.findUserById(id);    		
    		event.setHost(user);
//    		eveServ.createEvent(event);
    		eveServ.createEvent(event);
    		return "redirect:/events";
    	}
    }
    
    @RequestMapping("/events/{id}")
    public String showEvent(@PathVariable("id") Long id, Model model, HttpSession session) {
    	if(session.getAttribute("user_id") == null) {
    		return "redirect:/";
    	}
    	else {
    		Event event = eveServ.findEvent(id);
    		int count = event.getAttendees().size();
    		model.addAttribute("event", event);
    		model.addAttribute("count", count);
    		model.addAttribute("message", new Message());
    		return "show.jsp";
    	}
    }
    
    @RequestMapping(value="/events/{id}/messages", method=RequestMethod.POST)
    public String createEvent(@Valid @ModelAttribute("message") Message message, BindingResult result, @PathVariable("id") Long id, HttpSession session) {
    	if (result.hasErrors()) {
    		System.out.print(result.getFieldErrors());
    		return "index.jsp";
    	}
    	else {
    		if(!message.getId().equals(null)) {
    			message.setId(null);
    		}
    		System.out.println(message.getId());
    		Long uId = (Long) session.getAttribute("user_id");
    		User user = userServ.findUserById(uId);
    		Event event = eveServ.findEvent(id);
    		message.setUser(user);
    		message.setEvent(event);
    		messServ.createMessage(message);
    		return "redirect:/events/" + id;
    	}
    }	
    	
    @RequestMapping("/events/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Event event = eveServ.findEvent(id);
        model.addAttribute("event", event);
        return "edit.jsp";
    }
    
    @RequestMapping(value="/events/{id}", method=RequestMethod.PUT)
    public String update(@Valid @ModelAttribute("event") Event event, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
        	Event eventE = eveServ.findEvent(id);
            model.addAttribute("event", eventE);
        	return "edit.jsp";
        } else {
            eveServ.updateEvent(id, event.getName(), event.getDate(), event.getCity(), event.getState());
            return "redirect:/events";
        } 
    }
    
    @RequestMapping("/events/{id}/join")
    public String joinEvent(@PathVariable("id") Long eId, HttpSession session) {
    	Long aId = (Long) session.getAttribute("user_id");
    	eveServ.addAttendeeToEve(aId, eId);
    	return "redirect:/events";
    }
    
    @RequestMapping("/events/{id}/cancel")
    public String cancelEvent(@PathVariable("id") Long eId, HttpSession session) {
    	Long aId = (Long) session.getAttribute("user_id");
    	eveServ.removeAttendeeToEve(aId, eId);
    	return "redirect:/events";
    }
    
    @RequestMapping(value="/events/{id}", method=RequestMethod.DELETE)
    public String destroy(@PathVariable("id") Long id) {
        eveServ.deleteEvent(id);
        return "redirect:/events";
    }
    
    
}
