/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.patron.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface PatronDashboardRepository extends AbstractRepository {
	
	@Query("SELECT count(p) FROM Patronage p WHERE p.patron.id = :patronId AND p.status = 0")
	int proposedPatronages(int patronId);
	
	@Query("SELECT count(p) FROM Patronage p WHERE p.patron.id = :patronId AND p.status = 1")
	int acceptedPatronages(int patronId);
	
	@Query("SELECT count(p) FROM Patronage p WHERE p.patron.id = :patronId AND p.status = 2")
	int deniedPatronages(int patronId);
	
	@Query("SELECT p.status, p.budget.currency, avg(p.budget.amount) FROM Patronage p WHERE p.patron.id = :patronId GROUP BY p.status, p.budget.currency")
	List<String> averageBudget(int patronId);
	
	@Query("SELECT p.status, p.budget.currency, stddev(p.budget.amount) FROM Patronage p WHERE p.patron.id = :patronId GROUP BY p.status, p.budget.currency")
	List<String> deviationBudget(int patronId);
	
	@Query("SELECT p.status, p.budget.currency, min(p.budget.amount) FROM Patronage p WHERE p.patron.id = :patronId GROUP BY p.status, p.budget.currency")
	List<String> minimunBudget(int patronId);
	
	@Query("SELECT p.status, p.budget.currency, max(p.budget.amount) FROM Patronage p WHERE p.patron.id = :patronId GROUP BY p.status, p.budget.currency")
	List<String> maximunBudget(int patronId);
	
	@Query("SELECT ic.acceptedCurrencies FROM InitialConfiguration ic")
	String acceptedCurrencies();

}
