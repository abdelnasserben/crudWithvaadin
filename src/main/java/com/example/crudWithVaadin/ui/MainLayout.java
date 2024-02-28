package com.example.crudWithVaadin.ui;

import com.example.crudWithVaadin.service.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        H3 logo = new H3("Vaadin CRM");
        logo.addClassNames(
                LumoUtility.FontSize.SMALL,
                LumoUtility.Margin.MEDIUM);

        String loggedUsername = securityService.getLoggedUser().getUsername();
        Button logoutButton = new Button("Logout, " + loggedUsername, e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logoutButton);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);
    }

    private void createHeader() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Dashboard", DashboardLayout.class),
                new RouterLink("Contacts", ContactListView.class)));
    }
}
