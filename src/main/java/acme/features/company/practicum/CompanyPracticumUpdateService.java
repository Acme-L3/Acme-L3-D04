
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.practicums.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum object;
		Principal principal;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumById(practicumId);
		principal = super.getRequest().getPrincipal();

		status = object.getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repo.findCourseById(courseId);

		super.bind(object, "code", "title", "summary", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		Practicum practicumWithCode;
		Practicum deprecatedPracticum;

		if (!super.getBuffer().getErrors().hasErrors()) {
			final int id = super.getRequest().getData("id", int.class);
			practicumWithCode = this.repo.findPracticumByCode(object.getCode());
			deprecatedPracticum = this.repo.findPracticumById(id);
			super.state(practicumWithCode == deprecatedPracticum || practicumWithCode == null, "code", "company.practicum.error.label.code");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repo.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "summary", "goals");
		tuple.put("draftMode", true);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
