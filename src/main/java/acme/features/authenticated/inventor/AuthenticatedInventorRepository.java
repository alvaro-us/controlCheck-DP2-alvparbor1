package acme.features.authenticated.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;


@Repository
public interface AuthenticatedInventorRepository  extends AbstractRepository {
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration getSystemCofig();
	
	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select i from Inventor i where i.userAccount.id = :id")
	Inventor findOneInventorByUserAccountId(int id);

}
