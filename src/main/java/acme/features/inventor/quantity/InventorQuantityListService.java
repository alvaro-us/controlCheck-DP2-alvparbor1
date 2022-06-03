package acme.features.inventor.quantity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkits.Quantity;
import acme.entities.toolkits.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorQuantityListService implements AbstractListService<Inventor, Quantity> {
	
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
		
		if(!request.isPrincipal(toolkit.getInventor())) {
			result = !toolkit.isDraftMode(); //si el usuario no es el inventor de la toolkit solo podrá verla si no es un borrador
		} else {
			result = true; //si el usuario es inventor de la toolkit entonces va a poder verla de cualquier forma
		}
		
		
		return result;
	}

	@Override
	public Collection<Quantity> findMany(final Request<Quantity> request) {
		assert request != null;
		
		final Collection<Quantity> result;
		final int toolkitId;
		
		toolkitId = request.getModel().getInteger("masterId");
		
		result = this.repository.findManyQuantitiesByToolkitId(toolkitId);

		return result;
	}

	@Override
	public void unbind(final Request<Quantity> request, final Quantity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "item.name","item.code", "item.technology", "amount");
 		model.setAttribute("masterId", entity.getToolkit().getId());
		
	}

}
