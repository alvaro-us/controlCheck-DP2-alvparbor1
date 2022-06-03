package acme.features.any.toolkit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.entities.toolkits.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyToolkitListService implements AbstractListService<Any, Toolkit> {
	
	@Autowired
	protected AnyToolkitRepository repository;


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;
		return true;
	}

	@Override
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		assert request != null;
		
		final Collection<Toolkit> result;
		
		result = this.repository.findAllToolkits();

		return result;
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final int toolkitId;
		final Collection<Item> items;
		String payload;
		StringBuilder bld;
		
		bld = new StringBuilder();

		toolkitId = entity.getId();
		items = this.repository.findManyItemsByToolkitId(toolkitId);
		
		for(final Item i: items) {
			bld.append(String.format("%s; %s; %s; %s; ", i.getCode(), i.getName(), i.getTechnology(), i.getDescription()));
		}
		
		payload = bld.toString();
		request.unbind(entity, model, "title", "code", "description");
		model.setAttribute("payload", payload);

	}

}
