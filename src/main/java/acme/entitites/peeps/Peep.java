
package acme.entitites.peeps;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Peep {

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	protected Date		moment;

	@NotBlank
	@Length(max = 75)
	protected String	title;

	@NotBlank
	@Length(max = 75)
	protected String	nick;

	@NotBlank
	@Length(max = 100)
	protected String	message;

	@Email
	protected String	email;

	@URL
	protected String	link;

}
