package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.CampaignResource;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.CampaignService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;
@Singleton
public class CampaignController extends Controller {

    private final CampaignService campaignService;

    @Inject
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    public Result getAllCampaigns() {
        return ok(toJson(campaignService.getAll().stream()
                .map(Json::toJson)
                .collect(Collectors.toCollection(ArrayList::new))));
    }

    public Result getOneCampaign(String id) {
        return ok(toJson(campaignService.getOne(id)));
    }

    public Result createCampaign() {
        JsonNode json = request().body().asJson();
        CampaignResource campaignResource = Json.fromJson(json, CampaignResource.class);
        CampaignResource saved = campaignService.create(campaignResource);
        return created(toJson(saved));
    }

    public Result deleteCampaign(String id) {
        campaignService.delete(id);
        return ok(toJson("Campaign with Id: " + id + " has been deleted successfully!"));
    }

    public Result updateCampaign() {
        JsonNode json = request().body().asJson();
        CampaignResource campaignResource = Json.fromJson(json, CampaignResource.class);
        CampaignResource updated = campaignService.update(campaignResource);
        return ok(toJson(updated));
    }
}
