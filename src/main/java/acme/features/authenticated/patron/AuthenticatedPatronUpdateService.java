package acme.features.authenticated.patron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.SpamDetector;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.HttpMethod;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.entities.Principal;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Patron;

@Service
public class AuthenticatedPatronUpdateService implements AbstractUpdateService<Authenticated, Patron>{
	// Internal state ---------------------------------------------------------

			@Autowired
			protected AuthenticatedPatronRepository repository;

			// AbstractUpdateService<Authenticated, Patron> interface -----------------


			@Override
			public boolean authorise(final Request<Patron> request) {
				assert request != null;

				return true;
			}

			@Override
			public void validate(final Request<Patron> request, final Patron entity, final Errors errors) {
				assert request != null;
				assert entity != null;
				assert errors != null;
				
		        final InitialConfiguration initialConfig = this.repository.getSystemCofig();
		        final String Strong = initialConfig.getStrongSpamTerms();
		        final String Weak = initialConfig.getWeakSpamTerms();

		        final double StrongT = initialConfig.getStrongSpamTreshold();
		        final double WeakT = initialConfig.getWeakSpamTreshold();
				
		        if(!errors.hasErrors("company")) {
		            final boolean res;

		            res = SpamDetector.spamDetector(entity.getCompany(),Strong,Weak,StrongT,WeakT);

		            errors.state(request, res, "company", "any.chirp.form.error.spam");

		        }
		        
		        if(!errors.hasErrors("statement")) {
		            final boolean res;

		            res = SpamDetector.spamDetector(entity.getStatement(),Strong,Weak,StrongT,WeakT);

		            errors.state(request, res, "statement", "any.chirp.form.error.spam");

		        }
		        
		        if(!errors.hasErrors("moreInfo")) {
		            final boolean res;

		            res = SpamDetector.spamDetector(entity.getMoreInfo(),Strong,Weak,StrongT,WeakT);

		            errors.state(request, res, "moreInfo", "any.chirp.form.error.spam");

		        }
			}

			@Override
			public void bind(final Request<Patron> request, final Patron entity, final Errors errors) {
				assert request != null;
				assert entity != null;
				assert errors != null;

				request.bind(entity, errors, "company", "statement","moreInfo");
			}

			@Override
			public void unbind(final Request<Patron> request, final Patron entity, final Model model) {
				assert request != null;
				assert entity != null;
				assert model != null;

				request.unbind(entity, model, "company", "statement","moreInfo");
			}

			@Override
			public Patron findOne(final Request<Patron> request) {
				assert request != null;

				Patron result;
				Principal principal;
				int userAccountId;

				principal = request.getPrincipal();
				userAccountId = principal.getAccountId();

				result = this.repository.findOnePatronByUserAccountId(userAccountId);

				return result;
			}

			@Override
			public void update(final Request<Patron> request, final Patron entity) {
				assert request != null;
				assert entity != null;

				this.repository.save(entity);
			}

			@Override
			public void onSuccess(final Request<Patron> request, final Response<Patron> response) {
				assert request != null;
				assert response != null;

				if (request.isMethod(HttpMethod.POST)) {
					PrincipalHelper.handleUpdate();
				}
			}

}
