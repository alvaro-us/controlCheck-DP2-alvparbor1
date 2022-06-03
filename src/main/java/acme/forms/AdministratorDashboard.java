
package acme.forms;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.Min;

import org.springframework.data.util.Pair;

import acme.entities.patronages.PatronageStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard implements Serializable {

	// Serialisation identifier --------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//Components
	@Min(0)
	Integer	totalNumberOfComponents;
	Map<Pair<String,String>,Double>  averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency;
	Map<Pair<String,String>,Double> deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency;
	Map<Pair<String,String>,Double> minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency;
	Map<Pair<String,String>,Double> maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency;

	//Tools
	@Min(0)
	
	Integer totalNumberOfTools;
	Map<String,Double> averageBudgetOfToolGroupedByCurrency;
	Map<String,Double> deviationBudgetOfToolGroupedByCurrency;
	Map<String,Double> minimunBudgetOfToolGroupedByCurrency;
	Map<String,Double> maximunBudgetOfToolGroupedByCurrency;

	
	//Patronages
	int	totalNumberOfProposedPatronages;
	int	totalNumberOfAcceptedPatronages;
	int	totalNumberOfDeniedPatronages;
	
	Map<Pair<PatronageStatus,String>,Double> averageBudgetOfPatronagesGroupedByStatusAndCurrency;
	Map<Pair<PatronageStatus,String>,Double> deviationBudgetOfPatronagesGroupedByStatusAndCurrency;
	Map<Pair<PatronageStatus,String>,Double> minimunBudgetOfPatronagesGroupedByStatusAndCurrency;
	Map<Pair<PatronageStatus,String>,Double> maximunBudgetOfPatronagesGroupedByStatusAndCurrency;
	
	@Min(0)
	Double ratioOfItemsWithChimpum;
	Map<String,Double> averageBudgetOfChimpumGroupedByCurrency;
	Map<String,Double> deviationBudgetOfChimpumGroupedByCurrency;
	Map<String,Double> minimunBudgetOfChimpumGroupedByCurrency;
	Map<String,Double> maximunBudgetOfChimpumGroupedByCurrency;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
