package repository;

import model.Campaign;
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
}
