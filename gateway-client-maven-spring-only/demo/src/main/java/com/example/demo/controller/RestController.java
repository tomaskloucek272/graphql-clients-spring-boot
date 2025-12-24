package com.example.demo.controller;


import com.example.demo.dto.ClientsResponse;
import com.example.demo.service.GraphQlService;
import com.tomask.graphql.core.generated.types.Client;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final GraphQlService graphQlService;

    public RestController(GraphQlService graphQlService) {
        this.graphQlService = graphQlService;
    }

    @GetMapping("/test")
    public Mono<ClientsResponse> getClientData() {
        return graphQlService.getData();
    }
}
