package com.learning.contactmanagement.Repository;

import com.learning.contactmanagement.Entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface ContactRepository extends MongoRepository<Contact, Integer> {
    Page<Contact> findByFilter(String key, String value, Pageable pageable);



}
