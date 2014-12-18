package com.example;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.vaadin.spring.EnableVaadin;
import org.vaadin.spring.boot.EnableVaadinServlet;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableVaadin
@EnableVaadinServlet
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return servletContainer -> ((TomcatEmbeddedServletContainerFactory) servletContainer)
                .addConnectorCustomizers(connector -> {
                    AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>) connector.getProtocolHandler();
                    httpProtocol.setCompression("on");
                    httpProtocol.setCompressionMinSize(256);
                    String mimeTypes = httpProtocol.getCompressableMimeTypes();
                    String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE + ",application/javascript";
                    httpProtocol.setCompressableMimeTypes(mimeTypesWithJson);
                });
    }
}
