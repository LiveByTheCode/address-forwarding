package us.livebythecode.architecture.referencemodels.testing.address.externalserviceproxy;


import javax.ws.rs.POST;
import javax.ws.rs.FormParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import us.livebythecode.architecture.referencemodels.testing.address.dto.FindAddressCandidatesResponseDTO;

@RegisterRestClient(configKey="address-validation-api")
//The filter below is used to override Esri's incorrect text/plain header response
@RegisterProvider(HeaderOverwriteFilter.class)
public interface AddressValidationClient {
	@POST
    public FindAddressCandidatesResponseDTO validateAddress(@FormParam("SingleLine") String singleLine, @FormParam("outFields") String outFields, @FormParam("f") String f);
}
