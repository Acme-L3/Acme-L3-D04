
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseListService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Course> objects;
		final int id = super.getRequest().getPrincipal().getAccountId();
		objects = this.repository.findAllCoursesByLecturerId(id);

		super.getBuffer().setData(objects);

	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link");
		super.getResponse().setData(tuple);
	}

}