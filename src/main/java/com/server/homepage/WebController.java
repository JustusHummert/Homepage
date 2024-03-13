package com.server.homepage;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class WebController {
    private static final String projectSrc = "https://admin.localhost/getConnections";

    @GetMapping("")
    public String homepage(Model model){
        //add projects from projectsrc to model
        WebClient weblient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().secure(sslContextSpec -> sslContextSpec
                        .sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
                        ))))
                .baseUrl(projectSrc)
                .build();
        Set<String> projects = weblient.get().retrieve().bodyToMono(Set.class).block();
        if(projects== null){
            return null;
        }
        //remove self links
        projects.remove("www");
        projects.remove("justushummert");
        model.addAttribute("projects", projects);
        System.out.println("projects: " + model.getAttribute("projects"));
        return "homepage";
    }
}
