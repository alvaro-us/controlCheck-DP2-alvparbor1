package acme.features.inventor.toolkit;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorToolkitRepository extends AbstractRepository {
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration getSystemCofig();
	
	@Query("select i from Toolkit i where i.id = :id")
	Toolkit findOneToolkitById(int id);
	
	@Query("select i from Toolkit i where i.code = :code")
	Toolkit findOneToolkitByCode(String code);
	 
	//Devuelve todos los toolkit de un determinado inventor
	@Query("select c from Toolkit c where c.inventor.userAccount.id = :principalId")
	Collection<Toolkit> findToolkitsByInventor(Integer principalId);
	
	@Query("select i from Quantity i where i.toolkit.id = :toolkitId")
	Collection<Quantity> findManyQuantitiesByToolkitId(int toolkitId);
	
	@Query("select c.item.retailPrice.currency, c.item.retailPrice.amount, c.amount from Quantity c where c.toolkit.id = :toolkitId")
	List<String> getPricesOfItemsOfAToolkit(int toolkitId);
	
	@Query("select c.defaultCurrency from InitialConfiguration c")
	String defaultCurrency();

	@Query("select i from Inventor i where i.id = :activeRoleId")
	Inventor findOneInventorById(int activeRoleId);
	
}
