package acme.features.inventor.quantity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.toolkits.Quantity;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorQuantityController extends AbstractController<Inventor,Quantity> {
	
	@Autowired
	protected InventorQuantityCreateService createService;
	
	@Autowired
	protected InventorQuantityUpdateService updateService;
	
	@Autowired
	protected InventorQuantityDeleteService deleteService;
	
	@Autowired
	protected InventorQuantityListService listService;
	 
	@Autowired
	protected InventorQuantityShowService showService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
		
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		  
	}

}
