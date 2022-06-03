package acme.features.inventor.quantity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.entities.items.ItemType;
import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorQuantityCreateService implements AbstractCreateService<Inventor, Quantity> {
	
	@Autowired
	protected InventorQuantityRepository repository;

	@Override
	public boolean authorise(final Request<Quantity> request) {
		assert request != null;
		
		boolean result;
		int toolkitId;
		Toolkit toolkit;
		
		toolkitId = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		result = (toolkit != null && toolkit.isDraftMode() && request.isPrincipal(toolkit.getInventor()));
		
		return result;
	}

	@Override
	public void bind(final Request<Quantity> request, final Quantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Item item;
		Integer itemId;
		
		itemId = request.getModel().getInteger("item");
		item = this.repository.findOneItemById(itemId);
		entity.setItem(item);
		
		request.bind(entity, errors, "amount");
		
	}

	@Override
	public void unbind(final Request<Quantity> request, final Quantity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final Collection<Item> items;
		
		items = this.repository.findManyAvailableItems(entity.getToolkit().getId());

		request.unbind(entity, model, "amount");
		model.setAttribute("masterId", request.getModel().getAttribute("masterId"));
		model.setAttribute("items", items);
		model.setAttribute("itemSelected", entity.getItem());
		model.setAttribute("numItems", items.size());
		
	}

	@Override
	public Quantity instantiate(final Request<Quantity> request) {
		assert request != null;
		
		Quantity result;
		int toolkitId;
		Toolkit toolkit;
		
		toolkitId = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		
		result = new Quantity();
		result.setToolkit(toolkit);
		
		return result;
	}

	@Override
	public void validate(final Request<Quantity> request, final Quantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("amount") && entity.getItem().getItemType().equals(ItemType.TOOL) ) {
			errors.state(request, entity.getAmount() == 1 , "amount", "inventor.quantity.form.error.amount-of-tool");
		}
	}

	@Override
	public void create(final Request<Quantity> request, final Quantity entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
