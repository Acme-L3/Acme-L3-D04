
package acme.entitites.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotNull
	protected Money				retailPrice;

	@NotBlank
	@Length(max = 100)
	protected String			abstractText;

	@NotNull
	protected CourseType		courseType;

	@URL
	protected String			link;

	protected boolean			isPublished;

	//Relations -----------------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne()
	protected Lecturer			lecturer;

}
