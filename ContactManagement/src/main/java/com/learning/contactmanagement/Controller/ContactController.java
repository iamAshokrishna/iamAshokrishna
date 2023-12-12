package com.learning.contactmanagement.Controller;

import com.learning.contactmanagement.Entity.Contact;
import com.learning.contactmanagement.Entity.GenericModel.GenericResult;
import com.learning.contactmanagement.Service.ContactService.ContactService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;




//    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<GenericResult<Contact>> createContact(@RequestBody Contact contact) {
        Contact savedContact = contactService.saveContact(contact);
        return new ResponseEntity<>(new GenericResult<>(true, "Contact created successfully", savedContact), HttpStatus.CREATED);
    }

    @PostMapping("/n")
    public ResponseEntity<GenericResult<List<Contact>>> createMultipleContacts(@RequestBody List<Contact> contacts) {
        List<Contact> savedContacts = contactService.savemutlipleContacts(contacts);
        return new ResponseEntity<>(new GenericResult<>(true, "Contacts created successfully", savedContacts), HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<GenericResult<Contact>> updateContact(@PathVariable String id, @RequestBody Contact updatedContact) {
        GenericResult<Contact> result = new GenericResult<>();
        Contact existingContact = contactService.getContactById(id);

        if (existingContact != null) {
            BeanUtils.copyProperties(updatedContact, existingContact);

            Contact savedContact = contactService.saveContact(existingContact);

            result.setSuccess(true);
            result.setMessage("Contact updated successfully");
            result.setData(savedContact);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.setSuccess(true);
        result.setMessage("Contact not found, but request processed successfully");
        result.setData(null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResult<String>> deleteContactById(@PathVariable String id) {
        String result = contactService.deleteContactById(id);
        return new ResponseEntity<>(new GenericResult<>(true, "Contact deleted successfully", result), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<GenericResult<String>> deleteMultipleContacts(@RequestBody List<String> ids) {
        String result = contactService.deleteMultipleContacts(ids);
        return new ResponseEntity<>(new GenericResult<>(true, "Contacts deleted successfully", result), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GenericResult<Page<Contact>>> getAllContacts(
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        GenericResult<Page<Contact>> result = contactService.getAllContacts(filters, page, size, sortBy, order);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
