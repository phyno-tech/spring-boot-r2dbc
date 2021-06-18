package ke.co.phyno.learn.r2dbc.handler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ke.co.phyno.learn.r2dbc.data.http.request.CustomerRegisterRequest;
import ke.co.phyno.learn.r2dbc.data.http.response.HandlerResponse;
import ke.co.phyno.learn.r2dbc.store.model.CustomerProfile;
import ke.co.phyno.learn.r2dbc.store.repo.CustomerProfileRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.logging.Level;

@Log
@Api(tags = "Customer")
@RestController
@RequestMapping(path = "customer")
public class CustomerHandler {
    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @ApiOperation(
            value = "Register new customer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            path = {"/register"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<HandlerResponse>> register(
            @RequestBody CustomerRegisterRequest body
    ) {
        CustomerProfile profile = new CustomerProfile();
        profile.setLogId(UUID.randomUUID());
        profile.setFirstName(body.getFirstName());
        profile.setLastName(body.getLastName());
        profile.setCreationDate(Timestamp.from(Instant.now()));
        return this.customerProfileRepository.save(profile)
                .mapNotNull(customerProfile -> {
                    log.log(Level.INFO, String.format("Customer registered [ %s ]", customerProfile));
                    return ResponseEntity.status(HttpStatus.OK)
                            .cacheControl(CacheControl.noCache())
                            .body(HandlerResponse.builder().code(HttpStatus.OK.value()).message("Success").build());
                })
                .onErrorResume(e -> {
                    log.log(Level.SEVERE, String.format("Register customer error [ %s ]", body), e);
                    return Mono.justOrEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .cacheControl(CacheControl.noCache())
                            .body(HandlerResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Sorry, an error encountered. Please try again later").build()));
                });
    }

    @ApiOperation(
            value = "Get all customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            path = {"/all"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<HandlerResponse>> all() {
        return this.customerProfileRepository.findAll(Sort.by(Sort.Order.desc("creationDate")))
                .collectList()
                .mapNotNull(customerProfiles -> ResponseEntity.status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(HandlerResponse.builder().code(HttpStatus.OK.value()).message("Success").data(customerProfiles).build()))
                .onErrorResume(e -> {
                    log.log(Level.SEVERE, "Get customers error", e);
                    return Mono.justOrEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .cacheControl(CacheControl.noCache())
                            .body(HandlerResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Sorry, an error encountered. Please try again later").build()));
                });
    }
}
