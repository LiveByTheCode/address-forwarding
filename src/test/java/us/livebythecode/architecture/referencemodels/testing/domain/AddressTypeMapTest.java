package us.livebythecode.architecture.referencemodels.testing.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import us.livebythecode.architecture.referencemodels.testing.domain.AddressTypeMap;

public class AddressTypeMapTest {

	@Test
	public void testAddressTypeMap() {
		
	}

	@Test
	public void testGetTotalScore() {
		//Arrange
		AddressTypeMap atm = new AddressTypeMap();
		//Act
		int result = atm.getTotalScore("PointAddress", 80);
		//Assert
		assertEquals(1080, result);
	}

	@Test
	public void testGetTotalScore_BadAddressType_Zero() {
		//Arrange
		AddressTypeMap atm = new AddressTypeMap();
		//Act
		int result = atm.getTotalScore("BlahBlahBlah", 80);
		//Assert
		assertEquals(0, result);
	}

}
