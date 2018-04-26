package service;

import dto.CampaignResource;
import model.Campaign;
import model.CampaignStatus;
import service.exceptions.InvalidStatusException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CampaignResourceMapper {

    public Campaign mapToCampaign(CampaignResource campaignResource) {
        return new Campaign(campaignResource.getName(),
                campaignResource.getKeywords(),
                new BigDecimal(campaignResource.getBidAmount()),
                new BigDecimal(campaignResource.getFund()),
                resolveCampaignStatus(campaignResource.getStatus()),
                campaignResource.getTown(),
                new BigDecimal(campaignResource.getRadius()));
    }

    public CampaignResource mapToCampaignResource(Campaign campaign) {
        return new CampaignResource(campaign.getId().toString(),
                campaign.getName(),
                campaign.getKeywords(),
                convertBigDecimalToString(campaign.getBidAmount()),
                convertBigDecimalToString(campaign.getFund()),
                convertCampaignStatusToString(campaign.getStatus()),
                campaign.getTown(),
                convertBigDecimalToString(campaign.getRadius()));
    }

    private String convertBigDecimalToString(BigDecimal bd) {
        bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        return df.format(bd);
    }

    private CampaignStatus resolveCampaignStatus(String status) {
        if (status.equals("on")) {
            return CampaignStatus.ON;
        } else if (status.equals("off")) {
            return CampaignStatus.OFF;
        } else throw new InvalidStatusException();
    }

    private String convertCampaignStatusToString(CampaignStatus status){
        if (status.equals(CampaignStatus.ON)) {
            return "on";
        } else if (status.equals(CampaignStatus.OFF)) {
            return "off";
        } else throw new InvalidStatusException();
    }
}
