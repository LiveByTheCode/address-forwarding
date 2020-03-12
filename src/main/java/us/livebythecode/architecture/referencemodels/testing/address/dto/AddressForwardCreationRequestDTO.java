package us.livebythecode.architecture.referencemodels.testing.address.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressForwardCreationRequestDTO {
    private String fromStreetAddress;
    private String fromCity;
    private String fromState;
    private String fromPostalCode;

    private String toStreetAddress;
    private String toCity;
    private String toState;
    private String toPostalCode;

    private LocalDate startDate;
    private LocalDate endDate;

    public String getOldSingleLineAddress() {
        return fromStreetAddress + " " + fromCity + " " + fromState + " " + fromPostalCode;
    }

    public String getNewSingleLineAddress() {
        return toStreetAddress + " " + toCity + " " + toState + " " + toPostalCode;
    }
}
