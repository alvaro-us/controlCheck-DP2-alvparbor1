package acme.features.patron.patronage;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.patronages.Patronage;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;
import acme.roles.Patron;

@Repository
public interface PatronPatronageRepository extends AbstractRepository {
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration getSystemCofig();
	

	@Query("select p from Patronage p where p.id = :id")
	Patronage findOnePatronageById(int id);
	
	@Query("select p from Patron p where p.id = :id")
	Patron findOnePatronById(int id);
	
	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorById(int id);

	@Query("select p from Patronage p where p.patron.id = :id")
	Collection<Patronage> findPatronagesByPatronId(int id);
	
	@Query("select c.defaultCurrency from InitialConfiguration c")
	String defaultCurrency();
	
	@Query("select p from Patronage p where p.code = :code")
	Patronage findOnePatronageByCode(String code);
	
	@Query("select i from Inventor i")
	List<Inventor> findInventors();
	
	@Query("SELECT ic.acceptedCurrencies FROM InitialConfiguration ic")
	String acceptedCurrencies();
}
