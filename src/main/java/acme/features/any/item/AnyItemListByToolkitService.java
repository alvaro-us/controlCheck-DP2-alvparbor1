package acme.features.any.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyItemListByToolkitService implements AbstractListService<Any, Item> {
	
	@Autowired
	protected AnyItemRepository repository;

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		
		boolean result;
		int toolkitId;
		Toolkit toolkit;
		
		toolkitId = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(toolkitId);
		result = !toolkit.isDraftMode();
		
		return result;
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		assert request != null;
		
		final Collection<Item> result;
		final int toolkitId;
		
		toolkitId = request.getModel().getInteger("masterId");
		
		result = this.repository.findManyItemsByToolkitId(toolkitId);
		

		return result;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		int itemId;
		Integer toolkitId;
		Quantity quantity;
		
		itemId = entity.getId();
		toolkitId = request.getModel().getInteger("masterId");
		quantity = this.repository.findOneQuantityByToolkitIdAndItemId(toolkitId, itemId);

		request.unbind(entity, model, "name", "code", "technology");
		model.setAttribute("amounts", quantity.getAmount());
		model.setAttribute("navigability", true);

	}

}
