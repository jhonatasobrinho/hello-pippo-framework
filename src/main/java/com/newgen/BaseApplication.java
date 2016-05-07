package com.newgen;

import ro.pippo.core.Application;

import java.util.logging.Logger;

/**
 * Created by newgen on 5/7/16.
 */
public class BaseApplication extends Application {

    private Logger logger = Logger.getLogger(BaseApplication.class.getName());

    @Override
    protected void onInit() {
        ALL("/.*", routeContext -> {
            logger.info(String.format("Receiving %s at %s", routeContext.getRequestMethod(), routeContext.getRequestUri()));
            routeContext.next();
        });

        /*GET("/document./*", routeContext -> {
            if (routeContext.getSession("username") == null) {
                routeContext.setSession("originalDestination", routeContext.getRequest().getApplicationUriWithQuery());
                routeContext.redirect("login");
            } else {
                routeContext.next();
            }
        });*/

        GET("/login", routeContext -> routeContext.render("login"));

        POST("/login", (routeContext) -> {
            String username = routeContext.getParameter("username").toString();
            String password = routeContext.getParameter("password").toString();
            if (authenticate(username, password)) {
                String originalDestination = routeContext.removeSession("originalDestination");
                routeContext.resetSession();

                routeContext.setSession("username", username);
                routeContext.setLocal("username", username);
                routeContext.redirect(originalDestination != null ? originalDestination : "/documents");
            } else {
                routeContext.flashError("Authentication failed");
                routeContext.redirect("/login");
            }
        });

        GET("/documents", routeContext -> routeContext.render("documents"));
    }

    private boolean authenticate(String username, String password) {
        return username.toLowerCase().equals("admin") && password.toLowerCase().equals("admin");
    }
}
