
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.practicums.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Principal principal;
		Collection<Practicum> practicums;

		principal = super.getRequest().getPrincipal();
		practicums = this.repo.findPracticumByCompanyId(principal.getActiveRoleId());

		super.getBuffer().setData(practicums);
	}

	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;

		Tuple tuple;

		tuple = super.unbind(practicum, "code", "title", "draftMode");
		tuple.put("courseCode", practicum.getCourse().getCode());
		tuple.put("draftMode", practicum.getDraftMode());

		super.getResponse().setData(tuple);
	}

}
