
package acme.testing.student.enrolment;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentShowTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/show-enrolment.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation, final String goals, final String course) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("courseShow", course);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//There are not any negative test cases for this feature
	}

	@Test
	public void test300Hacking() {

		final List<Enrolment> enrolments = this.repo.findEnrolmentByStudentName("student1");

		super.checkLinkExists("Sign in");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/show", "id=" + e.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/show", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student2", "student2");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/show", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/show", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/show", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/show", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/show", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}
}
