package acme.features.any.toolkit;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.items.Item;
import acme.entities.toolkits.Toolkit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyToolkitRepository extends AbstractRepository {
	
	@Query("select i from Toolkit i where i.id = :id")
	Toolkit findOneToolkitById(int id);
	 
	@Query("select c from Toolkit c where c.draftMode = false")
	Collection<Toolkit> findAllToolkits();
	
	@Query("select distinct i.item from Quantity i where i.toolkit.id = :toolkitId")
	Collection<Item> findManyItemsByToolkitId(int toolkitId);
	
	@Query("select distinct c.toolkit from Quantity c where c.item.id = :itemId and c.toolkit.draftMode = false")
	Collection<Toolkit> findManyToolkitsByItem(int itemId);
	
	@Query("select c.item.retailPrice.currency, c.item.retailPrice.amount, c.amount from Quantity c where c.toolkit.id = :toolkitId")
	List<String> getPricesOfItemsOfAToolkit(int toolkitId);
	
	@Query("select c.defaultCurrency from InitialConfiguration c")
	String defaultCurrency();
	
	
	
	

}
