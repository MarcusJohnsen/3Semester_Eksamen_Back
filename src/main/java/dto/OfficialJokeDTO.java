package dto;

public class OfficialJokeDTO {
    
    private final static String JOKE_URL = "https://official-joke-api.appspot.com/random_joke";
    private String id;
    private String type;
    private String setup;
    private String punchline;

    public OfficialJokeDTO() {
    }

    public OfficialJokeDTO(String id, String type, String setup, String punchline) {
        this.id = id;
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    public static String getJOKE_URL() {
        return JOKE_URL;
    }
    
    
    
}
