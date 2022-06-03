package acme.features.administrator.announcement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorAnnouncementRepository extends AbstractRepository{
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration getSystemCofig();

}
