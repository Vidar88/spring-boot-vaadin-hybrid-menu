package de.vidar.example.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = ErrorPage.VIEW_NAME)
public class ErrorPage extends VerticalLayout implements View {

	public static final String VIEW_NAME = "ErrorPage";

	@PostConstruct
	void init() {
		addComponent(new Label("This is the error view"));
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		// This view is constructed in the init() method()
	}
}
