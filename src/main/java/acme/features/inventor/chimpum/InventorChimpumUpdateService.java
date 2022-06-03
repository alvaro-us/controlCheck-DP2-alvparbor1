package acme.features.inventor.chimpum;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.chimpums.Chimpum;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumUpdateService implements AbstractUpdateService<Inventor,Chimpum>{
	

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		final int id = request.getModel().getInteger("id");
		final Chimpum chimpum = this.repository.findOneChimpumById(id);
		final boolean res = chimpum.getItem().getInventor().getId() == request.getPrincipal().getActiveRoleId();
		return res;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "title", "description", "creationMoment", "budget", "startDate", "endDate", "moreInfo");
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "code", "title", "description", "creationMoment", "budget", "startDate", "endDate", "moreInfo");
		model.setAttribute("pattern", entity.getCode().substring(0, 3));
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
		
		final InitialConfiguration ic = this.repository.findInitialCofiguration();
		final String strong = ic.getStrongSpamTerms();
		final String weak = ic.getWeakSpamTerms();
		final double strongt = ic.getStrongSpamTreshold();
		final double weakt = ic.getWeakSpamTreshold();
		final String pattern = request.getModel().getString("pattern");
		errors.state(request, pattern.matches("[A-Z]{3}"), "pattern", "inventor.chimpum.error.pattern");
	}

	@Override
	public void update(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		final String pattern = request.getModel().getString("pattern");
		final LocalDate date = entity.getCreationMoment().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		entity.setCode(pattern + "-" + this.generateCode(date));
		this.repository.save(entity);
		
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
}
