package acme.features.any.userAccounts;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyUserAccountRepository extends AbstractRepository {
	
	@Query("SELECT ua FROM UserAccount ua")
	Collection<UserAccount> findAllUserAccounts();
	
	@Query("SELECT ua FROM UserAccount ua JOIN FETCH ua.roles r WHERE "
		+ "ua.enabled= true AND Administrator NOT IN (SELECT type(r2) FROM ua.roles r2)")
	Collection<UserAccount> findPatronInventorUserAccounts();
	
	@Query("SELECT ua FROM UserAccount ua WHERE ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select ua from UserAccount ua where ua.username = :username")
	UserAccount findOneUserAccountByUsername(String username);
}
