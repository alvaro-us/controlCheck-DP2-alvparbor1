package acme.features.any.chirp;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chirps.Chirp;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.repositories.AbstractRepository;


@Repository
public interface AnyChirpRepository extends AbstractRepository {
	


	@Query("select c from Chirp c where c.creationMoment > :deadline")
	Collection<Chirp> listChirpByMoment(Date deadline);
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration getSystemCofig();

}
