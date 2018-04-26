package service;

import com.google.inject.ImplementedBy;
import dto.CampaignResource;

import java.util.List;

@ImplementedBy(CampaignServiceImpl.class)
public interface CampaignService {

    List<CampaignResource> getAll();

    CampaignResource getOne(String id);

    CampaignResource create(CampaignResource campaignResource);

    void delete(String id);

    CampaignResource update(CampaignResource campaignResource);

    String [] getKeywords();

    String [] getTowns();
}
