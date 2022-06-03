package acme.features.inventor.patronageReport;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.patronage.report.PatronageReport;
import acme.entities.patronages.Patronage;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorPatronageReportRepository extends AbstractRepository {

	@Query("select pr from PatronageReport pr where pr.patronage.id = :id")
	Collection<PatronageReport> findPatronageReportByPatronageId(int id);

	@Query("select pr from PatronageReport pr where pr.id = :id")
	PatronageReport findPatronageReportById(int id);
	
	@Query("select p from Patronage p where p.id = :id")
	Patronage findOnePatronageById(int id);
	
	@Query("select pr from PatronageReport pr where pr.sequenceNumber = :sequenceNumber")
	PatronageReport findPatronageReportBySequenceNumber(String sequenceNumber);
	
	@Query("select initialConfiguration from InitialConfiguration initialConfiguration")
    InitialConfiguration getSystemCofig();

}
