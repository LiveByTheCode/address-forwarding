package us.livebythecode.architecture.referencemodels.testing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import us.livebythecode.architecture.referencemodels.testing.address.dto.AddressForwardCreationRequestDTO;
import us.livebythecode.architecture.referencemodels.testing.address.dto.AddressForwardCreationResponseDTO;
import us.livebythecode.architecture.referencemodels.testing.address.dto.Attributes;
import us.livebythecode.architecture.referencemodels.testing.address.dto.Candidate;
import us.livebythecode.architecture.referencemodels.testing.address.dto.FindAddressCandidatesResponseDTO;
import us.livebythecode.architecture.referencemodels.testing.address.dto.Location;
import us.livebythecode.architecture.referencemodels.testing.address.dto.SortCandidateByAddressTypeAndScore;
import us.livebythecode.architecture.referencemodels.testing.address.dto.SpatialReference;
import us.livebythecode.architecture.referencemodels.testing.address.externalserviceproxy.AddressValidationClient;
import us.livebythecode.architecture.referencemodels.testing.domain.Address;
import us.livebythecode.architecture.referencemodels.testing.domain.AddressTypeMap;
import us.livebythecode.architecture.referencemodels.testing.domain.ForwardTask;
import us.livebythecode.architecture.referencemodels.testing.persistence.service.api.ForwardTaskPersistenceService;
import us.livebythecode.architecture.referencemodels.testing.service.AddressForwardingService;

@ExtendWith(MockitoExtension.class)
public class AddressForwardingServiceTest {
	
	@InjectMocks
	private AddressForwardingService addressForwardingService;

	@Mock
	private AddressValidationClient addressValidationClient;
	
	@Mock
	private ForwardTaskPersistenceService forwardTaskPersistenceService;
	
	@Mock
	private AddressTypeMap addressTypeMap;
	
	@Mock
	private SortCandidateByAddressTypeAndScore candidateSort;

	@Test
	public void testSave() throws Exception {
		//Arrange
		//Be sure to use doReturn().when() (rather than when().thenReturn()) when stubbing the same method multiple times with different arguments
		doReturn(getOldAddressCandidatesResponseDTO()).when(addressValidationClient).validateAddress("500 Old Dominion Way Thomasville NC 27360","*","json");
		doReturn(getNewAddressCandidatesResponseDTO()).when(addressValidationClient).validateAddress("90 Piedmont Industrial Dr Winston-Salem NC 27107","*","json");
		doReturn(getOutputForwardTask()).when(forwardTaskPersistenceService).createForwardTask(getInputForwardTask());
		when(addressTypeMap.getTotalScore("PointAddress",100)).thenReturn(1100);
		//Act
		AddressForwardCreationResponseDTO result = addressForwardingService.save(new AddressForwardCreationRequestDTO("500 Old Dominion Way", "Thomasville", "NC", "27360", "90 Piedmont Industrial Dr", "Winston-Salem", "NC", "27107", LocalDate.parse("2020-03-05"), LocalDate.parse("2022-03-05")));
		//Assert
		assertEquals(3, result.getForwardTask().getForwardDaysRemaining());
		assertEquals(LocalDate.parse("2020-03-05"), result.getForwardTask().getStart());
		assertEquals(LocalDate.parse("2022-03-05"), result.getForwardTask().getEnd());
		assertEquals(getOldAddress(), result.getForwardTask().getFromAddress());
		assertEquals(getNewAddress(), result.getForwardTask().getToAddress());
		assertEquals(123l, result.getForwardTask().getId());
	}
	
