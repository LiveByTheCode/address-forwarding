package us.livebythecode.architecture.referencemodels.testing.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.livebythecode.architecture.referencemodels.testing.domain.ForwardTask;

@Data
@AllArgsConstructor
public class AddressForwardCreationResponseDTO {
    private ForwardTask forwardTask;
}
