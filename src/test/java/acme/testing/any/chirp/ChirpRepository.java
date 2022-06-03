package acme.testing.any.chirp;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chirps.Chirp;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ChirpRepository extends AbstractRepository{
	

	@Query("select a from Chirp a where a.creationMoment between '1900/01/01' and '1900/01/31'")
	Collection<Chirp> findChirpsToPatch();

}
