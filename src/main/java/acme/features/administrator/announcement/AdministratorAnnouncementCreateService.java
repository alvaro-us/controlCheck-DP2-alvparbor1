package acme.features.administrator.announcement;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.SpamDetector;
import acme.entities.announcements.Announcement;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorAnnouncementCreateService implements AbstractCreateService<Administrator, Announcement>{

	// Internal state ------------------------------------------------------
	@Autowired
	protected AdministratorAnnouncementRepository repository;
	
	// AbstractUpdateService<Administrator, Announcement> interface --------
	@Override
	public boolean authorise(final Request<Announcement> request) {
		return request.getPrincipal().hasRole(Administrator.class);
	}

	@Override
	public void bind(final Request<Announcement> request, final Announcement entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Date date = new Date(System.currentTimeMillis()-1);
		entity.setCreationMoment(date);
		
		request.bind(entity, errors, "title", "body", "critical", "moreInfo");
		
	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "body", "critical", "moreInfo");
	}

	@Override
	public void validate(final Request<Announcement> request, final Announcement entity, final Errors errors) {
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
        if(!errors.hasErrors("body")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getBody(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "body", "any.chirp.form.error.spam");

        }
        if(!errors.hasErrors("moreInfo")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getMoreInfo(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "moreInfo", "any.chirp.form.error.spam");

        }
		
		
		final boolean confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "authenticated.announcement.form.error.isConfirmation");
	}

	@Override
	public Announcement instantiate(final Request<Announcement> request) {
		assert request != null;
		final Announcement res;
		
		res = new Announcement();
		res.setCreationMoment(Calendar.getInstance().getTime());
		res.setCritical(false);		
		return res;
	}

	@Override
	public void create(final Request<Announcement> request, final Announcement entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}

}
