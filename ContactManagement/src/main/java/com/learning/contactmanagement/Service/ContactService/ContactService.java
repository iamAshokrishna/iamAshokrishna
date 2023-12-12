package com.learning.contactmanagement.Service.ContactService;

import com.learning.contactmanagement.Entity.Contact;
import com.learning.contactmanagement.Entity.GenericModel.GenericRequest;
import com.learning.contactmanagement.Entity.GenericModel.GenericResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Repository
public interface ContactService {
    Contact saveContact(Contact contact);

    Contact getContactById(String id);

    Page<Contact> getAllContacts(Map<String, String> filters, Pageable pageable);

    Object updateContact(String id, GenericRequest<Contact> contactRequest);

    String deleteContactById(String id);

    String deleteMultipleContacts(List<String> ids);

    GenericResult<Page<Contact>> getAllContacts(Map<String, String> filters, int page, int size, String sortBy, String order);

    GenericResult<Contact> updateContact(String id, Contact request);

    List<Contact> savemutlipleContacts(List<Contact> contacts);
}