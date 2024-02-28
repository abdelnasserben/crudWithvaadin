package com.example.crudWithVaadin.service;

import com.example.crudWithVaadin.model.Company;
import com.example.crudWithVaadin.model.Contact;
import com.example.crudWithVaadin.model.Status;
import com.example.crudWithVaadin.repository.CompanyRepository;
import com.example.crudWithVaadin.repository.ContactRepository;
import com.example.crudWithVaadin.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository, CompanyRepository companyRepository, StatusRepository statusRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    public List<Contact> findAllContacts(String filter) {

        if(filter == null || filter.isEmpty())
            return contactRepository.findAll();
        else
            return contactRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(filter, filter);
    }

    public void saveContact(Contact contact) {
        if(contact == null) {
            System.err.println("Can't save an empty contact");
            return;
        }

        contactRepository.save(contact);
    }

    public void deleteContact(Contact contact) {

        if(contact == null) {
            System.err.println("Can't delete an empty contact");
            return;
        }
        contactRepository.delete(contact);
    }

    public Long countContacts() {
        return contactRepository.count();
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatus() {
        return statusRepository.findAll();
    }
}
