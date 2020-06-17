package dto;

public class ItemDTO {
    
    private String joke;
    private String id;

    public ItemDTO() {
    }

    public ItemDTO(String joke, String id) {
        this.joke = joke;
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
