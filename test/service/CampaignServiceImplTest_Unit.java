package service;

import dto.CampaignResource;
import model.Campaign;
import model.EmeraldAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.CampaignRepository;
import service.exceptions.CampaignNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static util.TestConstant.*;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceImplTest_Unit {

    private static final int INITIAL_CAMPAIGNS_SIZE = 2;
    private static final String NOT_EXISTING_ID = "345654";

    @Mock
    private CampaignRepository campaignRepository;

    private CampaignResourceMapper campaignResourceMapper = new CampaignResourceMapper();
    private EmeraldAccount emeraldAccount = new EmeraldAccount();

    private List<Campaign> campaigns;
    private Campaign campaign1;
    private Campaign campaign2;

    @InjectMocks
    private CampaignService campaignService = new CampaignServiceImpl(
            campaignRepository, campaignResourceMapper, emeraldAccount);

    @Before
    public void prepareCampaignList() {
        campaign1 = campaignResourceMapper.mapToCampaign(new CampaignResource(CAMP_ID_1, CAMP_NAME_1,
                KEYWORDS_1, BID_AM_1, CAMP_FUND_1, STATUS_1, TOWN_1, RADIUS_1));
        campaign1.setId(Long.parseLong(CAMP_ID_1));
        campaign2 = campaignResourceMapper.mapToCampaign(new CampaignResource(CAMP_ID_2, CAMP_NAME_2,
                KEYWORDS_2, BID_AM_2, CAMP_FUND_2, STATUS_2, TOWN_2, RADIUS_2));
        campaign2.setId(Long.parseLong(CAMP_ID_2));
        campaigns = new ArrayList<>();
        campaigns.add(campaign1);
        campaigns.add(campaign2);

        emeraldAccount.setBalance(new BigDecimal(250000));
    }

    @After
    public void clearCampaignsList() {
        campaigns.clear();
    }

    @Test
    public void shouldReturnTwoCampaigns() {
        //given
        when(campaignRepository.getAll()).thenReturn(campaigns);

        //when
        List<CampaignResource> allCampaigns = campaignService.getAll();

        //then
        assertThat(allCampaigns).isNotEmpty();
        assertThat(allCampaigns).hasSize(INITIAL_CAMPAIGNS_SIZE);

        Optional<CampaignResource> campaignOptional = allCampaigns.stream()
                .filter(n -> n.getId().equals(CAMP_ID_1))
                .findFirst();
        assertThat(campaignOptional.isPresent()).isTrue();
        assertThat(campaignOptional.get().getName()).isEqualTo(CAMP_NAME_1);
        assertThat(campaignOptional.get().getFund()).isEqualTo(CAMP_FUND_1);
    }

    @Test
    public void shouldReturnOneCampaign() {
        //given
        when(campaignRepository.getOne(Long.parseLong(CAMP_ID_1))).thenReturn(campaign1);

        //when
        CampaignResource result = campaignService.getOne(CAMP_ID_1);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(CAMP_ID_1);
        assertThat(result.getTown()).isEqualTo(TOWN_1);
    }

    @Test(expected = CampaignNotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenIsIsInvalid() {
        //given

        //when
        campaignService.getOne(NOT_EXISTING_ID);

        //then
    }

    @Test
    public void shouldAddOneCampaign() {
        //given
        CampaignResource campaignToAdd = new CampaignResource(CAMP_NAME_3,
                KEYWORDS_3, BID_AM_3, CAMP_FUND_3, STATUS_3, TOWN_3, RADIUS_3);
        Campaign campaignEntity = campaignResourceMapper.mapToCampaign(campaignToAdd);
        campaignEntity.setId(Long.parseLong(CAMP_ID_3));

        when(campaignRepository.add(any(Campaign.class))).thenReturn(addCampaignToList(campaignEntity));
        when(campaignRepository.getAccount()).thenReturn(emeraldAccount);

        //when
        CampaignResource addedCampaign = campaignService.create(campaignToAdd);

        //then
        assertThat(campaigns).hasSize(INITIAL_CAMPAIGNS_SIZE + 1);
        assertThat(addedCampaign).isNotNull();
        assertThat(addedCampaign.getName()).isEqualTo(CAMP_NAME_3);
    }

    private Campaign addCampaignToList(Campaign campaign) {
        campaigns.add(campaign);
        return campaign;
    }

    @Test
    public void shouldUpdateCampaign() {
        //given
        String updatedCampaignName = CAMP_NAME_3;
        CampaignResource updatedCampaign2 = new CampaignResource(CAMP_ID_2, updatedCampaignName,
                KEYWORDS_2, BID_AM_2, CAMP_FUND_2, STATUS_2, TOWN_2, RADIUS_2);

        when(campaignRepository.getOne(Long.parseLong(CAMP_ID_2))).thenReturn(campaign2);
        when(campaignRepository.getAccount()).thenReturn(emeraldAccount);
        when(campaignRepository.update(any(Campaign.class))).thenReturn((campaign2));

        //when
        CampaignResource updatedCampaign = campaignService.update(updatedCampaign2);

        //then
        verify(campaignRepository, times(1)).update(any(Campaign.class));
    }

    @Test
    public void shouldRemoveOneCampaign() {
        //given
        when(campaignRepository.getOne(Long.parseLong(CAMP_ID_2))).thenReturn(campaign2);
        doAnswer(a -> removeCampaignFromList(campaign2)).when(campaignRepository).delete(campaign2);
        when(campaignRepository.getAccount()).thenReturn(emeraldAccount);

        //when
        campaignService.delete(CAMP_ID_2);

        //then
        verify(campaignRepository, times(1)).delete(any(Campaign.class));
        assertThat(campaigns).hasSize(INITIAL_CAMPAIGNS_SIZE - 1);
    }

    private Object removeCampaignFromList(Campaign campaign) {
        campaigns.remove(campaign);
        return null;
    }

    @Test(expected = CampaignNotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenInvalidIdWhileRemoving() {
        //given
        when(campaignRepository.getOne(Long.parseLong(NOT_EXISTING_ID))).thenReturn(null);

        //when
        campaignService.delete(NOT_EXISTING_ID);

        //then
    }
}