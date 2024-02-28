package com.example.crudWithVaadin;

import com.example.crudWithVaadin.model.Company;
import com.example.crudWithVaadin.model.Contact;
import com.example.crudWithVaadin.model.Status;
import com.example.crudWithVaadin.repository.CompanyRepository;
import com.example.crudWithVaadin.repository.ContactRepository;
import com.example.crudWithVaadin.repository.StatusRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudWithVaadinApplication {

	private final Logger logger = LoggerFactory.getLogger(CrudWithVaadinApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudWithVaadinApplication.class, args);
	}

	@Bean
	CommandLineRunner loadData(ContactRepository contactRepository, CompanyRepository companyRepository, StatusRepository statusRepository) {

		return args -> {

			Status status1 = statusRepository.save(new Status("PENDING"));
			Status status2 = statusRepository.save(new Status("ACTIVE"));
			Status status3 = statusRepository.save(new Status("DELETED"));

			Company company1 = companyRepository.save(new Company("Exim Bank"));
			Company company2 = companyRepository.save(new Company("Meta Inc"));
			Company company3 = companyRepository.save(new Company("Asian group"));

			contactRepository.save(new Contact("Jack", "Monroe", "jackmonroe@gmail.com", company1, status1));
			contactRepository.save(new Contact("Sarah", "Hunt", "sarah.hunt@gmail.com", company2, status2));
			contactRepository.save(new Contact("Bill", "Zayn", "zaynbill@gmail.com", company3, status3));
			contactRepository.save(new Contact("Marie", "Hoffman", "mariehoffman@gmail.com", company1, status3));

			logger.info("Data loaded!");
		};
	}

}
