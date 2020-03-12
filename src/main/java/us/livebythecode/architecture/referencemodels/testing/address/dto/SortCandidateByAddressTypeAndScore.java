package us.livebythecode.architecture.referencemodels.testing.address.dto;

import java.util.Comparator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import us.livebythecode.architecture.referencemodels.testing.domain.AddressTypeMap;

@ApplicationScoped
public class SortCandidateByAddressTypeAndScore implements Comparator<Candidate> {

    @Inject
    private AddressTypeMap atm;

    public int compare(Candidate candidate1, Candidate candidate2) {
        int response = 0;
        if (getTotalScore(candidate1) < getTotalScore(candidate2)) {
            response = 1;
        } else if (getTotalScore(candidate1) > getTotalScore(candidate2)) {
            response = -1;
        }
        return response;
    }

    private int getTotalScore(Candidate candidate) {
        return atm.getTotalScore(candidate.getAttributes().getAddrType(), candidate.getAttributes().getScore());
    }
}
