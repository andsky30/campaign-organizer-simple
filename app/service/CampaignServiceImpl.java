package service;

import dto.CampaignResource;
import model.Campaign;
import repository.CampaignRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignResourceMapper campaignResourceMapper;

    @Inject
    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignResourceMapper campaignResourceMapper) {
        this.campaignRepository = campaignRepository;
        this.campaignResourceMapper = campaignResourceMapper;
    }

    @Override
    public List<CampaignResource> getAll() {
        return campaignRepository.getAll().stream()
                .map(campaignResourceMapper::mapToCampaignResource)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public CampaignResource getOne(String id) {
        Campaign campaign = campaignRepository.getOne(Long.parseLong(id));
        if (campaign == null) {
            throw new RuntimeException("Campaign with ID: " + id + " doesn't exist!");
        } else {
            return campaignResourceMapper.mapToCampaignResource(campaign);
        }
    }

    @Override
    public CampaignResource create(CampaignResource campaignResource) {
        Campaign campaign = campaignResourceMapper.mapToCampaign(campaignResource);
        Campaign saved = campaignRepository.add(campaign);
        return campaignResourceMapper.mapToCampaignResource(saved);
    }

    @Override
    public void delete(String id) {
        Campaign campaign = campaignRepository.getOne(Long.parseLong(id));
        if (campaign == null) {
            throw new RuntimeException("Campaign with ID: " + id + " doesn't exist!");
        } else {
            campaignRepository.delete(campaign);
        }
    }

    @Override
    public CampaignResource update(CampaignResource campaignResource) {
        Campaign campaignToUpdate = campaignRepository.getOne(Long.parseLong(campaignResource.getId()));
        if (campaignToUpdate == null) {
            throw new RuntimeException("Campaign with ID: " + campaignResource.getId() + " doesn't exist!");
        } else {
            Campaign updatedCampaign = updateCampaignData(campaignToUpdate, campaignResource);
            return campaignResourceMapper.mapToCampaignResource(campaignRepository.update(updatedCampaign));
        }
    }

    private Campaign updateCampaignData(Campaign campaignToUpdate, CampaignResource campaignResource) {
        campaignToUpdate.setName(campaignResource.getName());
        campaignToUpdate.setTown(campaignResource.getTown());
        return campaignToUpdate;
    }
}
