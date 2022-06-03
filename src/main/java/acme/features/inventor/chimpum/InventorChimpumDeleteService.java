package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpums.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorChimpumDeleteService  implements AbstractDeleteService<Inventor,Chimpum>  {

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		request.bind(entity, errors, "code", "legalStuff", "budget", "creationDate", "startDate", "endDate", "moreInfo");
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		request.unbind(entity, model, "status", "code", "legalStuff", "budget", "creationDate", "startDate", "endDate", "moreInfo","published");
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		final int id = request.getModel().getInteger("id");
		final Chimpum res = this.repository.findOneChimpumById(id);
		return res;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
		
	}

}
