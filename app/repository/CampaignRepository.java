package repository;

import com.google.inject.ImplementedBy;
import model.Campaign;
import model.EmeraldAccount;

import java.util.List;

@ImplementedBy(JPACampaignRepository.class)
public interface CampaignRepository {

    List<Campaign> getAll();

    Campaign getOne(Long id);

    Campaign add(Campaign campaign);

    void delete(Campaign campaign);

    Campaign update(Campaign campaign);

    String [] getKeywords();

    String [] getTowns();

    EmeraldAccount saveAccount(EmeraldAccount emeraldAccount);

    EmeraldAccount getAccount();

    void deleteAll();
}
