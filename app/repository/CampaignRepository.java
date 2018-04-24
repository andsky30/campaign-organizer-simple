package repository;

import com.google.inject.ImplementedBy;
import com.sun.glass.ui.View;
import model.Campaign;

import java.util.List;

@ImplementedBy(JPACampaignRepository.class)
public interface CampaignRepository {

    List<Campaign> getAll();

    Campaign getOne(Long id);

    Campaign add(Campaign campaign);

    void delete(Campaign campaign);

    Campaign update(Campaign campaign);
}
