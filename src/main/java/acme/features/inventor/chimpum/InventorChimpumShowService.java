package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.chimpums.Chimpum;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorChimpumShowService implements AbstractShowService<Inventor, Chimpum> {

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		final int id = request.getModel().getInteger("id");
		final Chimpum chimpum = this.repository.findOneChimpumById(id);
		final boolean res = chimpum.getItem().getInventor().getId() == request.getPrincipal().getActiveRoleId();
		return res;
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		final int id = request.getModel().getInteger("id");
		final Chimpum chimpum = this.repository.findOneChimpumById(id);
		return chimpum;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String defaultCurrency = this.repository.findDefaultCurrency();
		final MoneyExchange me = this.exchangeService.computeMoneyExchange(entity.getBudget(), defaultCurrency);
		final boolean res = ! entity.getBudget().getCurrency().equals(me.target.getCurrency());
		
		request.unbind(entity, model, "code", "title", "description", "budget", "creationMoment", "startDate", "endDate", "moreInfo");
		
		model.setAttribute("exange", me.target);
		model.setAttribute("isExange", res);
		model.setAttribute("itemId", entity.getItem().getId());
		model.setAttribute("pattern", entity.getCode().substring(0,3));
	}

}