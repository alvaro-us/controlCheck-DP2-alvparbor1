package acme.features.administrator.initialConfiguration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorInitialConfigurationRepository extends AbstractRepository{

	@Query("select a from InitialConfiguration a")
	InitialConfiguration findConfiguration();
}
