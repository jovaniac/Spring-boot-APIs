package com.demo.springbootapis.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.springbootapis.model.ContactUsInfo;
import com.demo.springbootapis.repository.ContactUsRepository;

@Service("cotactService")
public class ContactServiceImpl implements ContactService {

  @Autowired
  ContactUsRepository contactRepository;

  @Override
  public List<ContactUsInfo> getAllMessages() {
    return contactRepository.findAll();
  }

  @Override
  public ContactUsInfo createMessage(ContactUsInfo info) {
    return contactRepository.save(info);
  }

  @Override
  public ContactUsInfo getMessageById(Integer id) {
    return contactRepository.findById(id).get();
  }

  @Override
  public ContactUsInfo updateMessage(ContactUsInfo info) {
    ContactUsInfo message = contactRepository.findById(info.getId()).get();
    if (message == null) {
      return null;
    }
    message.setName(info.getName());
    message.setEmail(info.getEmail());
    message.setPhoneNumber(info.getPhoneNumber());
    message.setSubject(info.getSubject());
    message.setMessage(info.getMessage());

    return contactRepository.save(message);
  }

  @Override
  public void deleteMessage(Integer id) {
    ContactUsInfo message = contactRepository.findById(id).get();
    if (message != null) {
      contactRepository.delete(message);
    }
  }
}
