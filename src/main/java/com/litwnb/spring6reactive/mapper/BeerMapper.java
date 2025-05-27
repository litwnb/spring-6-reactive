package com.litwnb.spring6reactive.mapper;

import com.litwnb.spring6reactive.domain.Beer;
import com.litwnb.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
