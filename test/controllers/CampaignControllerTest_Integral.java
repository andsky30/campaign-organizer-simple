package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.CampaignResource;

import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import repository.CampaignRepository;
import service.CampaignService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static play.libs.Json.toJson;
import static play.test.Helpers.*;
import static util.TestConstant.*;

public class CampaignControllerTest_Integral extends WithApplication {

    private static final int CAMPAIGNS_INITIAL_SIZE_2 = 2;
    private static final int NOT_EXISTING_ID = 345;
    private CampaignRepository campaignRepository;
    private CampaignService campaignService;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Before
    public void setup() {
        campaignRepository = app.injector().instanceOf(CampaignRepository.class);
        campaignService = app.injector().instanceOf(CampaignService.class);
    }

    // Initially there are two campaigns in DB, added by DataLoader.class.
    // DB is cleared after each test because of Hibernate settings: hibernate.hbm2ddl.auto=create.

    @Test
    public void shouldReturnAllNotes() {
        //given

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/api/campaigns");

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.OK);

        final String body = contentAsString(result);
        assertThat(body).contains("\"id\":\"1\"");
        assertThat(body).contains("\"id\":\"2\"");
    }

    @Test
    public void shouldReturnSingleNoteById() {
        //given

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/api/campaigns/1");

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.OK);

        final String body = contentAsString(result);
        assertThat(body).contains("\"id\":\"1\"");
    }

    @Test
    public void shouldReturnNotFoundStatusWhenCampaignIdIsInvalid() {
        //given

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/api/campaigns/" + NOT_EXISTING_ID);

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.NOT_FOUND);

        final String body = contentAsString(result);
        assertThat(body).isEqualTo("\"Cannot found campaign with Id: " + NOT_EXISTING_ID +"\"");
    }

    @Test
    public void shouldAddOneCampaign() {
        //given
        CampaignResource campaignToAdd = new CampaignResource(CAMP_NAME_1,
                KEYWORDS_1, BID_AM_1, CAMP_FUND_1, STATUS_1, TOWN_1, RADIUS_1);
        JsonNode jsonRequestBody = toJson(campaignToAdd);

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/api/campaigns")
                .bodyJson(jsonRequestBody);

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.CREATED);
        assertThat(campaignRepository.getAll()).hasSize(CAMPAIGNS_INITIAL_SIZE_2 + 1);
    }

    @Test
    public void shouldNotAllowToAddCampaignWithoutName() {
        //given
        CampaignResource campaignToAdd = new CampaignResource(null,
                KEYWORDS_1, BID_AM_1, CAMP_FUND_1, STATUS_1, TOWN_1, RADIUS_1);
        JsonNode jsonRequestBody = toJson(campaignToAdd);

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/api/campaigns")
                .bodyJson(jsonRequestBody);

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.BAD_REQUEST);
        assertThat(campaignRepository.getAll()).hasSize(CAMPAIGNS_INITIAL_SIZE_2);
    }

    @Test
    public void shouldRemoveOneCampaign() {
        //given
        CampaignResource campaignToAdd = new CampaignResource(CAMP_NAME_1,
                KEYWORDS_1, BID_AM_1, CAMP_FUND_1, STATUS_1, TOWN_1, RADIUS_1);
        CampaignResource addedCampaign = campaignService.create(campaignToAdd);
        String id = addedCampaign.getId();

        assertThat(campaignRepository.getAll()).hasSize(CAMPAIGNS_INITIAL_SIZE_2 + 1);

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/api/campaigns/" + id);

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.OK);
        assertThat(campaignRepository.getAll()).hasSize(CAMPAIGNS_INITIAL_SIZE_2);
    }

    @Test
    public void shouldReturnNotFoundStatusWhenIdIsInvalid() {
        //given

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/api/campaigns/" + NOT_EXISTING_ID);

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.NOT_FOUND);
        assertThat(campaignRepository.getAll()).hasSize(CAMPAIGNS_INITIAL_SIZE_2);
    }

    @Test
    public void shouldUpdateOneCampaign() {
        //given
        CampaignResource campaignToAdd = new CampaignResource("1", CAMP_NAME_1,
                KEYWORDS_1, BID_AM_1, CAMP_FUND_1, STATUS_1, TOWN_1, RADIUS_1);
        JsonNode jsonRequestBody = toJson(campaignToAdd);

        //when
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .uri("/api/campaigns")
                .bodyJson(jsonRequestBody);

        //then
        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(Http.Status.OK);

        final String body = contentAsString(result);
        assertThat(body).contains("\"name\":\"" + CAMP_NAME_1 + "\"");
    }

}