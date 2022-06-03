package acme.features.authenticated.announcement;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.announcements.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticationAnnouncementShowService implements AbstractShowService<Authenticated, Announcement>{

	// Internal state ------------------------------------------------
	@Autowired
	protected AuthenticatedAnnouncementRepository repository;
	
	// AbstractShowService<Authenticated, Announcement> interface -----
	@Override
	public boolean authorise(final Request<Announcement> request) {
		assert request != null;
		
		final Boolean result;
		
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		final Date deadline = calendar.getTime();
		final int id = request.getModel().getInteger("id");
		final Announcement announcement = this.repository.findOneAnnouncementById(id);
		result = announcement.getCreationMoment().after(deadline);
		return result;
	}

	@Override
	public Announcement findOne(final Request<Announcement> request) {
		assert request != null;
		final Announcement result;
		
		final int id = request.getModel().getInteger("id");
		result = this.repository.findOneAnnouncementById(id);
		return result;
	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "creationMoment", "body", "critical", "moreInfo");
		model.setAttribute("confirmation", false);
		model.setAttribute("readonly", true);
	}
	

}
