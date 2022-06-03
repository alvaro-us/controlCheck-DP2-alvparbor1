package acme.entities.initialConfiguration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InitialConfiguration extends AbstractEntity{

    protected static final long    serialVersionUID    = 1L;

    @NotBlank
    public String defaultCurrency;
    
    @NotBlank
    public String acceptedCurrencies;
    
    @NotBlank
    public String strongSpamTerms;

    @Range(min = 0, max = 100)
    public int strongSpamTreshold;
    
    @NotBlank
    public String weakSpamTerms;
    
    @Range(min = 0, max = 100)
    public int weakSpamTreshold;

}


