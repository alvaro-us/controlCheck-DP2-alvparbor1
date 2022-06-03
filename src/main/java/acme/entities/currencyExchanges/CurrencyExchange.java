
package acme.entities.currencyExchanges;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CurrencyExchange extends AbstractEntity {
	
	//Serialisation identifier

	protected static final long serialVersionUID = 1L;
	
	//Atributes
	@NotBlank
	protected String source;
	
	@NotBlank
	protected String target;
	
	protected double rate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date transactionMoment;
	
	

}	
