package acme.forms;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.util.Pair;

import acme.entities.patronages.PatronageStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatronDashboard implements Serializable {

    protected static final long    serialVersionUID    = 1L;

    Integer proposedPatronages;
    
    Integer acceptedPatronages;
    
    Integer deniedPatronages;

	Map<Pair<PatronageStatus,String>,Double> averageBudget;
	
	Map<Pair<PatronageStatus,String>,Double> deviationBudget;
	
	Map<Pair<PatronageStatus,String>,Double> minimunBudget;
	
	Map<Pair<PatronageStatus,String>,Double> maximunBudget;
	

}


