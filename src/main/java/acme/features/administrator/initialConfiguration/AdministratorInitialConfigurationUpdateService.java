package acme.features.administrator.initialConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorInitialConfigurationUpdateService implements AbstractUpdateService<Administrator, InitialConfiguration>{

	@Autowired
	protected AdministratorInitialConfigurationRepository repository;
	
	@Override
	public boolean authorise(final Request<InitialConfiguration> request) {
		assert request != null;
		return request.getPrincipal().hasRole(Administrator.class);
	}

	@Override
	public void bind(final Request<InitialConfiguration> request, final InitialConfiguration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "defaultCurrency", "acceptedCurrencies", "strongSpamTerms", "weakSpamTerms",
			"strongSpamTreshold", "weakSpamTreshold");
	}

	@Override
	public void unbind(final Request<InitialConfiguration> request, final InitialConfiguration entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model,"defaultCurrency", "acceptedCurrencies", "strongSpamTerms", "weakSpamTerms",
			"strongSpamTreshold", "weakSpamTreshold");
	}

	@Override
	public InitialConfiguration findOne(final Request<InitialConfiguration> request) {
		return this.repository.findConfiguration();
	}

	@Override
	public void validate(final Request<InitialConfiguration> request, final InitialConfiguration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;		
		
		if(!errors.hasErrors("defaultCurrency")) {
			final String acceptedCurrencies = entity.getAcceptedCurrencies();
			final String defaultCurrency = "(.*)" + entity.getDefaultCurrency() + "(.*)";
			final boolean ac = acceptedCurrencies.matches(defaultCurrency);
			errors.state(request, ac, "defaultCurrency", "administrator.initial-configuration.form.error.defaultCurrency");
		}
	}

	@Override
	public void update(final Request<InitialConfiguration> request, final InitialConfiguration entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}

}
