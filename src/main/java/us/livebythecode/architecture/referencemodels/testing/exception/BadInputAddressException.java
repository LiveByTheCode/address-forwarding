package us.livebythecode.architecture.referencemodels.testing.exception;

import us.livebythecode.architecture.referencemodels.testing.domain.Address;

public class BadInputAddressException extends RuntimeException{

	private static final long serialVersionUID = 5297298323027891544L;

	public BadInputAddressException(Address address) {
        super("Input address is not valid : " + address);
    }

}
