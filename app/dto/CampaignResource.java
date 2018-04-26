package dto;

import java.util.List;
import play.data.validation.Constraints.*;
import utils.PatternUtil;

public class CampaignResource {

    private String id;

    @Required(message = "Name cannot be empty")
    private String name;

    @Required(message = "Keywords cannot be empty")
    private List<String> keywords;

    @Required(message = "Bid amount cannot be empty")
    @Pattern(value = PatternUtil.DECIMAL_NUMBER_PATTERN_REGEX,
            message = "Bid amount  " + PatternUtil.DECIMAL_NUMBER_PATTERN_MESSAGE)
    private String bidAmount;

    @Required(message = "Fund cannot be empty")
    @Pattern(value = PatternUtil.DECIMAL_NUMBER_PATTERN_REGEX,
            message = "Fund " + PatternUtil.DECIMAL_NUMBER_PATTERN_MESSAGE)
    private String fund;

    @Required(message = "Status cannot be empty")
    private String status;

    @Required(message = "Town cannot be empty")
    private String town;

    @Required(message = "Radius cannot be empty")
    @Pattern(value = PatternUtil.DECIMAL_NUMBER_PATTERN_REGEX,
            message = "Radius " + PatternUtil.DECIMAL_NUMBER_PATTERN_MESSAGE)
    private String radius;

    public CampaignResource(String id, String name, List<String> keywords,
                            String bidAmount, String fund, String status, String town, String radius) {
        this.id = id;
        this.name = name;
        this.keywords = keywords;
        this.bidAmount = bidAmount;
        this.fund = fund;
        this.status = status;
        this.town = town;
        this.radius = radius;
    }

    public CampaignResource(String name,  List<String> keywords, String bidAmount,
                            String fund, String status, String town, String radius) {
        this.name = name;
        this.keywords = keywords;
        this.bidAmount = bidAmount;
        this.fund = fund;
        this.status = status;
        this.town = town;
        this.radius = radius;
    }

    public CampaignResource() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }
}
