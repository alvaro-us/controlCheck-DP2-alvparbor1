package acme.features.inventor.quantity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.items.Item;
import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorQuantityRepository extends AbstractRepository {

	@Query("select j from Quantity j where j.id = :id")
	Quantity findOneQuantityById(int id);
	
	@Query("select q from Quantity q where q.toolkit.id = :toolkitId and q.item.id = :itemId")
	Quantity findOneQuantityByToolkitIdAndItemId(int toolkitId, int itemId);
	
	@Query("select j from Item j where j.id = :id")
	Item findOneItemById(int id);
	
	@Query("select j from Toolkit j where j.id = :id")
	Toolkit findOneToolkitById(int id);
	
	@Query("select i from Quantity i where i.toolkit.id = :toolkitId")
	Collection<Quantity> findManyQuantitiesByToolkitId(int toolkitId);
	
	@Query("select c from Item c where c.draftMode = false and c.id not in (select q.item.id from Quantity q where q.toolkit.id = :toolkitId)")
	Collection<Item> findManyAvailableItems(Integer toolkitId);
	
	@Query("select c.defaultCurrency from InitialConfiguration c")
	String defaultCurrency();
}
