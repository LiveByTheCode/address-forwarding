package us.livebythecode.architecture.referencemodels.testing.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForwardTask {

    public ForwardTask() {

    }

    public ForwardTask(Address oldAddress, Address newAddress, LocalDate start, LocalDate end) {
        this.fromAddress = oldAddress;
        this.toAddress = newAddress;
        this.start = start;
        this.end = end;
    }

    private long id;

    private Address fromAddress;
    private Address toAddress;

    private LocalDate created;
    private LocalDate start;
    private LocalDate end;
    private long forwardDaysRemaining;

}
