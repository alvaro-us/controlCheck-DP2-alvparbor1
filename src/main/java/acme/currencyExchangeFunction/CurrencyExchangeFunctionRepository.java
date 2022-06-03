package acme.currencyExchangeFunction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.currencyExchanges.CurrencyExchange;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CurrencyExchangeFunctionRepository extends AbstractRepository {

	@Query("select c from CurrencyExchange c where c.source = :source and c.target = :target")
	CurrencyExchange findOneCurrencyExchangeBySourceAndTarget(String source, String target);

}