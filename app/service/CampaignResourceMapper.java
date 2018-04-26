package service;

import dto.CampaignResource;
import model.Campaign;
import model.CampaignStatus;
import service.exceptions.InvalidStatusException;
import utils.ConvertUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CampaignResourceMapper {

    public Campaign mapToCampaign(CampaignResource campaignResource) {
        return new Campaign(campaignResource.getName(),
                campaignResource.getKeywords(),
                new BigDecimal(campaignResource.getBidAmount()),
                new BigDecimal(campaignResource.getFund()),
                ConvertUtil.resolveCampaignStatus(campaignResource.getStatus()),
                campaignResource.getTown(),
                new BigDecimal(campaignResource.getRadius()));
    }

    public CampaignResource mapToCampaignResource(Campaign campaign) {
        return new CampaignResource(campaign.getId().toString(),
                campaign.getName(),
                campaign.getKeywords(),
                ConvertUtil.convertBigDecimalToString(campaign.getBidAmount()),
                ConvertUtil.convertBigDecimalToString(campaign.getFund()),
                ConvertUtil.convertCampaignStatusToString(campaign.getStatus()),
                campaign.getTown(),
                ConvertUtil.convertBigDecimalToString(campaign.getRadius()));
    }
}
