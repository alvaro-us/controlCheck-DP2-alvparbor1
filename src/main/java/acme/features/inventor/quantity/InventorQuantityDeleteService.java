package acme.features.inventor.quantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkits.Quantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor; 

@Service
public class InventorQuantityDeleteService implements AbstractDeleteService<Inventor, Quantity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorQuantityRepository repository;


	@Override
	public boolean authorise(final Request<Quantity> request) {
		assert request != null;

		//el principal debe ser dueño de la toolkit y esta debe estar en modo borrador
		//el principal también ha de ser el inventor del item pero esto se gestiona como regla de negocio en create/update
		boolean result;
		int masterId;
		Quantity quantity;
		Inventor inventor;

		masterId = request.getModel().getInteger("id");
		quantity = this.repository.findOneQuantityById(masterId);
		inventor = quantity.getToolkit().getInventor();
		result = quantity.getToolkit().isDraftMode() && request.isPrincipal(inventor);

		return result;
	}

	@Override
	public void bind(final Request<Quantity> request, final Quantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "amount");
	}

	@Override
	public void unbind(final Request<Quantity> request, final Quantity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "amount");
		//model.setAttribute("masterId", request.getModel().getAttribute("masterId"));
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
	public void validate(final Request<Quantity> request, final Quantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Quantity> request, final Quantity entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}

}
