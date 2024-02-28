package com.example.crudWithVaadin.ui;

import com.example.crudWithVaadin.model.Company;
import com.example.crudWithVaadin.model.Contact;
import com.example.crudWithVaadin.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContactFormTest {

    List<Company> companies;
    List<Status> statuses;
    Company company1, company2;
    Status status1, status2;
    Contact sarahContact;

    @BeforeEach
    void setUp() {
        company1 = new Company("Exim");
        company2 = new Company("Tesla");
        companies = Arrays.asList(company1, company2);

        status1 = new Status("Pending");
        status2 = new Status("Active");
        statuses = Arrays.asList(status1, status2);

        sarahContact = new Contact("Sarah", "Hunt", "sarah@gmail.com", company1, status1);
    }

    @Test
    void formFieldsPopulated() {
        ContactForm form = new ContactForm(companies, statuses);
        form.setContact(sarahContact);

        assertEquals("Sarah", form.firstName.getValue());
        assertEquals("Hunt", form.lastName.getValue());
        assertEquals("sarah@gmail.com", form.email.getValue());
        assertEquals(company1, form.company.getValue());
        assertEquals(status1, form.status.getValue());
    }

    @Test
    void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies, statuses);
        form.setContact(new Contact());
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.email.setValue("johndoe@gmail.com");
        form.company.setValue(company2);
        form.status.setValue(status2);

        AtomicReference<Contact> contactAtomicReference = new AtomicReference<>(null);
        form.addSaveListener(e -> contactAtomicReference.set(e.getContact()));
        form.save.click();
        Contact savedContact = contactAtomicReference.get();

        assertEquals("John", savedContact.getFirstName());
        assertEquals("Doe", savedContact.getLastName());
        assertEquals("johndoe@gmail.com", savedContact.getEmail());
        assertEquals(company2, savedContact.getCompany());
        assertEquals(status2, savedContact.getStatus());
    }

    @Test
    void saveEventHasIncorrectValues() {
        ContactForm form = new ContactForm(companies, statuses);
        form.setContact(new Contact());

        AtomicReference<Contact> contactAtomicReference = new AtomicReference<>(null);
        form.addSaveListener(e -> contactAtomicReference.set(e.getContact()));
        form.save.click();
        Contact savedContact = contactAtomicReference.get();

        assertNull(savedContact);
    }
}