package com.litwnb.spring6reactive.repository;

import com.litwnb.spring6reactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
