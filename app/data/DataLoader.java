package data;

import model.Campaign;
import model.CampaignStatus;
import model.EmeraldAccount;
import play.inject.ApplicationLifecycle;
import repository.CampaignRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Singleton
public class DataLoader {

    private final CampaignRepository campaignRepository;
    private final EmeraldAccount emeraldAccount;

    @Inject
    public DataLoader(ApplicationLifecycle applicationLifecycle, CampaignRepository campaignRepository,
                      EmeraldAccount emeraldAccount) {
        this.campaignRepository = campaignRepository;
        this.emeraldAccount = emeraldAccount;

        applicationLifecycle.addStopHook(() -> {
            sayGoodbye();
            return CompletableFuture.completedFuture(null);
        });

        loadInitialData();

        emeraldAccount.setBalance(new BigDecimal(150000));
        campaignRepository.saveAccount(emeraldAccount);

    }

    private void loadInitialData() {
        String[] keywords = campaignRepository.getKeywords();
        String[] keywords1 = {keywords[1], keywords[3], keywords[8]};
        Campaign campaign1 = new Campaign(
                "name",
                new ArrayList<>(Arrays.asList(keywords1)),
                new BigDecimal(123.45),
                new BigDecimal(123.45),
                CampaignStatus.OFF,
                "Rzeszow",
                new BigDecimal(345)
        );
        campaignRepository.add(campaign1);
    }

    public void sayGoodbye() {
        System.out.println("Goodbye");
    }

}
