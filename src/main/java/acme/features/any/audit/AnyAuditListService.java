
package acme.features.any.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyAuditListService extends AbstractService<Any, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyAuditRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Collection<Audit> objects;
		objects = this.repository.findAuditsWithCourse();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");
		super.getResponse().setData(tuple);
	}
}