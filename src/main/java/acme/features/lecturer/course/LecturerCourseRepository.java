
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entitites.course.Course;
import acme.entitites.course.CourseLecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select count(c) > 0 from Course c where c.code like :code")
	boolean existsCourseWithCodeParam(@Param("code") String code);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findAllCoursesByLecturerId(int id);
	@Query("select c from Course c where c.lecturer.userAccount.id = :id")
	Collection<Course> findAllCoursesByLecturerUserAccountId(int id);

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	Lecturer findLecturerByUserAccountId(int id);

	@Query("select c from CourseLecture c where c.course.id = :courseId and c.lecture.id = :lectureId")
	CourseLecture findCourseLectureByCourseAndLecture(int courseId, int lectureId);
}
