package com.example.graphql.rest;

import com.example.graphql.dto.ClientResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.client.codegen.GraphQLMultiQueryRequest;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import com.tomask.graphql.core.generated.client.ClientGraphQLQuery;
import com.tomask.graphql.core.generated.client.ClientProjectionRoot;
import com.tomask.graphql.core.generated.types.Client;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.DgsGraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;

@RestController
public class GraphQlController {

    @GetMapping("/sendQueries")
    public String test() throws JsonProcessingException {
        HttpGraphQlClient client = HttpGraphQlClient.create(WebClient.create("http://localhost:3000/graphql"));
        DgsGraphQlClient dgsClient = DgsGraphQlClient.create(client);


        ClientGraphQLQuery clientRequest1 = ClientGraphQLQuery.newRequest().id("1").build();
        GraphQLQueryRequest request1 = new GraphQLQueryRequest(clientRequest1,
                new ClientProjectionRoot<>().id().name().orders().total());
        request1.getQuery().setQueryAlias("client1");

        ClientGraphQLQuery clientRequest2 = ClientGraphQLQuery.newRequest().id("2").build();
        GraphQLQueryRequest request2 = new GraphQLQueryRequest(clientRequest2,
                new ClientProjectionRoot<>().id().name().orders().total());
        request2.getQuery().setQueryAlias("client2");

        GraphQLMultiQueryRequest multiQueryRequest = new GraphQLMultiQueryRequest(
                List.of(request1, request2)
        );

        String request = multiQueryRequest.serialize();

        var response = dgsClient.getGraphQlClient().document(request).executeSync();

        /** return whole data */
        ObjectMapper objectMapper = new ObjectMapper();

        var clientResponse = objectMapper.convertValue(response.getData(), ClientResponse.class);

        return clientResponse.client1() + ", " + clientResponse.client2();
    }
}