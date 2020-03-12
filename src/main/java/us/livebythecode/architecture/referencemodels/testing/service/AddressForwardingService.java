package us.livebythecode.architecture.referencemodels.testing.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import lombok.extern.slf4j.Slf4j;
import us.livebythecode.architecture.referencemodels.testing.address.dto.AddressForwardCreationRequestDTO;
import us.livebythecode.architecture.referencemodels.testing.address.dto.AddressForwardCreationResponseDTO;
import us.livebythecode.architecture.referencemodels.testing.address.dto.Candidate;
import us.livebythecode.architecture.referencemodels.testing.address.dto.FindAddressCandidatesResponseDTO;
import us.livebythecode.architecture.referencemodels.testing.address.dto.SortCandidateByAddressTypeAndScore;
import us.livebythecode.architecture.referencemodels.testing.address.externalserviceproxy.AddressValidationClient;
import us.livebythecode.architecture.referencemodels.testing.domain.Address;
import us.livebythecode.architecture.referencemodels.testing.domain.AddressTypeMap;
import us.livebythecode.architecture.referencemodels.testing.domain.ForwardTask;
import us.livebythecode.architecture.referencemodels.testing.exception.BadInputAddressException;
import us.livebythecode.architecture.referencemodels.testing.persistence.service.api.ForwardTaskPersistenceService;


@ApplicationScoped
@Slf4j
public class AddressForwardingService {
	
	@Inject
	@RestClient
	private AddressValidationClient addressValidationClient;
	
	@Inject
	private ForwardTaskPersistenceService forwardTaskPersistenceService;
	
	@Inject
	private AddressTypeMap atm;
	
	@Inject
	private SortCandidateByAddressTypeAndScore candidateSort;
	
	
	
	public AddressForwardCreationResponseDTO save(AddressForwardCreationRequestDTO requestDTO) {
		return new AddressForwardCreationResponseDTO(
				persistForwardTask(
						requestDTO.getOldSingleLineAddress(),
						requestDTO.getNewSingleLineAddress(),
						requestDTO.getStartDate(),
						requestDTO.getEndDate()
		));
	}
	
	/*
	 * Validate the old and new addresses, persist, and return the persisted record
	 */
	private ForwardTask persistForwardTask(String oldAddress, String newAddress, LocalDate startDate, LocalDate endDate) {
		return forwardTaskPersistenceService.createForwardTask(new ForwardTask(
				validateSingleLineAddress(oldAddress),
				validateSingleLineAddress(newAddress),
				startDate,
				endDate));
	}
	
	private Address validateSingleLineAddress(String address) {
		return getBestAddressFromCandidates(addressValidationClient.validateAddress(address,"*","json"));
	}
	
	/*
	 * Sort the candidate addresses by the sum of address-type + score by assigning a hierarchical numeric value 
	 * to address type (PointAddress (1000) > StreetAddress (800) > StreetName (600) etc...). Once this is done, 
	 * the first address in the list is the best candidate.
	 */
	private Address getBestAddressFromCandidates(FindAddressCandidatesResponseDTO facResponseDTO) {
		List<Candidate> candidateList = facResponseDTO.getCandidates();
		candidateList.sort(candidateSort);
		validateMinimumScore(candidateList.get(0));
		return new Address(candidateList.get(0));
	}
	
	/*
	 * Checking to see if address match is at least PointAddress (numeric value of 1000) with a minimum score of 80
	 * or if not, if it's a StreetAddress (numeric value of 800) with a minimum score of 80
	 */
	private void validateMinimumScore(Candidate candidate) {
		int score = atm.getTotalScore(candidate.getAttributes().getAddrType(), candidate.getAttributes().getScore());
		if(!(score > 1080) && !(score > 880 && score < 1000)) {
			log.error("Input is invalid: {}",new Address(candidate));
			throw new BadInputAddressException(new Address(candidate));
		}
	}


}
