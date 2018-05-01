package service;

import dto.CampaignResource;
import model.Campaign;
import model.EmeraldAccount;
import repository.CampaignRepository;
import service.exceptions.CampaignNotFoundException;
import service.exceptions.InvalidStatusException;
import utils.ConvertUtil;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CampaignServiceImpl implements CampaignService {

    private CampaignRepository campaignRepository;
    private final CampaignResourceMapper campaignResourceMapper;
    private final EmeraldAccount emeraldAccount;

    @Inject
    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignResourceMapper campaignResourceMapper,
                               EmeraldAccount emeraldAccount) {
        this.campaignRepository = campaignRepository;
        this.campaignResourceMapper = campaignResourceMapper;
        this.emeraldAccount = emeraldAccount;
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
            throw new CampaignNotFoundException(id);
        } else {
            return campaignResourceMapper.mapToCampaignResource(campaign);
        }
    }

    @Override
    public CampaignResource create(CampaignResource campaignResource) throws InvalidStatusException {
        Campaign campaign = campaignResourceMapper.mapToCampaign(campaignResource);
        updateAccountBalanceWhenAddingCampaign(campaign);
        Campaign saved = campaignRepository.add(campaign);
        return campaignResourceMapper.mapToCampaignResource(saved);
    }

    private void updateAccountBalanceWhenAddingCampaign(Campaign campaign) {
        BigDecimal fund = campaign.getFund();
        BigDecimal balance = campaignRepository.getAccount().getBalance();
        BigDecimal newBalance = balance.subtract(fund);
        saveNewBalance(newBalance);
    }

    private void saveNewBalance(BigDecimal newBalance) {
        emeraldAccount.setBalance(newBalance);
        campaignRepository.saveAccount(emeraldAccount);
    }

    @Override
    public void delete(String id) {
        Campaign campaign = campaignRepository.getOne(Long.parseLong(id));
        if (campaign == null) {
            throw new CampaignNotFoundException(id);
        } else {
            BigDecimal oldFund = new BigDecimal(campaign.getFund().toString());
            updateAccountBalanceWhenDeletingCampaign(oldFund);
            campaignRepository.delete(campaign);
        }
    }

    private void updateAccountBalanceWhenDeletingCampaign(BigDecimal oldFund) {
        BigDecimal balance = campaignRepository.getAccount().getBalance();
        BigDecimal actualBalance = balance.add(oldFund);
        saveNewBalance(actualBalance);
    }

    @Override
    public CampaignResource update(CampaignResource campaignResource) {
        Campaign campaignToUpdate = campaignRepository.getOne(Long.parseLong(campaignResource.getId()));

        if (campaignToUpdate == null) {
            throw new CampaignNotFoundException(campaignResource.getId());
        } else {
            BigDecimal oldFund = new BigDecimal(campaignToUpdate.getFund().toString());
            Campaign updatedCampaign = updateCampaignData(campaignToUpdate, campaignResource);
            updateAccountBalanceWhenUpdatingCampaign(oldFund, updatedCampaign);
            Campaign updated = campaignRepository.update(updatedCampaign);
            return campaignResourceMapper.mapToCampaignResource(updated);
        }
    }

    private Campaign updateCampaignData(Campaign campaignToUpdate, CampaignResource campaignResource) {
        Campaign newCampaign = campaignResourceMapper.mapToCampaign(campaignResource);
        campaignToUpdate.setName(newCampaign.getName());
        campaignToUpdate.setKeywords(newCampaign.getKeywords());
        campaignToUpdate.setBidAmount(newCampaign.getBidAmount());
        campaignToUpdate.setFund(newCampaign.getFund());
        campaignToUpdate.setStatus(newCampaign.getStatus());
        campaignToUpdate.setTown(newCampaign.getTown());
        campaignToUpdate.setRadius(newCampaign.getRadius());
        return campaignToUpdate;
    }

    private void updateAccountBalanceWhenUpdatingCampaign(BigDecimal oldFund, Campaign newCampaign) {
        BigDecimal newFund = newCampaign.getFund();
        BigDecimal fundToSubtract = oldFund.subtract(newFund);
        BigDecimal balance = campaignRepository.getAccount().getBalance();
        BigDecimal newBalance = balance.add(fundToSubtract);
        saveNewBalance(newBalance);
    }

    @Override
    public String[] getKeywords() {
        return campaignRepository.getKeywords();
    }

    @Override
    public String[] getTowns() {
        return campaignRepository.getTowns();
    }

    @Override
    public String getAccountBalance() {
        EmeraldAccount account = campaignRepository.getAccount();
        return ConvertUtil.convertBigDecimalToString(account.getBalance());
    }
}
