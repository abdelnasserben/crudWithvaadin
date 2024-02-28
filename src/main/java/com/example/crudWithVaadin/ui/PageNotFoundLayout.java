package com.example.crudWithVaadin.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.servlet.http.HttpServletResponse;


public class PageNotFoundLayout extends VerticalLayout implements HasErrorParameter<NotFoundException> {

    public PageNotFoundLayout() {
        setHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(new H2("Oops"), new RouterLink("Go back home", DashboardLayout.class));

    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
