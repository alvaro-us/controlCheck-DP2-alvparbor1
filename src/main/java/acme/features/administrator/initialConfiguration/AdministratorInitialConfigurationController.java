package acme.features.administrator.initialConfiguration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.initialConfiguration.InitialConfiguration;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Administrator;

@Controller
public class AdministratorInitialConfigurationController extends AbstractController<Administrator, InitialConfiguration>{

	// Internal state ------------------------------------------------------------
	@Autowired
	protected AdministratorInitialConfigurationShowService showService;
	
	@Autowired
	protected AdministratorInitialConfigurationUpdateService updateService;
	
	// Contructors ---------------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
		super.addCommand("update", this.updateService);
	}
}
