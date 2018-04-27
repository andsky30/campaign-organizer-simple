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
    }

    private void loadInitialData() {
        emeraldAccount.setBalance(new BigDecimal(150000));
        campaignRepository.saveAccount(emeraldAccount);

        String[] keywords = campaignRepository.getKeywords();
        String[] keywords1 = {keywords[1], keywords[3], keywords[8]};
        String[] keywords2 = {keywords[2], keywords[5], keywords[7]};
        Campaign campaign1 = createCampaign("Initial campaign", keywords1, 150);
        Campaign campaign2 = createCampaign("Product campaign", keywords2, 2000);
        campaignRepository.add(campaign1);
        campaignRepository.add(campaign2);
    }

    private Campaign createCampaign(String name, String[] keywords1, int num) {
        return new Campaign(
                name,
                new ArrayList<>(Arrays.asList(keywords1)),
                new BigDecimal(num +50),
                new BigDecimal(num * 5),
                CampaignStatus.OFF,
                "Krakow",
                new BigDecimal(num)
        );
    }

    private void sayGoodbye() {
        System.out.println("Goodbye");
    }
}
