package com.example.freemarker.config;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class TemplateConfiguration {

    @Bean(name = "ftlConfiguraion")
    public freemarker.template.Configuration ftlConfiguraion() {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            configuration.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir"), "ftl"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}
