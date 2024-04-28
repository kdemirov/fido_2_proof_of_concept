package com.iwmnetwork.aqtos.internship;

import com.iwmnetwork.aqtos.internship.DiscussionApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DiscussionApplication.class);
    }

}
