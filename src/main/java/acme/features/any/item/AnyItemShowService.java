package acme.features.any.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.items.Item;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;

@Service
public class AnyItemShowService implements AbstractShowService<Any, Item> {
	
	@Autowired
	protected AnyItemRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		Item result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneItemById(id);
		
		return !result.isDraftMode();
	}

	@Override
	public Item findOne(final Request<Item> request) {
		assert request != null;
		Item result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneItemById(id);
			
		return result;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String defaultCurrency;
		MoneyExchange exchange;
		boolean isExchange;
		
		defaultCurrency = this.repository.defaultCurrency();
		exchange = this.exchangeService.computeMoneyExchange(entity.getRetailPrice(), defaultCurrency);
		isExchange = ! entity.getRetailPrice().getCurrency().equals(exchange.target.getCurrency());
		
		request.unbind(entity, model, "name", "code", "technology", "description", "retailPrice", "moreInfo", "itemType");
		model.setAttribute("exchange", exchange.target);
		model.setAttribute("isExchange", isExchange);
		
	}

}
