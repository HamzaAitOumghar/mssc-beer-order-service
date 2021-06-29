package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.beer.order.service.web.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerService beerService;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        final BeerOrderLineDto orderLineDto = beerOrderLineMapper.beerOrderLineToDto(line);
        final Optional<BeerDto> beerByUpc = beerService.getBeerByUpc(orderLineDto.getUpc());
        beerByUpc.ifPresent(b->{
            orderLineDto.setBeerName(b.getBeerName());
            orderLineDto.setBeerStyle(b.getBeerStyle());
            orderLineDto.setPrice(b.getPrice());
            orderLineDto.setBeerId(b.getId());
        });
        return orderLineDto;
    }


}
