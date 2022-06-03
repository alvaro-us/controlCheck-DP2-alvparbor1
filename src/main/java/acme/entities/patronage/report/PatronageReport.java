package acme.entities.patronage.report;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.patronages.Patronage;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PatronageReport extends AbstractEntity{

	// Serialisation identifier ----------------------------------------------------------
	
	protected static final long serialVersionUID = 1L;

	// Attributes ----------------------------------------------------------
	
	@NotBlank
	@Column(unique=true)
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?:[0-9]{4}$")
	protected String sequenceNumber;
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date creationMoment;
	
	@NotBlank
	@Length(min=1, max=256)
	protected String memorandum;
	
	@URL
	protected String moreInfo;
	
	// RelationShips ----------------------------------------------------------
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	protected Patronage patronage;
}
