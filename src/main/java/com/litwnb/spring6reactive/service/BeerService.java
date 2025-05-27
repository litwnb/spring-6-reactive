package com.litwnb.spring6reactive.service;

import com.litwnb.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(Integer beerId);
}
