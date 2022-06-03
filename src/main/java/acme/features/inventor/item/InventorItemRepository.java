package acme.features.inventor.item;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.items.Item;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorItemRepository extends AbstractRepository {
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration getSystemCofig();
	
 
	@Query("select i from Item i where i.id = :id")
	Item findOneItemById(int id);
	
	@Query("select i from Item i where i.code = :code")
	Item findOneItemByCode(String code);
	 
	@Query("select c from Item c where c.inventor.userAccount.id = :inventorId")
	Collection<Item> findManyItemsByInventorId(Integer inventorId);
	
	@Query("select c.item from Quantity c where c.toolkit.id = :toolkitId")
	Collection<Item> findManyItemsByToolkitId(int toolkitId);
	
	@Query("select c.defaultCurrency from InitialConfiguration c")
	String defaultCurrency();
	
	@Query("select c.acceptedCurrencies from InitialConfiguration c")
	String acceptedCurrencies();
	
	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorById(int id);
	
	@Query("SELECT c.id FROM Chimpum c WHERE c.item.id =:id")
	Integer findOneChimpumByItemId(int id);
	

}
