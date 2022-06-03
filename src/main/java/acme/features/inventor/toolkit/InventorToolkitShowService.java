package acme.features.inventor.toolkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorToolkitShowService implements AbstractShowService<Inventor, Toolkit> {
	
	@Autowired
	protected InventorToolkitRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;
		boolean result;
		Toolkit toShow; 
		int toolkitId;
		final int principalId;
		
		toolkitId = request.getModel().getInteger("id");
		principalId = request.getPrincipal().getAccountId();
		
		toShow = this.repository.findOneToolkitById(toolkitId); 
		result = toShow.getInventor().getUserAccount().getId() == principalId;
		
		return result;
	}

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		assert request != null;
		Toolkit result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneToolkitById(id);
		
		//calculamos el precio de esa toolkit
				final Money retailPrice;
				double totalPrice;
				MoneyExchange exchange;
				final String defaultCurrency;
				final List<String> priceAndQuantity;
				final List<Money> itemPrices;
				final List<Integer> itemQuantities;
				
				defaultCurrency = this.repository.defaultCurrency();
				priceAndQuantity = this.repository.getPricesOfItemsOfAToolkit(id);
				totalPrice = 0.;
				itemPrices = new ArrayList<Money>();
				itemQuantities = new ArrayList<Integer>();
				
				this.parse(priceAndQuantity, itemPrices, itemQuantities);
				
				for(int t=0; t<priceAndQuantity.size(); t++) {
					exchange = this.exchangeService.computeMoneyExchange(itemPrices.get(t), defaultCurrency);
					totalPrice += exchange.getTarget().getAmount() * itemQuantities.get(t);
				}
				
				retailPrice = new Money();
				retailPrice.setAmount(totalPrice);
				retailPrice.setCurrency(defaultCurrency);
				
				result.setRetailPrice(retailPrice);
		
		return result;
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		Collection<Quantity> quantities;
		quantities = this.repository.findManyQuantitiesByToolkitId(entity.getId());
		
		request.unbind(entity, model,"code","title","description","assemblyNotes","moreInfo","retailPrice", "draftMode");
		
		model.setAttribute("numQuantities", quantities.size());
	}
	
	//Auxiliary methods-----------------------------------

	private void parse(final List<String> list, final List<Money> prices, final List<Integer> quantities) {

		for (final String s : list) {

			final String[] split = s.split(",");
			Money retailPrice;
			int amount;

			retailPrice = new Money();
			retailPrice.setCurrency(split[0].trim());
			retailPrice.setAmount(Double.valueOf(split[1]));
			amount = Integer.valueOf(split[2]);

			prices.add(retailPrice);
			quantities.add(amount);
		}
	}

}
