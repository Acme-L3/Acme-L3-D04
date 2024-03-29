
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditorCreateService extends AbstractService<Authenticated, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditorRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = !super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Auditor object;
		Principal principal;
		int uaId;
		final UserAccount ua;

		principal = super.getRequest().getPrincipal();
		uaId = principal.getAccountId();
		ua = this.repository.findUserAccountById(uaId);

		object = new Auditor();
		object.setUserAccount(ua);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Auditor object) {
		assert object != null;
		super.bind(object, "firm", "professionalID", "certifications", "link");
	}

	@Override
	public void validate(final Auditor object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("professionalId")) {
			Auditor existing;
			existing = this.repository.findOneAuditorByProfessionalId(object.getProfessionalID());
			super.state(existing == null || existing.equals(object), "professionalID", "authenticated.auditor.form.error.duplicated");
		}
	}

	@Override
	public void perform(final Auditor object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Auditor object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "firm", "professionalID", "certifications", "link");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
