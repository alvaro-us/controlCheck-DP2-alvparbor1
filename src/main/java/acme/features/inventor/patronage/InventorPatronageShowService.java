package acme.features.inventor.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.patronages.Patronage;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPatronageShowService implements AbstractShowService<Inventor, Patronage> {

	@Autowired
	protected InventorPatronageRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int patronageId;
		Patronage patronage;

		patronageId = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(patronageId);
		result = (patronage.getInventor().getId() == request.getPrincipal().getActiveRoleId()) && patronage.isPublished();
		
		return result;
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		assert request != null;
		Patronage result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOnePatronageById(id);


		return result;
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String defaultCurrency;
		MoneyExchange exchange;
		boolean isExchange;
		
		defaultCurrency = this.repository.defaultCurrency();
		exchange = this.exchangeService.computeMoneyExchange(entity.getBudget(), defaultCurrency);
		isExchange = ! entity.getBudget().getCurrency().equals(exchange.target.getCurrency());

		request.unbind(entity, model, "status", "code", "legalStuff", "budget", "creationDate", "startDate", "endDate", "moreInfo");

		model.setAttribute("patronId", entity.getPatron().getUserAccount().getId());
		model.setAttribute("patronageId", entity.getId());
		
		model.setAttribute("exchange", exchange.target);
		model.setAttribute("isExchange", isExchange);

	}

}