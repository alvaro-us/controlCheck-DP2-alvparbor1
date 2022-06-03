package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chimpums.Chimpum;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.items.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorChimpumRepository extends AbstractRepository {

	@Query("select i from Item i where i.id = :id")
	Item findOneItemById(int id);

	@Query("select c from Chimpum c where c.id = :id")
	Chimpum findOneChimpumById(int id);

	
	@Query("select c from Chimpum c where c.item.inventor.id =:id")
	Collection<Chimpum> findChimpumsByInventorId(int id);
	
	@Query("select c.defaultCurrency from InitialConfiguration c")
	String findDefaultCurrency();
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration findInitialCofiguration();
	
	@Query("select p from Chimpum p where p.code = :code")
	Chimpum findOneChimpumByCode(String code);
	
	@Query("SELECT ic.acceptedCurrencies FROM InitialConfiguration ic")
	String acceptedCurrencies();
	

}