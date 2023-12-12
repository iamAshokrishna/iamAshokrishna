package com.learning.contactmanagement.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Indexed
    private String contact_Name;
    @NonNull
    private String contact_Email;
    @NonNull
    private String contact_Phone;
    private String filter;
    @NonNull
    private String contact_Type;
    @NonNull
    private String admin_Name;
    @NonNull
    private String admin_Email;
    @NonNull
    private String contact_Number;
    @NonNull
    private String job_Title;
    @NonNull
    private String organisation;
    @NonNull
    private String notes;
    @NonNull
    private String parent_Company;
    @NonNull
    private String country;
    @NonNull
    private String street;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String zip;
    @NonNull
    private String website;
    @NonNull
    private Date create_Date = new Date(System.currentTimeMillis());
}
