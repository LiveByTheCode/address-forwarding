package us.livebythecode.architecture.referencemodels.testing.domain;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AddressTypeMap extends HashMap<String, Integer>{
	private static final long serialVersionUID = -5665723800765859012L;

	
	public AddressTypeMap() {
		this.put("PointAddress", 1000);
		this.put("StreetAddress", 800);
		this.put("StreetName", 600);
		this.put("Locality", 400);
		this.put("Postal", 200);
	}
	
	public int getTotalScore(String addressType, int score) {
		int returnScore = 0;
		if(this.containsKey(addressType)){
			returnScore += this.get(addressType) + score;
		}else {
			log.warn("AddressTypeMap doesn't contain an entry for address type {}. This address candidate will be given a low priority.",addressType);
		}
		return returnScore;
	}

}
