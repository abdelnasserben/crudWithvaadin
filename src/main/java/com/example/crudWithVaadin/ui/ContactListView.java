package com.example.crudWithVaadin.ui;


import com.example.crudWithVaadin.model.Contact;
import com.example.crudWithVaadin.service.CrmService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed(value = "ADMIN")
@Route(value = "contacts", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
@CssImport("./styles/shared-styles.css")
public class ContactListView extends VerticalLayout {
    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();
    ContactForm form;

    CrmService crmService;

    public ContactListView(CrmService crmService) {
        this.crmService = crmService;
        setSizeFull();
        addClassName("listview");

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        configureGrid();
        configureForm();

        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();

        return content;
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button newContactButton = new Button("New contact");
        newContactButton.addClickListener(a -> clearEditor());


        var toolbar = new HorizontalLayout(filterText, newContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassNames("grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }

    private void configureForm() {
        form = new ContactForm(crmService.findAllCompanies(), crmService.findAllStatus());
        form.setWidth("25rem");
        form.addSaveListener(e -> {
            crmService.saveContact(e.getContact());
            updateList();
        });
        form.addDeleteListener(e -> {
            crmService.deleteContact(e.getContact());
            updateList();
        });
        form.addCancelListener(e -> closeEditor());
    }

    private void updateList() {
        grid.setItems(crmService.findAllContacts(filterText.getValue()));
        clearEditor();
    }

    private void editContact(Contact contact) {
        if(contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    public void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    public void clearEditor() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }
}
