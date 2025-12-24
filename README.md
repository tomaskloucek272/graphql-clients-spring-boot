# Spring GraphQL Sample Clients (DGS & Native)

This is a [follow up of this repository](https://github.com/tomaskloucek272/graphql-federation) and all samples are expecting mentioned NestJS gateway running at http://localhost:3000 
and contains Spring Boot based graphql clients created with using:

## 1) Netflix DGS Gradle codegen plugin
<img width="48" height="48" alt="image" src="https://github.com/user-attachments/assets/59e62a39-560c-463e-a2c2-472ca70ff6ca" />

[Netflix DGS CodeGen](https://github.com/Netflix/dgs-codegen)
   
## 2) Deweyjose DGS Maven codegen plugin

<img width="48" height="48" alt="image" src="https://github.com/user-attachments/assets/77141c4f-3fe9-49ec-8207-209d8cbc3761" />

[Maven DGS GraphQL](https://github.com/deweyjose/graphqlcodegen)
   
## 3) Spring ONLY WebFlux GraphQl client (no codegen)

<img width="48" height="48" alt="image" src="https://github.com/user-attachments/assets/694779c3-2ca3-45ce-bfa7-6801d34ec68f" />

[Spring GraphQL](https://docs.spring.io/spring-graphql/reference/client.html)

All three samples aims to demonstrate howto **send two aliased queries in one GraphQl request** 

```
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
```
with output like in the third case:

<img width="377" height="1360" alt="image" src="https://github.com/user-attachments/assets/2fdae1ad-2765-412a-959c-b6946c4c8048" />




