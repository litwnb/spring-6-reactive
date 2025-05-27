package com.litwnb.spring6reactive.service;

import com.litwnb.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;

public interface BeerService {
    Flux<BeerDTO> listBeers();
}
