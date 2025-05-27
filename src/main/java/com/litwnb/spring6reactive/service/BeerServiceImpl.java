package com.litwnb.spring6reactive.service;

import com.litwnb.spring6reactive.mapper.BeerMapper;
import com.litwnb.spring6reactive.model.BeerDTO;
import com.litwnb.spring6reactive.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDto);
    }
}
