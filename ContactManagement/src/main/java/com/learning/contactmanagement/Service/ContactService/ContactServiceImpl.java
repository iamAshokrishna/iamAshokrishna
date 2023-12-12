package com.learning.contactmanagement.Service.ContactService;

import com.learning.contactmanagement.Entity.Contact;
import com.learning.contactmanagement.Entity.GenericModel.GenericRequest;
import com.learning.contactmanagement.Entity.GenericModel.GenericResult;
import com.learning.contactmanagement.Repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(Contact.class);

    private final ContactRepository contactRepo;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public Contact saveContact(Contact contact) {
        logger.info("Contact saved..!");
        return contactRepo.save(contact);
    }

    @Override
    public Contact getContactById(String id) {
        try {
            logger.info("Getting contact by ID: {}", id);
            Optional<Contact> optionalContact = contactRepo.findById(Integer.valueOf(id));
            return optionalContact.orElse(null);
        } catch (NumberFormatException e) {
            logger.error("Invalid contact ID format: {}", id, e);
            throw new IllegalArgumentException("Invalid contact ID format");
        }
    }

    @Override
    public Page<Contact> getAllContacts(Map<String, String> filters, Pageable pageable) {
        try {
            logger.info("Getting all contacts with filters: {}, pageable: {}", filters, pageable);
            if (filters == null) {
                throw new IllegalArgumentException("Filters cannot be null");
            }
            Page<Contact> contacts = contactRepo.findAll(pageable);
            return contacts;
        } catch (Exception e) {
            logger.error("Error getting all contacts", e);
            throw new RuntimeException("Error getting all contacts", e);
        }
    }


    @Override
    public GenericResult<Contact> updateContact(String id, GenericRequest<Contact> contactRequest) {
        try {
            logger.info("Updating contact with ID: {}", id);
            Contact existingContact = contactRepo.findById(Integer.valueOf(id)).orElse(null);
            if (existingContact == null) {
                return GenericResult.error("Contact not found");
            }
            Contact updatedContact = contactRequest.getRequestData();
            existingContact.setContact_Name(updatedContact.getContact_Name());
            existingContact.setContact_Email(updatedContact.getContact_Email());
            existingContact.setContact_Phone(updatedContact.getContact_Phone());
            Contact savedContact = contactRepo.save(existingContact);
            return GenericResult.error(savedContact);
        } catch (Exception e) {
            logger.error("Error updating contact", e);
            throw new RuntimeException("Error updating contact", e);
        }
    }

    @Override
    public String deleteContactById(String id) {
        try {
            logger.info("Contact deleted");
            contactRepo.deleteById(Integer.valueOf(id));
            return "Contact deleted successfully";
        }
        catch (NumberFormatException e) {
            logger.error("Contact ID invalid");
            throw new IllegalArgumentException("Invalid contact ID format");
        }
        catch (Exception e) {
            logger.error("Error in deleting Contact");
            throw new RuntimeException("Error deleting contact", e);
        }
    }

    @Override
    public String deleteMultipleContacts(List<String> ids) {
        try {
            logger.info("Contact Deleted ");
            List<Integer> integerIds = ids.stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            contactRepo.deleteAllById(integerIds);

            return "Contacts deleted successfully";
        }
        catch (NumberFormatException e) {
            logger.error("Contact id invalid");
            throw new IllegalArgumentException("Invalid contact ID format");
        }
        catch (Exception e) {
            logger.error("multiple Contact Deleted ");
            throw new RuntimeException("Error deleting multiple contacts", e);
        }
    }

    @Override
    public GenericResult<Page<Contact>> getAllContacts(Map<String, String> filters, int page, int size, String sortBy, String order) {
        try {
            GenericResult<Page<Contact>> result = new GenericResult<>();
            Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<Contact> contactPage;
            if (filters != null && !filters.isEmpty()) {
                Map.Entry<String, String> entry = filters.entrySet().iterator().next();
                contactPage = (!isPageKey(entry.getKey())) ?
                        contactRepo.findByFilter(entry.getKey(), entry.getValue(), pageable) :
                        contactRepo.findAll(pageable);
                logger.info("Contact Retrieved ");
                result.setSuccess(true);
                result.setMessage("Contacts retrieved successfully");
                result.setData(contactPage);

            }
            else {
                contactPage = contactRepo.findAll(pageable);
                logger.info("All Contact Retrieved ");
                result.setSuccess(true);
                result.setMessage("All Contacts Retrieved Successfully");
                result.setData(contactPage);
            }
            return result;
        }
        catch (Exception e) {
            logger.error("Error in retreiving all contacts ");
            throw new RuntimeException("Error getting all contacts", e);
        }
    }

    @Override
    public GenericResult<Contact> updateContact(String id, Contact contact) {
        try {
            Contact existingContact = contactRepo.findById(Integer.valueOf(id))
                    .orElseThrow(() -> new RuntimeException("Contact not found"));

            BeanUtils.copyProperties(contact, existingContact, "id");

            Contact savedContact = contactRepo.save(existingContact);

            return GenericResult.success(savedContact);
        } catch (Exception e) {
            throw new RuntimeException("Error updating contact", e);
        }
    }

    @Override
    public List<Contact> savemutlipleContacts(List<Contact> contacts) {
        List<Contact> savedContacts = contactRepo.saveAll(contacts);
        return savedContacts;
    }


    private boolean isPageKey(String key) {
        return key.equals("page") || key.equals("size") || key.equals("sortBy") || key.equals("order");
    }
}


