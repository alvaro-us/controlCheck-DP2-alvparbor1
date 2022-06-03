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

package acme.features.administrator.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {
	
	
	//PATRONAGE
	@Query("select count(p) from Patronage p where p.status = 0")
	int totalNumberOfProposedPatronages();
	
	@Query("select count(p) from Patronage p where p.status = 1")
	int totalNumberOfAcceptedPatronages();
	
	@Query("select count(p) from Patronage p where p.status = 2")
	int totalNumberOfDeniedPatronages();
	
	@Query("select p.status, p.budget.currency, avg(p.budget.amount) from Patronage p  group by p.status, p.budget.currency")
	List<String> averageBudgetOfPatronagesGroupedByStatusAndCurrency();
	
	@Query("select p.status, p.budget.currency, stddev(p.budget.amount) from Patronage p group by p.status, p.budget.currency")
	List<String> deviationBudgetOfPatronagesGroupedByStatusAndCurrency();
	
	@Query("select p.status, p.budget.currency, min(p.budget.amount) from Patronage  p group by p.status, p.budget.currency")
	List<String> minimunBudgetOfPatronagesGroupedByStatusAndCurrency();
	
	@Query("select p.status, p.budget.currency, max(p.budget.amount) from Patronage p group by p.status, p.budget.currency")
	List<String> maximunBudgetOfPatronagesGroupedByStatusAndCurrency();
	
	@Query("select cd.acceptedCurrencies from InitialConfiguration cd")
	String acceptedCurrencies();
	
	
	
	//TOOL
	@Query("select count(t) from Item t where t.itemType = acme.entities.items.ItemType.TOOL")
	Integer totalNumberOfTools();
	
	@Query("select t.retailPrice.currency, avg(t.retailPrice.amount) from Item t where t.itemType = acme.entities.items.ItemType.TOOL group by t.retailPrice.currency")
	List<String> averageBudgetOfToolGroupedByCurrency();
	
	@Query("select t.retailPrice.currency, stddev(t.retailPrice.amount) from Item t where t.itemType = acme.entities.items.ItemType.TOOL group by t.retailPrice.currency")
	List<String> deviationBudgetOfToolGroupedByCurrency();
	
	@Query("select t.retailPrice.currency, min(t.retailPrice.amount) from Item  t where t.itemType = acme.entities.items.ItemType.TOOL group by t.retailPrice.currency")
	List<String> minimunBudgetOfToolGroupedByCurrency();
	
	@Query("select  t.retailPrice.currency, max(t.retailPrice.amount) from Item t where t.itemType = acme.entities.items.ItemType.TOOL group by t.retailPrice.currency")
	List<String> maximunBudgetOfToolGroupedByCurrency();
	
	
	//CHIMPUM
	@Query("select 1.0 * count(a) / (select count(b) from Item b) from Chimpum a")
	Double getRatioOfItemsWithChimpum();
	
	@Query("select t.budget.currency, avg(t.budget.amount) from Chimpum t  group by t.budget.currency")
	List<String> getAverageBudgetOfChimpumGroupedByCurrency();
	
	@Query("select t.budget.currency, stddev(t.budget.amount) from Chimpum t group by t.budget.currency")
	List<String> getDeviationBudgetOfChimpumGroupedByCurrency();
	
	@Query("select t.budget.currency, min(t.budget.amount) from Chimpum  t group by t.budget.currency")
	List<String> getMinimunBudgetOfChimpumGroupedByCurrency();
	
	@Query("select  t.budget.currency, max(t.budget.amount) from Chimpum t  group by t.budget.currency")
	List<String> getMaximunBudgetOfChimpumGroupedByCurrency();
	
	//COMPONENT
	@Query("select count(c) from Item c where c.itemType = acme.entities.items.ItemType.COMPONENT")
	Integer totalNumberOfComponents();
	
	@Query("select c.technology ,c.retailPrice.currency, avg(c.retailPrice.amount) from Item c where c.itemType = acme.entities.items.ItemType.COMPONENT group by c.technology, c.retailPrice.currency")
	List<String> averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency();
	
	@Query("select c.technology ,c.retailPrice.currency, stddev(c.retailPrice.amount) from Item c where c.itemType = acme.entities.items.ItemType.COMPONENT group by c.technology, c.retailPrice.currency")
	List<String> deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency();
	
	@Query("select c.technology ,c.retailPrice.currency, min(c.retailPrice.amount) from Item  c where c.itemType = acme.entities.items.ItemType.COMPONENT group by c.technology ,c.retailPrice.currency")
	List<String> minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency();
	
	@Query("select c.technology ,c.retailPrice.currency, max(c.retailPrice.amount) from Item c where c.itemType = acme.entities.items.ItemType.COMPONENT group by c.technology, c.retailPrice.currency")
	List<String> maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency();
	
	@Query("select distinct c.technology from Item c where c.itemType = acme.entities.items.ItemType.COMPONENT")
	List<String> technologiesOfComponents();

}

