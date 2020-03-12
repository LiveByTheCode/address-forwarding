package us.livebythecode.architecture.referencemodels.testing.exception;

import java.time.LocalDate;

public class BadInputDateRangeException extends RuntimeException {

    private static final long serialVersionUID = 5297298323027891544L;

    public BadInputDateRangeException(LocalDate startDate, LocalDate endDate) {
        super("Input date range is not valid : " + startDate + " - " + endDate);
    }

}
