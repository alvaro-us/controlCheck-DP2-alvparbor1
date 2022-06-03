package acme.features.inventor.toolkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.SpamDetector;
import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorToolkitPublishService implements AbstractUpdateService<Inventor, Toolkit> {

	@Autowired
	protected InventorToolkitRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;
 
	
	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;
		
		boolean result;
		Integer toolkitId;
		Toolkit toolkit;
		Inventor inventor;
		
		toolkitId = request.getModel().getInteger("id");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		inventor = toolkit.getInventor();
		
		result = toolkit.isDraftMode() && request.isPrincipal(inventor);
		
		return result;
	}
	

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		assert request != null;
		
		Toolkit result;
		int id; ;
		
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
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "title", "description", "assemblyNotes", "moreInfo");
	}
	
	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		

        final InitialConfiguration initialConfig = this.repository.getSystemCofig();
        final String Strong = initialConfig.getStrongSpamTerms();
        final String Weak = initialConfig.getWeakSpamTerms();

        final double StrongT = initialConfig.getStrongSpamTreshold();
        final double WeakT = initialConfig.getWeakSpamTreshold();
		
        if(!errors.hasErrors("title")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getTitle(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "title", "any.chirp.form.error.spam");

        }
        
        if(!errors.hasErrors("description")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getDescription(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "description", "any.chirp.form.error.spam");

        }
        
        if(!errors.hasErrors("assemblyNotes")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getAssemblyNotes(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "assemblyNotes", "any.chirp.form.error.spam");

        }
        if(!errors.hasErrors("moreInfo")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getMoreInfo(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "moreInfo", "any.chirp.form.error.spam");

        }
		
		if(!errors.hasErrors("code")) {
			Toolkit existing;
			
			existing = this.repository.findOneToolkitByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId() == entity.getId(), "code", "inventor.toolkit.form.error.duplicated");
		}		
		
		if(!errors.hasErrors("items")) {
			final Collection<Quantity> quantities = this.repository.findManyQuantitiesByToolkitId(entity.getId());
			//Esta restricción no está vinculada a ningún atributo pero debemos vincularla a alguno para mostrarla en la interfaz
			errors.state(request, !quantities.isEmpty(), "assemblyNotes", "inventor.toolkit.form.error.no-items"); 
		}
	}
	

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title", "description", "assemblyNotes", "moreInfo", "retailPrice", "draftMode");
	}
	
	
	@Override
	public void update(final Request<Toolkit> request, final Toolkit entity) {
		assert request != null;
		assert entity != null; 
		
		entity.setDraftMode(false);
		this.repository.save(entity);
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
