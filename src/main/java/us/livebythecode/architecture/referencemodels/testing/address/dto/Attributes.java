package us.livebythecode.architecture.referencemodels.testing.address.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Loc_name", "Score", "Match_addr", "Addr_type", "AddNum", "Side", "StPreDir", "StPreType",
        "StName", "StType", "StDir", "StAddr", "City", "Subregion", "Region", "RegionAbbr", "Postal", "Country",
        "LangCode", "Distance", "X", "Y", "DisplayX", "DisplayY", "Xmin", "Xmax", "Ymin", "Ymax", "AddNumFrom",
        "AddNumTo", "PostalExt", "Rank" })
@Data
public class Attributes {

    @JsonProperty("Loc_name")
    private String locName;
    @JsonProperty("Score")
    private Integer score;
    @JsonProperty("Match_addr")
    private String matchAddr;
    @JsonProperty("Addr_type")
    private String addrType;
    @JsonProperty("AddNum")
    private String addNum;
    @JsonProperty("Side")
    private String side;
    @JsonProperty("StPreDir")
    private String stPreDir;
    @JsonProperty("StPreType")
    private String stPreType;
    @JsonProperty("StName")
    private String stName;
    @JsonProperty("StType")
    private String stType;
    @JsonProperty("StDir")
    private String stDir;
    @JsonProperty("StAddr")
    private String stAddr;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Subregion")
    private String subregion;
    @JsonProperty("Region")
    private String region;
    @JsonProperty("RegionAbbr")
    private String regionAbbr;
    @JsonProperty("Postal")
    private String postal;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("LangCode")
    private String langCode;
    @JsonProperty("Distance")
    private Integer distance;
    @JsonProperty("X")
    private Double x;
    @JsonProperty("Y")
    private Double y;
    @JsonProperty("DisplayX")
    private Double displayX;
    @JsonProperty("DisplayY")
    private Double displayY;
    @JsonProperty("Xmin")
    private Double xmin;
    @JsonProperty("Xmax")
    private Double xmax;
    @JsonProperty("Ymin")
    private Double ymin;
    @JsonProperty("Ymax")
    private Double ymax;
    @JsonProperty("AddNumFrom")
    private String addNumFrom;
    @JsonProperty("AddNumTo")
    private String addNumTo;
    @JsonProperty("PostalExt")
    private String postalExt;
    @JsonProperty("Rank")
    private String rank;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}