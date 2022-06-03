package acme.features.administrator.initialConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorInitialConfigurationShowService implements AbstractShowService<Administrator, InitialConfiguration>{

	// Internal state ------------------------------------------------------------
	@Autowired 
	protected AdministratorInitialConfigurationRepository repository;
	
	// AbstractShowService<Administrador, InitialConfiguration> interface --------
	@Override
	public boolean authorise(final Request<InitialConfiguration> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public InitialConfiguration findOne(final Request<InitialConfiguration> request) {
		assert request != null;
		InitialConfiguration ic;
		ic = this.repository.findConfiguration();
		return ic;
	}

	@Override
	public void unbind(final Request<InitialConfiguration> request, final InitialConfiguration entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "defaultCurrency", "acceptedCurrencies", "strongSpamTerms", "strongSpamTreshold", "weakSpamTerms", "weakSpamTreshold");
		
	}

}
