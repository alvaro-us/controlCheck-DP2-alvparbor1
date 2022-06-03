package acme.features.inventor.quantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.ItemType;
import acme.entities.toolkits.Quantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor; 

@Service
public class InventorQuantityUpdateService implements AbstractUpdateService<Inventor, Quantity> {

	@Autowired
	protected InventorQuantityRepository repository;

	@Override
	public boolean authorise(final Request<Quantity> request) {
		assert request != null;

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
	public void validate(final Request<Quantity> request, final Quantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("amount") && entity.getItem().getItemType().equals(ItemType.TOOL) ) {
			errors.state(request, entity.getAmount() == 1 , "amount", "inventor.quantity.form.error.amount-of-tool");
		}
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
		model.setAttribute("masterId", entity.getToolkit().getId());
		model.setAttribute("itemSelect", entity.getItem().getName()+" "+entity.getItem().getCode());
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
	public void update(final Request<Quantity> request, final Quantity entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
