
package acme.features.authenticated.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("auditId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int auditId;
		final Audit audit;
		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findAuditById(auditId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final AuditingRecord object;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findAuditById(auditId);
		object = new AuditingRecord();
		object.setSubject("");
		object.setAssessment("");
		object.setInitialMoment(null);
		object.setFinalMoment(null);
		object.setMark("");
		object.setLink("");
		object.setCorrection(false);
		object.setAudit(audit);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;
		super.bind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link", "correction");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link", "correction");
		tuple.put("auditId", super.getRequest().getData("auditId", int.class));
		tuple.put("draftMode", object.getAudit().isDraftMode());
		tuple.put("correction", false);
		super.getResponse().setData(tuple);
	}

}