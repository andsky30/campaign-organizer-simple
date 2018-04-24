package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Campaign  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "keywords")
    private List<String> keywords;
    private BigDecimal bidAmount;
    private BigDecimal fund;
    private CampaignStatus status;
    private String town;
    private BigDecimal radius;


    public Campaign(String name, List<String> keywords, BigDecimal bidAmount,
                    BigDecimal fund, CampaignStatus status, String town, BigDecimal radius) {
        this.name = name;
        this.keywords = keywords;
        this.bidAmount = bidAmount;
        this.fund = fund;
        this.status = status;
        this.town = town;
        this.radius = radius;
    }

    public Campaign() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }
}
