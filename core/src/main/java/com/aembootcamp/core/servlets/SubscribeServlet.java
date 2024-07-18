package com.aembootcamp.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = { Servlet.class },
    property = {
        Constants.SERVICE_DESCRIPTION + "=Subscribe Form Servlet",
        "sling.servlet.paths=" + "/bin/subscribe",
            "sling.servlet.methods=" + HttpConstants.METHOD_POST,
    })
public class SubscribeServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String redirect = request.getParameter("redirect");

        response.setContentType("text/html");
        response.getWriter().write("Subscription successful!");
        response.sendRedirect(redirect);
    }
}
