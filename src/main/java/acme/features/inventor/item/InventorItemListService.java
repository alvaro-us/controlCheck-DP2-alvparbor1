package acme.features.inventor.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorItemListService implements AbstractListService<Inventor, Item> {
	
	@Autowired
	protected InventorItemRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		return true;
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		assert request != null;
		
		
		final Collection<Item> result;
		final Integer inventorId = request.getPrincipal().getAccountId();
	
		result = this.repository.findManyItemsByInventorId(inventorId);
		

		return result;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "code", "draftMode");

	}

}
