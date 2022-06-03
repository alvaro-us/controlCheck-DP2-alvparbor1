package acme.features.authenticated.moneyExchange;

import org.springframework.data.jpa.repository.Query;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedMoneyExchangeRepository extends AbstractRepository{

	@Query("select a from InitialConfiguration a")
	InitialConfiguration findInitialConfiguration();
}
