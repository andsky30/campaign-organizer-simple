package service;

import dto.CampaignResource;
import model.Campaign;

public class CampaignResourceMapper {

    public Campaign mapToCampaign(CampaignResource campaignResource) {
        return new Campaign(campaignResource.getName(),
                campaignResource.getTown());
    }

    public CampaignResource mapToCampaignResource(Campaign campaign) {
        return new CampaignResource(campaign.getId().toString(),
                campaign.getName(),
                campaign.getTown());
    }
}
