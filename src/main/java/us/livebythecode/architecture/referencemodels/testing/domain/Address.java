package us.livebythecode.architecture.referencemodels.testing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.livebythecode.architecture.referencemodels.testing.address.dto.Candidate;

@Data
@AllArgsConstructor
public class Address {
	
	private String streetAddress;
	private String city;
	private String state;
	private String postalCode;
	private double x;
	private double y;
	private String addressType;
	private int score;
	
	public Address() {
		
	}
	
	public Address (Candidate candidate) {
		setStreetAddress(candidate.getAttributes().getStAddr());
		setCity(candidate.getAttributes().getCity());
		setState(candidate.getAttributes().getRegionAbbr());
		setPostalCode(candidate.getAttributes().getPostal());
		setX(candidate.getAttributes().getX());
		setY(candidate.getAttributes().getY());
		setAddressType(candidate.getAttributes().getAddrType());
		setScore(candidate.getAttributes().getScore());
	}

}
