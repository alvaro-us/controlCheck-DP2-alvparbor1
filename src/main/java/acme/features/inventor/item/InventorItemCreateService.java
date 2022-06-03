package acme.features.inventor.item;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.SpamDetector;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.items.Item;
import acme.entities.items.ItemType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor; 

@Service
public class InventorItemCreateService implements AbstractCreateService<Inventor, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorItemRepository repository;

	// AbstractCreateService<Employer, Job> interface -------------------------


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		return true;
	}

	@Override
	public Item instantiate(final Request<Item> request) {
		assert request != null;

		Item result;
		Inventor inventor;

		inventor = this.repository.findOneInventorById(request.getPrincipal().getActiveRoleId());
		result = new Item();
		result.setDraftMode(true);
		result.setInventor(inventor);

		return result;
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "name", "code", "technology", "description", "retailPrice", "moreInfo", "itemType");
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
        final InitialConfiguration initialConfig = this.repository.getSystemCofig();
        final String Strong = initialConfig.getStrongSpamTerms();
        final String Weak = initialConfig.getWeakSpamTerms();

        final double StrongT = initialConfig.getStrongSpamTreshold();
        final double WeakT = initialConfig.getWeakSpamTreshold();
		
        if(!errors.hasErrors("name")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getName(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "name", "any.chirp.form.error.spam");

        }
        if(!errors.hasErrors("technology")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getTechnology(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "technology", "any.chirp.form.error.spam");

        }
        if(!errors.hasErrors("description")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getDescription(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "description", "any.chirp.form.error.spam");

        }
        if(!errors.hasErrors("moreInfo")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getMoreInfo(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "moreInfo", "any.chirp.form.error.spam");

        }

		if (!errors.hasErrors("code")) {
			Item existing;

			existing = this.repository.findOneItemByCode(entity.getCode());
			errors.state(request, existing == null, "code", "inventor.item.form.error.duplicated");
		}

		if (!errors.hasErrors("retailPrice")) {
			String currencies;
			String[] currenciesSplit;
			List<String> listCurrencies;
			
			currencies = this.repository.acceptedCurrencies();
			currenciesSplit = currencies.split(",");
			listCurrencies = new ArrayList<String>();
			
			for(int i=0; i<currenciesSplit.length; i++) {
				listCurrencies.add(currenciesSplit[i].trim());
			}
			
			errors.state(request, listCurrencies.contains(entity.getRetailPrice().getCurrency()), "retailPrice", "inventor.item.form.error.invalid-retailPrice");
			
			if (entity.getItemType().equals(ItemType.TOOL)) {
				errors.state(request, entity.getRetailPrice().getAmount() >= 0, "retailPrice", "inventor.item.form.error.tool-retailPrice");
			} else {
				errors.state(request, entity.getRetailPrice().getAmount() > 0, "retailPrice", "inventor.item.form.error.component-retailPrice");
			}		
		}
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model,  "name", "code", "technology", "description", "retailPrice", "moreInfo", "itemType", "draftMode");
	}

	@Override
	public void create(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
}