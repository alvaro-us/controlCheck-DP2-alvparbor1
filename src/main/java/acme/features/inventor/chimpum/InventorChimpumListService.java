package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.chimpums.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorChimpumListService implements AbstractListService<Inventor, Chimpum> {

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;


	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		return true;
	}

	@Override
	public Collection<Chimpum> findMany(final Request<Chimpum> request) {
		assert request != null;
		final Collection<Chimpum> res = this.repository.findChimpumsByInventorId(request.getPrincipal().getActiveRoleId());
		return res;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title","budget", "startDate","endDate");

	}

}