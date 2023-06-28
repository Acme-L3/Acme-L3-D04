
package acme.features.authenticated.assistant.tutorial;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialPublishService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final int tutorialId;
		final Tutorial tutorial;
		final Assistant assistant;

		tutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(assistant);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		super.bind(object, "code", "tittle", "summary", "goals", "startDate", "endDate", "draftMode");
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;
			existing = this.repository.findTutorialByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "assistant.tutorial.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			if (object.getStartDate() != null && object.getEndDate() != null)
				super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "assistant.tutorial.form.error.is.before");

		if (!super.getBuffer().getErrors().hasErrors("draftMode")) {
			final Collection<TutorialSession> theory = this.repository.findTutorialSessionsByTutorialId(object.getId());
			super.state(!theory.isEmpty(), "draftMode", "assistant.tutorial.form.error.publish.empty");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			final Collection<Date> tsStartDate = this.repository.findTutorialSessionsStartDateByTutorialId(object.getId());
			Boolean res = null;
			if (!tsStartDate.isEmpty()) {
				for (final Date fecha : tsStartDate)
					if (MomentHelper.isBefore(object.getStartDate(), fecha))
						res = true;
					else
						res = false;
			} else
				res = true;
			super.state(res, "startDate", "assistant.tutorial.form.error.is.before.tuto");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			final Collection<Date> tsEndDate = this.repository.findTutorialSessionsEndDateByTutorialId(object.getId());
			Boolean res = null;
			if (!tsEndDate.isEmpty()) {
				for (final Date fecha : tsEndDate)
					if (MomentHelper.isBefore(fecha, object.getEndDate()))
						res = true;
					else
						res = false;
			} else
				res = true;
			super.state(res, "endDate", "assistant.tutorial.form.error.is.after.tuto");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;
		Collection<Course> courses;
		final SelectChoices coursesChoices;
		final Tuple tuple;

		courses = this.repository.findAllCourses();
		coursesChoices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "code", "tittle", "summary", "goals", "startDate", "endDate", "draftMode");
		tuple.put("course", coursesChoices.getSelected().getKey());
		tuple.put("courses", coursesChoices);

		super.getResponse().setData(tuple);
	}
}
