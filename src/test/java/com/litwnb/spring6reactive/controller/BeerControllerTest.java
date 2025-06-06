package com.litwnb.spring6reactive.controller;

import com.litwnb.spring6reactive.domain.Beer;
import com.litwnb.spring6reactive.model.BeerDTO;
import com.litwnb.spring6reactive.repository.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(6)
    void testDeleteBeer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteBeerNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testUpdateBeer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(4)
    void testUpdateBeerBadRequest() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerStyle("");

        webTestClient.mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(testBeer), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateBeerNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 999)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testCreateBeer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http:/localhost:8080/api/v2/beer/4");
    }

    @Test
    void testCreateBeerBadData() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");

        webTestClient.mutateWith(mockOAuth2Login())
                .post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(1)
    void testGetById() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    void testGetByIdNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(2)
    void testListBeers() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }
}