	@Test
	public void testValidateMinimumScore_BarelyBadStreetName_ThrowsException() throws Exception {
		//Arrange
		when(addressTypeMap.getTotalScore("StreetName",79)).thenReturn(879);
		//Act
		Candidate candidate = getCandidate(
				"Old Dominion Way, Thomasville, North Carolina, 27360", 
				79, 
				getAttributes(
						"USAStreetName", 
						"StreetName", 
						79, 
						"Old Dominion Way", 
						"Thomasville", 
						"North Carolina", 
						"NC", 
						"27360", 
						"Davidson", 
						-80.060606, 
						35.90805
				), 
				getLocation(-80.06060462971706, 35.90905609055881)
		);
		//Assert
		assertThrows(Exception.class, () -> {
			//Use reflection (via PowerMock) to call private validateMinimumScore() method
			Whitebox.invokeMethod(addressForwardingService, "validateMinimumScore", candidate);
		});
	}
	
	@Test
	public void testValidateMinimumScore_BarelyBadPointAddress_ThrowsException() throws Exception {
		//Arrange
		when(addressTypeMap.getTotalScore("PointAddress",79)).thenReturn(1079);
		//Act
		Candidate candidate = getCandidate(
				"501 Old Dominion Way, Thomasville, North Carolina, 27360", 
				79, 
				getAttributes(
						"USAPointAddr", 
						"PointAddress", 
						79, 
						"500 Old Dominion Way", 
						"Thomasville", 
						"North Carolina", 
						"NC", 
						"27360", 
						"Davidson", 
						-80.060606, 
						35.90805
				), 
				getLocation(-80.06060462971706, 35.90905609055881)
		);
		//Assert
		assertThrows(Exception.class, () -> {
			//Use reflection (via PowerMock) to call private validateMinimumScore() method
			Whitebox.invokeMethod(addressForwardingService, "validateMinimumScore", candidate);
		});
	}
	
	@Test
	public void testValidateMinimumScore_BarelyGoodStreetMatch_NoException() throws Exception {
		//Arrange
		when(addressTypeMap.getTotalScore("StreetAddress",81)).thenReturn(881);
		//Act
		Candidate candidate = getCandidate(
				"501 Old Dominion Way, Thomasville, North Carolina, 27360", 
				81, 
				getAttributes(
						"USAStreetAddr", 
						"StreetAddress", 
						81, 
						"500 Old Dominion Way", 
						"Thomasville", 
						"North Carolina", 
						"NC", 
						"27360", 
						"Davidson", 
						-80.060606, 
						35.90805
				), 
				getLocation(-80.06060462971706, 35.90905609055881)
		);
		//Assert
		//Use reflection (via PowerMock) to call private validateMinimumScore() method
		Whitebox.invokeMethod(addressForwardingService, "validateMinimumScore", candidate);
	}
	
	//helper methods
	
	private ForwardTask getOutputForwardTask() throws ParseException {
		return new ForwardTask(123l, getOldAddress(), getNewAddress(), LocalDate.parse("2020-03-01"), LocalDate.parse("2020-03-05"), LocalDate.parse("2022-03-05"), 3l);
	}
	
	private ForwardTask getInputForwardTask() throws ParseException {
		return new ForwardTask(getOldAddress(), getNewAddress(),  LocalDate.parse("2020-03-05"), LocalDate.parse("2022-03-05"));
	}
	
	private Address getOldAddress() {
		return new Address("500 Old Dominion Way", "Thomasville", "NC", "27360", -80.060606, 35.90805, "PointAddress", 100);
	}
	
	private Address getNewAddress() {
		return new Address("90 Piedmont Industrial Dr", "Winston-Salem", "NC", "27107", -80.235872, 36.020324, "PointAddress", 100);
	}
	
	private Candidate getCandidate(String singleLineAddress, int score, Attributes attributes, Location location) {
		Candidate candidate = new Candidate();
		candidate.setAddress("500 Old Dominion Way, Thomasville, North Carolina, 27360");
		candidate.setScore(100);
		candidate.setAttributes(attributes);
		candidate.setLocation(location);
		return candidate;
	}
	
