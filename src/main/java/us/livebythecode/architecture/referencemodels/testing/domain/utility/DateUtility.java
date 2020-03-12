package us.livebythecode.architecture.referencemodels.testing.domain.utility;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import us.livebythecode.architecture.referencemodels.testing.exception.BadInputDateRangeException;

@ApplicationScoped
@Slf4j
public class DateUtility {

    @Inject
    CurrentDate currentDate;

    public long daysRemaining(LocalDate startDate, LocalDate endDate) {
        long daysRemaining = 0;
        LocalDate now = currentDate.getLocalDate();
        System.out.println("StartDate: "+startDate+" EndDate: "+endDate);
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            log.error("End date ({}) can not be before Start date ({}) (and neither date may be null)", endDate, startDate);
            throw new BadInputDateRangeException(startDate, endDate);
        } else if (startDate.isAfter(now)) {
            daysRemaining = ChronoUnit.DAYS.between(startDate, endDate);
        } else if (now.isBefore(endDate)) {
            daysRemaining = ChronoUnit.DAYS.between(now, endDate);
        }
        return daysRemaining;
    }
 
}
