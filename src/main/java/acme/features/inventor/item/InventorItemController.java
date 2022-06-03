package acme.features.inventor.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.items.Item;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorItemController extends AbstractController<Inventor, Item>{
	
	@Autowired
	protected InventorItemPublishService publishService;
	
	@Autowired
	protected InventorItemCreateService createService;
	
	@Autowired
	protected InventorItemDeleteService deleteService;
	
	@Autowired
	protected InventorItemUpdateService updateService;
	
	@Autowired
	protected InventorItemListService listAllInventorItemService;
	

	
	@Autowired
	protected InventorItemShowService showService;
	
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("publish", "update", this.publishService);
		super.addCommand("create", this.createService);
		super.addCommand("delete", this.deleteService);
		super.addCommand("update", this.updateService);
		
		super.addCommand("show", this.showService);
		super.addCommand("list-inventor-items","list", this.listAllInventorItemService); 
	}

}
