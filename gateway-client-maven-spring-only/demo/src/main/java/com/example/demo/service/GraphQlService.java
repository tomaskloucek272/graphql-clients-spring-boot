package com.example.demo.service;


import com.example.demo.dto.ClientsResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class GraphQlService {

    private final HttpGraphQlClient graphQlClient;

    public GraphQlService() {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:3000/graphql")
                .build();
        graphQlClient = HttpGraphQlClient.builder(client).build();
    }


    public Mono<ClientsResponse> getData() {
        String document = """
                {
                  client1: client(id: "1") {
                    id
                    name
                    orders {
                      total
                    }
                  }
                  client2: client(id: "2") {
                    id
                    name
                    orders {
                      total
                    }
                  }
                }
        """;

        /** Match whole response path = "" */
        return graphQlClient.document(document)
                .retrieve("").toEntity(ClientsResponse.class);
    }
}
