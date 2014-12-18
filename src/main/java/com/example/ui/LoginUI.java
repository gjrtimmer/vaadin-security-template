package com.example.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.security.Security;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@VaadinUI(path = "/login")
@Title("Login")
@Theme("login")
public class LoginUI extends UI implements ClickListener {

	private static final long serialVersionUID = -7679349571926742502L;

	@Value("${app.title}")
	private String applicationTitle;
	
	@Autowired
	private Security security;
	
	private TextField username;
	private PasswordField password;
	
	@Override
	protected void init(VaadinRequest request) {
		
		setSizeFull();
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		layout.setSizeFull();
		
        Component loginForm = buildLoginForm();
        layout.addComponent(loginForm);
        layout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        setContent(layout);
	}
	
	private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        
        CheckBox rememberMe = new CheckBox("Remember me", true);
        rememberMe.setTabIndex(3);
        
        loginPanel.addComponent(rememberMe);
        return loginPanel;
    }

    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        username = new TextField("Username");
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        username.setTabIndex(1);
        username.focus();

        password = new PasswordField("Password");
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setTabIndex(2);

        final Button signin = new Button("Sign In", this);
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);
        signin.setTabIndex(4);

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
        
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label(applicationTitle);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

	@Override
	public void buttonClick(ClickEvent event) {
		
		try {
			
			security.login(username.getValue(), password.getValue());
			getPage().setLocation("/");
			
		} catch(AuthenticationException e) {
			
			Notification notification = new Notification("Login Failed", Type.ERROR_MESSAGE);
			notification.setHtmlContentAllowed(true);
			notification.setPosition(Position.TOP_CENTER);
			notification.setDescription(e.getLocalizedMessage());
			notification.setDelayMsec(2000);
			notification.show(Page.getCurrent());
			
		}
		
	}

}
