package io.r2dbc.sample;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface ProviderRepository extends ReactiveCrudRepository<Provider, Long>
{
	@Query("select p from customer p where c.lastname = :lastname")
	Flux<Provider> findByLastname(String lastname);
}
