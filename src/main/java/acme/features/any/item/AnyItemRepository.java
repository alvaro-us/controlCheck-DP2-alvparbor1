package acme.features.any.item;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.items.Item;
import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyItemRepository extends AbstractRepository {
	
	@Query("select i from Item i where i.id = :id")
	Item findOneItemById(int id);
	
	@Query("select i from Toolkit i where i.id = :id")
	Toolkit findOneToolkitById(int id);
	
	@Query("select c from Item c where c.draftMode = false")
	Collection<Item> findAllItems();
	
	@Query("select c.item from Quantity c where c.toolkit.id = :toolkitId")
	Collection<Item> findManyItemsByToolkitId(int toolkitId);
	
	@Query("select c.defaultCurrency from InitialConfiguration c")
	String defaultCurrency();
	
	@Query("select i from Quantity i where i.toolkit.id = :toolkitId and i.item.id = :itemId")
	Quantity findOneQuantityByToolkitIdAndItemId(int toolkitId, int itemId);
	

}
