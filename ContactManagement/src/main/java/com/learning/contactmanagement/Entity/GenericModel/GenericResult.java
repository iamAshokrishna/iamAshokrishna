package com.learning.contactmanagement.Entity.GenericModel;

import com.learning.contactmanagement.Entity.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenericResult<T> {
    private static boolean success;
    private String message;
    private T data;


    // Constructor
    public GenericResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static GenericResult<Contact> error(String contactNotFound) {

        return GenericResult.error("Contact not found");
    }

    public static GenericResult<Contact> error(Contact savedContact) {
        return GenericResult.error("Contact Not Saved");
    }

    public static GenericResult<Contact> success(Contact savedContact) {
        return GenericResult.success("Contact Saved Successfully");
    }

    private static GenericResult<Contact> success(String contactSavedSuccessfully) {
        return GenericResult.success("Changed Successfully");
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}