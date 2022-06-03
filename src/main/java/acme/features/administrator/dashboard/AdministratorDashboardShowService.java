/*
 * AdministratorDashboardShowService.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import acme.entities.patronages.PatronageStatus;
import acme.forms.AdministratorDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<AdministratorDashboard> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public AdministratorDashboard findOne(final Request<AdministratorDashboard> request) {
		assert request != null;
		
		//COMPONENT DECLARACION
		final Integer totalNumberOfComponents; 
		final Map<Pair<String, String>, Double> averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency;
		final Map<Pair<String, String>, Double> deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency;
		final Map<Pair<String,String>, Double> minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency;
		final Map<Pair<String,String>, Double> maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency;
		
		//TOOL DECLARACION
		final Integer totalNumberOfTools;
		final Map<String,Double> averageBudgetOfToolGroupedByCurrency;
		final Map<String,Double> deviationBudgetOfToolGroupedByCurrency;
		final Map<String,Double> minimunBudgetOfToolGroupedByCurrency;
		final Map<String,Double> maximunBudgetOfToolGroupedByCurrency;
		
		
		//CHIMPUM DECLARACION
		final Double ratioOfItemsWithChimpum;
		final Map<String,Double> averageBudgetOfChimpumGroupedByCurrency;
		final Map<String,Double> deviationBudgetOfChimpumGroupedByCurrency;
		final Map<String,Double> minimunBudgetOfChimpumGroupedByCurrency;
		final Map<String,Double> maximunBudgetOfChimpumGroupedByCurrency;
		
		//PATRONAGE DECLARACION
		final AdministratorDashboard result;
		
		final int	totalNumberOfProposedPatronages;
		final int	totalNumberOfAcceptedPatronages;
		final int	totalNumberOfDeniedPatronages;
		
		final Map<Pair<PatronageStatus,String>,Double> averageBudgetOfPatronagesGroupedByStatusAndCurrency;
		final Map<Pair<PatronageStatus,String>,Double> deviationBudgetOfPatronagesGroupedByStatusAndCurrency;
		final Map<Pair<PatronageStatus,String>,Double> minimunBudgetOfPatronagesGroupedByStatusAndCurrency;
		final Map<Pair<PatronageStatus,String>,Double> maximunBudgetOfPatronagesGroupedByStatusAndCurrency;
		
		

		//COMPONENT INICIALIZACION
		totalNumberOfComponents = this.repository.totalNumberOfComponents();
		averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency = this.parseComponent(
			this.repository.averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency());
		deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency = this.parseComponent(
			this.repository.deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency());
		minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency = this.parseComponent(
			this.repository.minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency());
		maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency = this.parseComponent(
			this.repository.maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency());
		
		//TOOL INICIALIZACION
		totalNumberOfTools = this.repository.totalNumberOfTools();
		averageBudgetOfToolGroupedByCurrency = this.parseTool(this.repository.averageBudgetOfToolGroupedByCurrency());
		deviationBudgetOfToolGroupedByCurrency = this.parseTool(this.repository.deviationBudgetOfToolGroupedByCurrency());
		minimunBudgetOfToolGroupedByCurrency = this.parseTool(this.repository.minimunBudgetOfToolGroupedByCurrency());
		maximunBudgetOfToolGroupedByCurrency = this.parseTool(this.repository.maximunBudgetOfToolGroupedByCurrency());
		
		//TOOL INICIALIZACION
		ratioOfItemsWithChimpum = this.repository.getRatioOfItemsWithChimpum();
		averageBudgetOfChimpumGroupedByCurrency = this.parseTool(this.repository.getAverageBudgetOfChimpumGroupedByCurrency());
		deviationBudgetOfChimpumGroupedByCurrency = this.parseTool(this.repository.getDeviationBudgetOfChimpumGroupedByCurrency());
		minimunBudgetOfChimpumGroupedByCurrency = this.parseTool(this.repository.getMinimunBudgetOfChimpumGroupedByCurrency());
		maximunBudgetOfChimpumGroupedByCurrency = this.parseTool(this.repository.getMaximunBudgetOfChimpumGroupedByCurrency());
	
		//PATRONAGE INICIALIZACION
		totalNumberOfProposedPatronages = this.repository.totalNumberOfProposedPatronages();
		totalNumberOfAcceptedPatronages = this.repository.totalNumberOfAcceptedPatronages();
		totalNumberOfDeniedPatronages = this.repository.totalNumberOfDeniedPatronages();
		
		averageBudgetOfPatronagesGroupedByStatusAndCurrency = this.parsePatronage(
			this.repository.averageBudgetOfPatronagesGroupedByStatusAndCurrency());
		deviationBudgetOfPatronagesGroupedByStatusAndCurrency = this.parsePatronage(
			this.repository.deviationBudgetOfPatronagesGroupedByStatusAndCurrency());
		minimunBudgetOfPatronagesGroupedByStatusAndCurrency = this.parsePatronage(
			this.repository.minimunBudgetOfPatronagesGroupedByStatusAndCurrency());
		maximunBudgetOfPatronagesGroupedByStatusAndCurrency = this.parsePatronage(
			this.repository.maximunBudgetOfPatronagesGroupedByStatusAndCurrency());
		
		
		result = new AdministratorDashboard();
		
		
		//COMPONENT ASIGNACION
		result.setTotalNumberOfComponents(totalNumberOfComponents);
		result.setAverageRetailPriceOfComponentsGroupedByTechnologyAndCurrency(averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency);
		result.setDeviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency(deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency);
		result.setMinimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency(minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency);
		result.setMaximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency(maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency);
				
		
		//TOOL ASIGNACION
		result.setTotalNumberOfTools(totalNumberOfTools);
		result.setAverageBudgetOfToolGroupedByCurrency(averageBudgetOfToolGroupedByCurrency);
		result.setDeviationBudgetOfToolGroupedByCurrency(deviationBudgetOfToolGroupedByCurrency);
		result.setMinimunBudgetOfToolGroupedByCurrency(minimunBudgetOfToolGroupedByCurrency);
		result.setMaximunBudgetOfToolGroupedByCurrency(maximunBudgetOfToolGroupedByCurrency);
		
		//CHIMPUM ASIGNACION
		result.setRatioOfItemsWithChimpum(ratioOfItemsWithChimpum);;
		result.setAverageBudgetOfChimpumGroupedByCurrency(averageBudgetOfChimpumGroupedByCurrency);
		result.setDeviationBudgetOfChimpumGroupedByCurrency(deviationBudgetOfChimpumGroupedByCurrency);
		result.setMinimunBudgetOfChimpumGroupedByCurrency(minimunBudgetOfChimpumGroupedByCurrency);
		result.setMaximunBudgetOfChimpumGroupedByCurrency(maximunBudgetOfChimpumGroupedByCurrency);
		
		//PATRONAGE ASIGNACION
		
		result.setTotalNumberOfProposedPatronages(totalNumberOfProposedPatronages);
		result.setTotalNumberOfAcceptedPatronages(totalNumberOfAcceptedPatronages);
		result.setTotalNumberOfDeniedPatronages(totalNumberOfDeniedPatronages);
		result.setAverageBudgetOfPatronagesGroupedByStatusAndCurrency(averageBudgetOfPatronagesGroupedByStatusAndCurrency);
		result.setDeviationBudgetOfPatronagesGroupedByStatusAndCurrency(deviationBudgetOfPatronagesGroupedByStatusAndCurrency);
		result.setMinimunBudgetOfPatronagesGroupedByStatusAndCurrency(minimunBudgetOfPatronagesGroupedByStatusAndCurrency);
		result.setMaximunBudgetOfPatronagesGroupedByStatusAndCurrency(maximunBudgetOfPatronagesGroupedByStatusAndCurrency);

		
		return result;
	}
	

	@Override
	public void unbind(final Request<AdministratorDashboard> request, final AdministratorDashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final List<String> acceptedCurrencies;
		final List<String> technologiesOfComponent;
		
		technologiesOfComponent = this.repository.technologiesOfComponents();
		acceptedCurrencies = new ArrayList<>();
		for(final String currency: this.repository.acceptedCurrencies().split(",")) {
			acceptedCurrencies.add(currency.trim());
		}
		
		request.unbind(entity, model,   
			"totalNumberOfComponents", "averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency", 
			"deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency",
			"minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency", "maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency",
			
			"totalNumberOfTools", "averageBudgetOfToolGroupedByCurrency", "deviationBudgetOfToolGroupedByCurrency", 
			"minimunBudgetOfToolGroupedByCurrency", "maximunBudgetOfToolGroupedByCurrency",
			
			"ratioOfItemsWithChimpum", "averageBudgetOfChimpumGroupedByCurrency", "deviationBudgetOfChimpumGroupedByCurrency", 
			"minimunBudgetOfChimpumGroupedByCurrency", "maximunBudgetOfChimpumGroupedByCurrency",
			
			"totalNumberOfProposedPatronages", "totalNumberOfAcceptedPatronages", "totalNumberOfDeniedPatronages",
			"averageBudgetOfPatronagesGroupedByStatusAndCurrency", "deviationBudgetOfPatronagesGroupedByStatusAndCurrency",
			"minimunBudgetOfPatronagesGroupedByStatusAndCurrency", "maximunBudgetOfPatronagesGroupedByStatusAndCurrency");
		
		model.setAttribute("statuses", PatronageStatus.values());
		model.setAttribute("currencies", acceptedCurrencies);
		model.setAttribute("technologies", technologiesOfComponent);
	}
	
	//Ancillary methods
	
	private Map<Pair<String,String>, Double>parseComponent(final List<String> list) {
		
		final Map<Pair<String,String>, Double> result = new HashMap<>();
		 
		for(final String s: list) {

			final String[] split = s.split(",");

			final String technology = split[0].trim();
			final String currency = split[1].trim();
			final Double retailPrice = Double.valueOf(split[2]);
			
			result.put(Pair.of(technology, currency), retailPrice);
		}
		
		return result;
	}
	
	private Map<Pair<PatronageStatus,String>, Double>parsePatronage(final List<String> list) {
		
		final Map<Pair<PatronageStatus,String>, Double> result = new HashMap<>();
		
		for(final String s: list) {
			final String[] split = s.split(",");

			final PatronageStatus patronageStatus = PatronageStatus.valueOf(split[0]);
			final String currency = split[1];
			final Double budget = Double.valueOf(split[2]);
			
			result.put(Pair.of(patronageStatus, currency), budget);
		}
		
		return result;
	}
	
	
	private Map<String, Double> parseTool(final List<String> statisticList) {
		final Map<String, Double> result;
		
		result = new HashMap<>();
		for(final String entry: statisticList) {
			// entry -> currency,value
			final String[] splitEntry = entry.split(",");
			String currency;
			Double value;
			
			currency = splitEntry[0].trim();
			value = Double.valueOf(splitEntry[1].trim());
			
			result.put(currency, value);
		}
		
		return result;
	}
}
