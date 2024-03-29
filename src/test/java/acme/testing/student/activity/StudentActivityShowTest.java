
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;


	// Test methods -----------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentIndex, final String title, final String summary, final String activityType, final String initDate, final String endDate, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Activities");
		super.clickOnListingRecord(0);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("initDate", initDate);
		super.checkInputBoxHasValue("endDate", endDate);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}
	@Test
	public void test300Hacking() {

		String param;
		final Collection<Enrolment> enrolments = this.repository.findEnrolmentByStudentUsername("student1");

		for (final Enrolment enrolment : enrolments) {
			final Collection<Activity> activities = this.repository.findActivitiesByEnrolmentId(enrolment.getId());
			for (final Activity activity : activities)
				if (!activity.getEnrolment().isDraftMode()) {
					param = String.format("id=%d", activity.getEnrolment().getId());

					super.checkLinkExists("Sign in");
					super.request("/student/activity/show", param);
					super.checkPanicExists();

					super.signIn("administrator", "administrator");
					super.request("/student/activity/show");
					super.checkPanicExists();
					super.signOut();

					super.signIn("auditor1", "auditor1");
					super.request("/student/activity/show");
					super.checkPanicExists();
					super.signOut();

					super.signIn("lecturer1", "lecturer1");
					super.request("/student/activity/show");
					super.checkPanicExists();
					super.signOut();

					super.signIn("company1", "company1");
					super.request("/student/activity/show");
					super.checkPanicExists();
					super.signOut();

					super.signIn("assistant1", "assistant1");
					super.request("/student/activity/show");
					super.checkPanicExists();
					super.signOut();
				}
		}
	}
}
