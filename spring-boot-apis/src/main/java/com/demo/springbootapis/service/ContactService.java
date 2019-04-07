package com.demo.springbootapis.service;

import java.util.List;

import com.demo.springbootapis.model.ContactUsInfo;

public interface ContactService {
	List<ContactUsInfo> getAllMessages();

	ContactUsInfo createMessage(ContactUsInfo info);

	ContactUsInfo getMessageById(Integer id);

	ContactUsInfo updateMessage(ContactUsInfo info);

	void deleteMessage(Integer id);
}
