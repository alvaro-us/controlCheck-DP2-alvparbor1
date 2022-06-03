package acme.features.inventor.patronageReport;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.SpamDetector;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.entities.patronage.report.PatronageReport;
import acme.entities.patronages.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportCreateService implements AbstractCreateService<Inventor, PatronageReport>{
	
	@Autowired
	protected InventorPatronageReportRepository repository;

	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		assert request != null;

		boolean result;
		int patronageId;
		Patronage patronage;

		patronageId = request.getModel().getInteger("patronageId");
		patronage = this.repository.findOnePatronageById(patronageId);
		result = request.getPrincipal().getActiveRoleId() == patronage.getInventor().getId();

		return result;
	}

	@Override
	public void bind(final Request<PatronageReport> request, final PatronageReport entity, final Errors errors) {
		
		entity.setPatronage(this.repository.findOnePatronageById(request.getModel().getInteger("patronageId")));
		request.bind(entity, errors, "sequenceNumber", "creationMoment", "memorandum", "moreInfo");
		
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		
		
		request.unbind(entity, model, "sequenceNumber", "creationMoment", "memorandum", "moreInfo");
		model.setAttribute("confirm", "false");	
		
		final int patronageId;
		patronageId = request.getModel().getInteger("patronageId");
		model.setAttribute("patronageId", patronageId);	
		
	}

	@Override
	public PatronageReport instantiate(final Request<PatronageReport> request) {
		assert request != null;
		PatronageReport result;
		
		String sequenceNumber;
		Date moment;
		Collection<PatronageReport> reports;
		int patronageId;
		Patronage patronage;
		
		moment = new Date(System.currentTimeMillis() - 1);
		patronageId = request.getModel().getInteger("patronageId");
		patronage = this.repository.findOnePatronageById(patronageId);
		
		reports = this.repository.findPatronageReportByPatronageId(patronageId);
		sequenceNumber = this.getsequenceNumber(reports,patronage.getCode());
		result = new PatronageReport();
		result.setSequenceNumber(sequenceNumber);
		result.setPatronage(this.repository.findOnePatronageById(request.getModel().getInteger("patronageId")));
		result.setCreationMoment(moment);	
		return result;
	}

	@Override
	public void validate(final Request<PatronageReport> request, final PatronageReport entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		
		

        final InitialConfiguration initialConfig = this.repository.getSystemCofig();
        final String Strong = initialConfig.getStrongSpamTerms();
        final String Weak = initialConfig.getWeakSpamTerms();

        final double StrongT = initialConfig.getStrongSpamTreshold();
        final double WeakT = initialConfig.getWeakSpamTreshold();
		
        if(!errors.hasErrors("memorandum")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getMemorandum(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "memorandum", "any.chirp.form.error.spam");

        }
        
        if(!errors.hasErrors("moreInfo")) {
            final boolean res;

            res = SpamDetector.spamDetector(entity.getMoreInfo(),Strong,Weak,StrongT,WeakT);

            errors.state(request, res, "moreInfo", "any.chirp.form.error.spam");

        }
		
		if(!errors.hasErrors("sequenceNumber")) {
		
			final PatronageReport patronageTest;
		
			patronageTest = this.repository.findPatronageReportBySequenceNumber(entity.getSequenceNumber());
		
			if(patronageTest!=null) {
				errors.state(request, patronageTest.getId()==entity.getId(), "sequenceNumber", "inventor.patronageReport.form.error.duplicated");
			}
		
		}	
		
		
		final Boolean isConfirmed = request.getModel().getBoolean("confirm");
		errors.state(request, isConfirmed, "confirm", "inventor.patronageReport.form.error.must-confirm");
		
	}

	@Override
	public void create(final Request<PatronageReport> request, final PatronageReport entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);	
		
	}
	
	public String getsequenceNumber(final Collection<PatronageReport> reports, final String patronageCode) {
		
		Integer numReports;	
		numReports = reports.size();
		
		String result = "";
		if(numReports.toString().length()==1) {
			
			result = patronageCode + ":"+ "000" + numReports;
			
			
		}
		else if(numReports.toString().length()==2) {
			
			result = patronageCode  + ":"+ "00" + numReports;
			
			
		}
		else if(numReports.toString().length()==3) {
			
			result = patronageCode + ":"+ "0" + numReports;
			
			
		}
		else if(numReports.toString().length()==4) {
			
			result = patronageCode + ":"+ numReports;
			
			
		}
		
		return result;
		
	}

}
