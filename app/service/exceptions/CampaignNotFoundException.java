package service.exceptions;

public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException(String id) {
        super("Cannot found campaign with Id: " + id);
    }
}
