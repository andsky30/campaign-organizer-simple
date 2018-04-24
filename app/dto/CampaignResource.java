package dto;

import java.util.List;

public class CampaignResource {

    private String id;
    private String name;
    private List<String> keywords;
    private String bidAmount;
    private String fund;
    private String status;
    private String town;
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
