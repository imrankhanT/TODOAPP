package com.bridgelabz.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.bridgelabz.model.Email;

public class ProducerMsg implements ProducerService {
	@Autowired
	JmsTemplate jmsTemplate;

	public void send(final Email email) {
		jmsTemplate.send(new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				System.out.println("inside produce:"+email.getTo());
				Message message = session.createObjectMessage(email);
				return message;
			}
		});
	}

}
