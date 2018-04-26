package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dto.CampaignResource;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.CampaignService;
import service.exceptions.CampaignNotFoundException;
import service.exceptions.InvalidStatusException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class CampaignController extends Controller {

    private final FormFactory formFactory;
    private final CampaignService campaignService;

    @Inject
    public CampaignController(CampaignService campaignService, FormFactory formFactory) {
        this.campaignService = campaignService;
        this.formFactory = formFactory;
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
        Form<CampaignResource> formFromRequest = getFormFromRequest();
        if (formFromRequest.hasErrors()) {
            return badRequest(formFromRequest.errorsAsJson());
        } else {
            try {
                CampaignResource campaignResource = getCampaignResourceFromRequest();
                CampaignResource saved = campaignService.create(campaignResource);
                return created(toJson(saved));
            } catch (InvalidStatusException ex) {
                return badRequest(toJson(ex.getMessage()));
            } catch (CampaignNotFoundException ex) {
                return notFound(toJson(ex.getMessage()));
            }
        }
    }

    public Result deleteCampaign(String id) {
        try {
            campaignService.delete(id);
            return ok(toJson("Campaign with Id: " + id + " has been deleted successfully!"));
        } catch (CampaignNotFoundException ex) {
            return notFound(toJson(ex.getMessage()));
        }
    }

    public Result updateCampaign() {
        Form<CampaignResource> formFromRequest = getFormFromRequest();
        if (formFromRequest.hasErrors()) {
            return badRequest(formFromRequest.errorsAsJson());
        } else {
            try {
                CampaignResource campaignResource = getCampaignResourceFromRequest();
                CampaignResource updated = campaignService.update(campaignResource);
                return ok(toJson(updated));
            } catch (InvalidStatusException ex) {
                return badRequest(toJson(ex.getMessage()));
            } catch (CampaignNotFoundException ex) {
                return notFound(toJson(ex.getMessage()));
            }
        }
    }

    private Form<CampaignResource> getFormFromRequest() {
        return formFactory.form(CampaignResource.class).bindFromRequest();
    }

    private CampaignResource getCampaignResourceFromRequest() {
        JsonNode json = request().body().asJson();
        return Json.fromJson(json, CampaignResource.class);
    }

    public Result getKeywords() {
        String[] keywords = campaignService.getKeywords();
        return ok(toJson(keywords));
    }

    public Result getTowns() {
        String[] towns = campaignService.getTowns();
        return ok(toJson(towns));
    }

    public Result getAccountBalance() {
        return ok(toJson(campaignService.getAccountBalance()));
    }

}
