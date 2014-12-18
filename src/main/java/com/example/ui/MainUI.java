package com.example.ui;

import org.vaadin.spring.VaadinUI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@VaadinUI
@Title("Main View")
@Theme("main")
public class MainUI extends UI {

	private static final long serialVersionUID = 6973925747736836511L;

	@Override
	protected void init(VaadinRequest request) {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		
		Label label = new Label("<h1>Main View</h1>", ContentMode.HTML);
		layout.addComponent(label);
		
	}

}
