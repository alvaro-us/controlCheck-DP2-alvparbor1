package acme.features.inventor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.items.Item;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorItemShowService implements AbstractShowService<Inventor, Item> {
	
	@Autowired
	protected InventorItemRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		boolean result;
		//Acceso solo si está publicado o si pertenece al inventor autenticado
		Item toShow; 
		Inventor inventor;
		int itemId;
		
		itemId = request.getModel().getInteger("id");
		toShow = this.repository.findOneItemById(itemId); 
		inventor = toShow.getInventor();
		
		result = !toShow.isDraftMode() || request.isPrincipal(inventor);
		
		return result;
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
		
		
		request.unbind(entity, model, "name", "code", "technology", "description", "retailPrice", "moreInfo", "itemType", "draftMode");
		model.setAttribute("exchange", exchange.target);
		model.setAttribute("isExchange", isExchange);
		model.setAttribute("itemId", entity.getId());
		model.setAttribute("chimpum", this.repository.findOneChimpumByItemId(entity.getId()));
	}

}
