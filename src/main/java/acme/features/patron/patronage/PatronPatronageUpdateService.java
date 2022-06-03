package acme.features.patron.patronage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.SpamDetector;
import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.patronages.Patronage;
import acme.entities.patronages.PatronageStatus;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Patron;

@Service
public class PatronPatronageUpdateService implements AbstractUpdateService<Patron,Patronage>{
	
	@Autowired
	protected PatronPatronageRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeService;

	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;
		
		boolean result;
		int patronageId;
		Patronage patronage;

		patronageId = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(patronageId);
		result = patronage.getPatron().getId() == request.getPrincipal().getActiveRoleId() && !patronage.isPublished();

		return result;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "legalStuff", "budget", "creationDate", "startDate", "endDate", "moreInfo");

		
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "legalStuff", "budget", "creationDate", "startDate", "endDate", "moreInfo");

		model.setAttribute("inventorId", entity.getInventor().getId());
		model.setAttribute("patronageId", entity.getId());
		model.setAttribute("statusList", PatronageStatus.values());
		
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		assert request != null;
		Patronage result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOnePatronageById(id);

		
		return result;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		

        final InitialConfiguration initialConfig = this.repository.getSystemCofig();
        final String Strong = initialConfig.getStrongSpamTerms();
        final String Weak = initialConfig.getWeakSpamTerms();

        final double StrongT = initialConfig.getStrongSpamTreshold();
        final double WeakT = initialConfig.getWeakSpamTreshold();
		
        if(!errors.hasErrors("legalStuff")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getLegalStuff(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "legalStuff", "any.chirp.form.error.spam");

        }
        
        if(!errors.hasErrors("moreInfo")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getMoreInfo(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "moreInfo", "any.chirp.form.error.spam");

        }
		
		
		if(!errors.hasErrors("code")) {
			
			final Patronage patronageTest;
			
			patronageTest = this.repository.findOnePatronageByCode(entity.getCode());
			
			if(patronageTest!=null) {
				errors.state(request, patronageTest.getId()==entity.getId(), "code", "patron.patronage.form.error.duplicated");
			}
			
		}
		if(entity.getStartDate()!=null) {
		if(!errors.hasErrors("startDate")) {
			
			Date startDate;
			
			startDate = entity.getStartDate();
			
			final long diff = startDate.getTime() - entity.getCreationDate().getTime();

	        final TimeUnit time = TimeUnit.DAYS; 
	        final long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
	        
	        errors.state(request, diffrence>=30, "startDate","patron.patronage.form.error.startDate");
			
			
		}
		
		if(!errors.hasErrors("endDate")) {
			
			Date endDate;
			
			endDate = entity.getEndDate();
			
			final long diff = endDate.getTime() - entity.getStartDate().getTime();

	        final TimeUnit time = TimeUnit.DAYS; 
	        final long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
	        
	        errors.state(request, diffrence>=30 , "endDate","patron.patronage.form.error.endDate");
			
			
		}
		}
		if(!errors.hasErrors("budget")) {
			
			final List<String> currencies = new ArrayList<>();
			
			String currency;
			
			for(final String c: this.repository.acceptedCurrencies().split(",")) {
				currencies.add(c.trim());
			}
			
			currency = entity.getBudget().getCurrency();
			
			errors.state(request, currencies.contains(currency) , "budget","patron.patronage.form.error.currency");
			
		}
		if(!errors.hasErrors("budget")) {
			
			Money budget;
			
			budget = entity.getBudget();
			
			
			errors.state(request, budget.getAmount()>0 , "budget","patron.patronage.form.error.amount");
			
		}
	}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
