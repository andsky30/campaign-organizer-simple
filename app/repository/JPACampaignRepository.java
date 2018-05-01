package repository;

import model.Campaign;
import model.EmeraldAccount;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class JPACampaignRepository implements CampaignRepository {

    private final JPAApi jpaApi;

    @Inject
    public JPACampaignRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public List<Campaign> getAll() {
        final List<Campaign> campaigns = new ArrayList<>();
        jpaApi.withTransaction(() ->{
            EntityManager em = jpaApi.em();
            Query query = em.createQuery("SELECT c FROM Campaign c");
            campaigns.addAll(query.getResultList());
        });
        return campaigns;
    }

    @Override
    public Campaign getOne(Long id) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.find(Campaign.class, id);
        });
    }

    @Override
    public Campaign add(Campaign campaign) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            em.persist(campaign);
        });
        return campaign;
    }

    @Override
    public void delete(Campaign campaign) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            em.remove(em.contains(campaign) ? campaign : em.merge(campaign));
        });
    }

    @Override
    public Campaign update(Campaign campaign) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.merge(campaign);
        });
    }

    @Override
    public String [] getKeywords() {
        //little bit hardcoded for exercise purposes
        String[] keywordsArr = {"campaign", "quality", "product", "price",
                "clients", "time", "media", "ad", "logo"};
        return keywordsArr;
    }

    @Override
    public String[] getTowns() {
        //little bit hardcoded for exercise purposes
        String [] townsArr = {"Krakow", "Rzeszow", "Wroclaw", "Katowice", "Radom", "Kalisz", "Gdansk"};
        return townsArr;
    }

    @Override
    public EmeraldAccount saveAccount(EmeraldAccount emeraldAccount) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.merge(emeraldAccount);
        });
    }

    @Override
    public EmeraldAccount getAccount() {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.find(EmeraldAccount.class, EmeraldAccount.EMERALD_ACCOUNT_ID);
        });
    }

    @Override
    public void deleteAll() {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            em.createQuery("DELETE FROM Campaign").executeUpdate();
        });
    }
}
