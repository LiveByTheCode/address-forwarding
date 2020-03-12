package us.livebythecode.architecture.referencemodels.testing.domain.utility;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CurrentDate {
	public LocalDate getLocalDate() {
		return LocalDate.now();
	}
}
