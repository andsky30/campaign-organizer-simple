package dto;

public class CampaignResource {

    private String id;
    private String name;
    private String town;

    public CampaignResource(String id, String name, String town) {
        this.id = id;
        this.name = name;
        this.town = town;
    }

    public CampaignResource(String name, String town) {
        this.name = name;
        this.town = town;
    }

    public CampaignResource() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
