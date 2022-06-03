package acme.features.inventor.chimpum;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpums.Chimpum;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.items.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumCreateService implements AbstractCreateService<Inventor, Chimpum>{
	
	@Autowired
	protected InventorChimpumRepository repository;

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		final Item item = this.repository.findOneItemById(request.getModel().getInteger("itemId"));
		final boolean res = item.getInventor().getId() == request.getPrincipal().getActiveRoleId() && !item.isDraftMode();
		return res;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		request.bind(entity, errors, "title", "description", "budget", "creationMoment", "startDate", "endDate", "moreInfo");
		final String pattern = request.getModel().getString("pattern");
		final LocalDate date = entity.getCreationMoment().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		entity.setCode(pattern + "-" + this.generateCode(date));
	}
	
	private String generateCode(final LocalDate date) {
		final Integer day = date.getDayOfMonth();
		final Integer month = date.getMonthValue();
		final Integer year = date.getYear();
		String stringDay = "";
		String stringMonth = "";
		final String stringYear = year.toString().substring(2, 4);
		if(day.toString().length() == 1) {
			stringDay = "0" + day.toString();
		} else {
			stringDay = day.toString();
		}
		if(month.toString().length() == 1) {
			stringMonth = "0" + month.toString();
		} else {
			stringMonth = month.toString();
		}
		
		return stringYear + "-" + stringMonth + "-" + stringDay;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		request.unbind(entity, model, "title", "description", "budget", "creationMoment", "startDate", "endDate", "moreInfo");
		model.setAttribute("itemId", request.getModel().getInteger("itemId"));
	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		assert request != null;
		final Chimpum chimpum = new Chimpum();
		chimpum.setItem(this.repository.findOneItemById(request.getModel().getInteger("itemId")));
		chimpum.setCreationMoment(new Date(System.currentTimeMillis() -1));
		return null;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final InitialConfiguration ic = this.repository.findInitialCofiguration();
		final String strong = ic.getStrongSpamTerms();
		final String weak = ic.getWeakSpamTerms();
		final double strongt = ic.getStrongSpamTreshold();
		final double weakt = ic.getWeakSpamTreshold();
		final String pattern = request.getModel().getString("pattern");
		errors.state(request, pattern.matches("[A-Z]{3}"), "pattern", "inventor.chimpum.error.pattern");
		
	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
}
