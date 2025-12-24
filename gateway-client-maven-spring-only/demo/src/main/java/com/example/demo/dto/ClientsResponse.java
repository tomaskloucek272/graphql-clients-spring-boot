package com.example.demo.dto;

import com.tomask.graphql.core.generated.types.Client;

/** DTO matching for aliased queries in one request (client1, client2 aliases) */
public record ClientsResponse(Client client1, Client client2) {}