	private Attributes getAttributes(String locName, String addrType, int score, String streetAddr, String city, String state, String stateAbbr, String postalCode, String county, double x, double y) {
		Attributes attributes = new Attributes();
		attributes.setLocName(locName);
		attributes.setScore(score);
		attributes.setMatchAddr(streetAddr+", "+city+", "+state+" "+ postalCode);
		attributes.setAddrType(addrType);
		attributes.setStAddr(streetAddr);
		attributes.setCity(city);
		attributes.setSubregion(county);
		attributes.setRegion(state);
		attributes.setRegionAbbr(stateAbbr);
		attributes.setPostal(postalCode);
		attributes.setCountry("USA");
		attributes.setX(x);
		attributes.setY(y);
		return attributes;
	}
	
	private Location getLocation(double x, double y) {
		Location location = new Location();
		location.setX(x);
		location.setY(y);
		return location;
	}
	
	private FindAddressCandidatesResponseDTO getOldAddressCandidatesResponseDTO() {
		FindAddressCandidatesResponseDTO findAddressCandidatesResponseDTO = new FindAddressCandidatesResponseDTO();

		Candidate candidate1 = getCandidate(
				"500 Old Dominion Way, Thomasville, North Carolina, 27360", 
				100, 
				getAttributes(
						"USAPointAddr", 
						"PointAddress", 
						100, 
						"500 Old Dominion Way", 
						"Thomasville", 
						"North Carolina", 
						"NC", 
						"27360", 
						"Davidson", 
						-80.060606, 
						35.90805
				), 
				getLocation(-80.06060462971706, 35.90905609055881)
		);


		Candidate candidate2 = getCandidate(
				"Old Dominion Way, Thomasville, North Carolina, 27360", 
				100, 
				getAttributes(
						"USAStreetName", 
						"StreetName", 
						100, 
						"Old Dominion Way", 
						"Thomasville", 
						"North Carolina", 
						"NC", 
						"27360", 
						"Davidson", 
						-80.058138, 
						35.909730000000003
				), 
				getLocation(-80.058138, 35.909730000000003)
		);

		
		findAddressCandidatesResponseDTO.setSpatialReference(getSpatialReference(4326));
		
		findAddressCandidatesResponseDTO.setCandidates(Arrays.asList(candidate1, candidate2));
		return findAddressCandidatesResponseDTO;
		
	}
	
	private SpatialReference getSpatialReference(int wkid) {
		SpatialReference sr = new SpatialReference();
		sr.setWkid(wkid);
		sr.setLatestWkid(wkid);
		return sr;
	}
	
	private FindAddressCandidatesResponseDTO getNewAddressCandidatesResponseDTO() {
		FindAddressCandidatesResponseDTO findAddressCandidatesResponseDTO = new FindAddressCandidatesResponseDTO();

		
		Candidate candidate1 = getCandidate(
				"90 Piedmont Industrial Dr, Winston-Salem, North Carolina, 27107", 
				100, 
				getAttributes(
						"USAPointAddr", 
						"PointAddress", 
						100, 
						"90 Piedmont Industrial Dr", 
						"Winston-Salem", 
						"North Carolina", 
						"NC", 
						"27107", 
						"Davidson", 
						-80.235872, 
						36.020324
				), 
				getLocation(-80.235872, 36.020324)
		);

		
		Candidate candidate2 = getCandidate(
				"Piedmont Industrial Dr, Winston-Salem, North Carolina, 27107", 
				100, 
				getAttributes(
						"USAStreetName", 
						"StreetName", 
						100, 
						"Piedmont Industrial Dr", 
						"Winston-Salem", 
						"North Carolina", 
						"NC", 
						"27107", 
						"Davidson", 
						-80.235804, 
						36.020108
				), 
				getLocation(-80.235804, 36.020108)
		);
		
		findAddressCandidatesResponseDTO.setSpatialReference(getSpatialReference(4326));
		
		findAddressCandidatesResponseDTO.setCandidates(Arrays.asList(candidate1, candidate2));
		return findAddressCandidatesResponseDTO;
		
	}
}
