package io.github.eltankesitoarceus.dailyDig.web;

import io.github.eltankesitoarceus.dailyDig.DailyDig;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.Headers;

public class RouteHandler {

    public static HttpHandler createHandler() {
        ResourceHandler resourceHandler = new ResourceHandler(
                new ClassPathResourceManager(DailyDig.class.getClassLoader(), "web"))
                .addWelcomeFiles("index.html")
                .setDirectoryListingEnabled(false);

        HttpHandler websiteHandler = new RoutingHandler()
                .get("/", resourceHandler)
                //CSS && JS
                .get("/css/*",resourceHandler)
                .get("/js/*", resourceHandler);

        HttpHandler apiHandler = new RoutingHandler()
//                .get("/api/hello", exchange -> {
//                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
//                    exchange.getResponseSender().send("{\"message\": \"Hello, World!\"}");
//                })
                .post("/api/data", exchange -> {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                    exchange.getRequestReceiver().receiveFullString((exchange1, data) -> {
                        exchange1.getResponseSender().send("{\"received\": \"" + data + "\"}");
                    });
                });

        return new PathHandler()
                .addPrefixPath("/api", apiHandler)
                .addPrefixPath("/", websiteHandler);
    }
}
