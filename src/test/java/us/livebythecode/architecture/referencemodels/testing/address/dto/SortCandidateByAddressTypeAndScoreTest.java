package us.livebythecode.architecture.referencemodels.testing.address.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import us.livebythecode.architecture.referencemodels.testing.address.dto.Attributes;
import us.livebythecode.architecture.referencemodels.testing.address.dto.Candidate;
import us.livebythecode.architecture.referencemodels.testing.address.dto.Location;
import us.livebythecode.architecture.referencemodels.testing.address.dto.SortCandidateByAddressTypeAndScore;
import us.livebythecode.architecture.referencemodels.testing.domain.AddressTypeMap;

@ExtendWith(MockitoExtension.class)
public class SortCandidateByAddressTypeAndScoreTest {
	
	@InjectMocks
	private SortCandidateByAddressTypeAndScore sort;

	@Mock
	private AddressTypeMap atm;

	@Test
	public void testCompare_Prefer1stCandidate_ReturnNeg1() {
		//Arrange
		doReturn(1100).when(atm).getTotalScore("PointAddress", 100);
		doReturn(900).when(atm).getTotalScore("StreetName", 100);
		//Act
		int result = sort.compare(getCandidate1(), getCandidate2());
		//Assert
		assertEquals(-1, result);
	}

	@Test
	public void testCompare_Prefer2ndCandidate_Return1() {
		//Arrange
		doReturn(1100).when(atm).getTotalScore("PointAddress", 100);
		doReturn(900).when(atm).getTotalScore("StreetName", 100);
		//Act
		int result = sort.compare(getCandidate2(), getCandidate1());
		//Assert
		assertEquals(1, result);
	}

	@Test
	public void testCompare_BothCandidatesSamePreference_Return0() {
		//Arrange
		doReturn(1100).when(atm).getTotalScore("PointAddress", 100);
		//Act
		int result = sort.compare(getCandidate1(), getCandidate1());
		//Assert
		assertEquals(0, result);
	}
	
	//Helper methods
	
	private Candidate getCandidate1() {
		return getCandidate(
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
	}
	
	private Candidate getCandidate2() {
		return getCandidate(
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

}
