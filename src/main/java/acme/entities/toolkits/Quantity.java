
package acme.entities.toolkits;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.entities.items.Item;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

// Esta entidad es la clase intermedia que nos sirve para crear la relación entre Item y Toolkit

@Entity
@Getter
@Setter
public class Quantity extends AbstractEntity {

	//Serialisation identifier

	protected static final long serialVersionUID = 1L;

	
	//Atributes
	
	@Min(1) //La cantidad mínima debe ser 1, pues que haya menos de la unidad no tiene sentido
	protected int amount;
	
	// Relationships ----------------------------------------------------------
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Toolkit toolkit;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Item item;

}
