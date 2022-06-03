package acme.features.inventor.quantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.items.Item;
import acme.entities.toolkits.Quantity;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorQuantityShowService implements AbstractShowService<Inventor, Quantity> {
	
	@Autowired
	protected InventorQuantityRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Quantity> request) {
		assert request != null;
		boolean result;
		//Acceso solo si está publicado o si pertenece al inventor autenticado
		final Quantity quantity;
		Item itemToShow; 
		Inventor inventor;
		int quantityId;
		
		quantityId = request.getModel().getInteger("id");
		quantity = this.repository.findOneQuantityById(quantityId); 
		itemToShow = quantity.getItem();
		inventor = itemToShow.getInventor();
		
		result = !itemToShow.isDraftMode() || request.isPrincipal(inventor);
		
		return result;
	}

	@Override
	public Quantity findOne(final Request<Quantity> request) {
		assert request != null;
		Quantity result; 
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneQuantityById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Quantity> request, final Quantity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String defaultCurrency;
		MoneyExchange exchange;
		boolean isExchange;
		
		defaultCurrency = this.repository.defaultCurrency();
		exchange = this.exchangeService.computeMoneyExchange(entity.getItem().getRetailPrice(), defaultCurrency);
		isExchange = ! entity.getItem().getRetailPrice().getCurrency().equals(exchange.target.getCurrency());
		
		request.unbind(entity, model, "amount","item.name", "item.code", "item.technology", "item.description", "item.retailPrice", "item.moreInfo", "item.itemType", "item.draftMode");
		model.setAttribute("exchange", exchange.target);
		model.setAttribute("isExchange", isExchange);
		model.setAttribute("showItem", !entity.getToolkit().isDraftMode());
		model.setAttribute("itemSelect", entity.getItem().getName()+" "+entity.getItem().getCode());
	}

}
