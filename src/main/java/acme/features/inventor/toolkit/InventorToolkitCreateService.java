package acme.features.inventor.toolkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.SpamDetector;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.toolkits.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorToolkitCreateService implements AbstractCreateService<Inventor, Toolkit> {

	@Autowired
	protected InventorToolkitRepository repository;
 
	
	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;
			
		return true;
	}
	
	@Override
	public Toolkit instantiate(final Request<Toolkit> request) {
		assert request != null;

		Toolkit result;
		Inventor inventor;

		inventor = this.repository.findOneInventorById(request.getPrincipal().getActiveRoleId());
		result = new Toolkit();
		result.setDraftMode(true);
		result.setInventor(inventor);

		return result;
	}
 
	
	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "title", "description", "assemblyNotes", "moreInfo");
	}
	
	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
        final InitialConfiguration initialConfig = this.repository.getSystemCofig();
        final String Strong = initialConfig.getStrongSpamTerms();
        final String Weak = initialConfig.getWeakSpamTerms();

        final double StrongT = initialConfig.getStrongSpamTreshold();
        final double WeakT = initialConfig.getWeakSpamTreshold();
		
        if(!errors.hasErrors("title")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getTitle(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "title", "any.chirp.form.error.spam");

        }
        
        if(!errors.hasErrors("description")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getDescription(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "description", "any.chirp.form.error.spam");

        }
        
        if(!errors.hasErrors("assemblyNotes")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getAssemblyNotes(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "assemblyNotes", "any.chirp.form.error.spam");

        }
        
        if(!errors.hasErrors("moreInfo")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getMoreInfo(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "moreInfo", "any.chirp.form.error.spam");

        }
		
		if(!errors.hasErrors("code")) {
			Toolkit existing;
			
			existing = this.repository.findOneToolkitByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId() == entity.getId(), "code", "inventor.toolkit.form.error.duplicated");
		}		
	}
	

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title", "description", "assemblyNotes", "moreInfo", "retailPrice", "draftMode");
	}
	
	
	@Override
	public void create(final Request<Toolkit> request, final Toolkit entity) {
		assert request != null;
		assert entity != null; 
		 
		this.repository.save(entity);
	}


}
