
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.practicums.Practicum;
import acme.entitites.session.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		PracticumSession object;
		int practicumSessionId;
		Principal principal;

		practicumSessionId = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumSessionById(practicumSessionId);
		principal = super.getRequest().getPrincipal();

		status = object != null && principal.hasRole(object.getPracticum().getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;
		Practicum practicum;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumSessionById(id);
		practicum = this.repo.findPracticumById(object.getPracticum().getId());

		super.getBuffer().setData(object);
		super.getResponse().setGlobal("draftMode", practicum.getDraftMode());
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "initialDate", "endDate", "link");
		tuple.put("id", object.getId());

		super.getResponse().setData(tuple);
	}

}
