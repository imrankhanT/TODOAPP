package com.bridgelabz.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.bridgelabz.model.Email;
import com.bridgelabz.utility.EmailSender;

public class ConsumerMsg implements MessageListener {

	public void onMessage(Message message) {
		try {
			System.out.println("In consumer");
			ObjectMessage objectMessage = (ObjectMessage) message;
			Email email = (Email) objectMessage.getObject();
			System.out.println(email);
			System.out.println(
					email.getTo() + " " + email.getSubject() + " " + email.getUrl() + " " + email.getSetBody());
			EmailSender.sendMail(email);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
