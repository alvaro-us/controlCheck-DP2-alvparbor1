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

package acme.features.patron.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import acme.entities.patronages.PatronageStatus;
import acme.forms.PatronDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Patron;

@Service
public class PatronDashboardShowService implements AbstractShowService<Patron, PatronDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronDashboardRepository repository;

	// AbstractShowService<Patron, PatronDashboard> interface ----------------


	@Override
	public boolean authorise(final Request<PatronDashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public PatronDashboard findOne(final Request<PatronDashboard> request) {
		assert request != null;
		
		int patronId;
		final int	proposedPatronages;
		final int	acceptedPatronages;
		final int	deniedPatronages;
		
		final Map<Pair<PatronageStatus,String>,Double> averageBudget;
		final Map<Pair<PatronageStatus,String>,Double> deviationBudget;
		final Map<Pair<PatronageStatus,String>,Double> minimumBudget;
		final Map<Pair<PatronageStatus,String>,Double> maximumBudget;
		
		patronId = request.getPrincipal().getActiveRoleId();
		
		proposedPatronages = this.repository.proposedPatronages(patronId);
		acceptedPatronages = this.repository.acceptedPatronages(patronId);
		deniedPatronages = this.repository.deniedPatronages(patronId);
		
		averageBudget = this.parse(this.repository.averageBudget(patronId));
		deviationBudget = this.parse(this.repository.deviationBudget(patronId));
		minimumBudget = this.parse(this.repository.minimunBudget(patronId));	
		maximumBudget = this.parse(this.repository.maximunBudget(patronId));
		
		final PatronDashboard result = new PatronDashboard();
		result.setProposedPatronages(proposedPatronages);
		result.setAcceptedPatronages(acceptedPatronages);
		result.setDeniedPatronages(deniedPatronages);
		result.setAverageBudget(averageBudget);
		result.setDeviationBudget(deviationBudget);
		result.setMinimunBudget(minimumBudget);
		result.setMaximunBudget(maximumBudget);
		return result;
	}
	
	

	@Override
	public void unbind(final Request<PatronDashboard> request, final PatronDashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		final List<String> currencies = new ArrayList<>();
		
		request.unbind(entity, model,"proposedPatronages", "acceptedPatronages", 
			"deniedPatronages","averageBudget", "deviationBudget","minimunBudget", "maximunBudget");
		
		for(final String c: this.repository.acceptedCurrencies().split(",")) {
			currencies.add(c.trim());
		}
		
		model.setAttribute("currencies", currencies);
		model.setAttribute("status", PatronageStatus.values());

	}
	
	//Auxiliary methods-----------------------------------
	
	private Map<Pair<PatronageStatus,String>, Double>parse(final List<String> list) {
		
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

}
