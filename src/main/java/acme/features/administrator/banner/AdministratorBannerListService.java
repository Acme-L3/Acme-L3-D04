
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

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
		Collection<Banner> objects;
		objects = this.repository.findAllBanners();

		super.getBuffer().setData(objects);

	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "startDate", "endDate", "slogan");
		super.getResponse().setData(tuple);
	}

}
