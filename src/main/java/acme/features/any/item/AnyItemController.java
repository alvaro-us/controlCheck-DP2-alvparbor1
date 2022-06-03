package acme.features.any.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.items.Item;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;

@Controller
public class AnyItemController extends AbstractController<Any, Item> {
	
	@Autowired
	protected AnyItemListService listAllService;
	
	@Autowired
	protected AnyItemShowService showService;
	
	@Autowired
	protected AnyItemListByToolkitService listItemsByToolkitService;
	
	
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
		super.addCommand("list-all","list", this.listAllService); 
		super.addCommand("list-items-by-toolkit", "list", this.listItemsByToolkitService); 
	}

}
