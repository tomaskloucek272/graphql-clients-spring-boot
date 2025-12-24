package com.example.gateway_client.rest;

import com.example.gateway_client.codegen.client.ClientGraphQLQuery;
import com.example.gateway_client.codegen.client.ClientProjectionRoot;
import com.example.gateway_client.codegen.types.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.codegen.GraphQLMultiQueryRequest;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.DgsGraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
public class QueryController {

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

        /**
         * 1) Direct low level access (data, errors, extensions)...works, but uneffective
         *

        var response = dgsClient.getGraphQlClient().document(request).executeSync();
        var responseMap = (HashMap<String, Client>) response.getData();

        return String.valueOf(responseMap.get("client1"));
         */

        /** 2) using the GraphQLResponse parsers */
        ClientGraphQlResponse clientResponse =
                dgsClient.getGraphQlClient()
                        .document(request)
                        .executeSync();

        ObjectMapper mapper = new ObjectMapper();
        GraphQLResponse graphQLResponse =
                new GraphQLResponse(mapper.writeValueAsString(clientResponse));

        var client1 = graphQLResponse.extractValueAsObject("client1", Client.class).toString();
        var client2 = graphQLResponse.extractValueAsObject("client2", Client.class).toString();

        return List.of(client1, client2).toString();
    }
}
