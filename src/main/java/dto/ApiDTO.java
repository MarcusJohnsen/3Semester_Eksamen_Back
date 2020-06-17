package dto;

import java.util.Objects;

public class ApiDTO {

    private String chuckJoke;
    private String chuckJokeID;
    private String dadJoke;
    private String dadJokeID;
    private String jokeSetup;
    private String jokePunchLine;

    private final static String COULD_NOT_FETCH = "Could not fetch this data";

    public ApiDTO() {
    }

    public ApiDTO(IngredientDTO chuck, ItemDTO dad, StorageDTO officalJoke) {
        if (!Objects.isNull(chuck)) {
            this.chuckJoke = chuck.getValue();
            this.chuckJokeID = chuck.getId();
        } else {
            this.chuckJoke = COULD_NOT_FETCH;
            this.chuckJokeID = COULD_NOT_FETCH;
        }
        if (!Objects.isNull(dad)) {
            this.dadJoke = dad.getJoke();
            this.dadJokeID = dad.getId();
        } else {
            this.dadJoke = COULD_NOT_FETCH;
            this.dadJokeID = COULD_NOT_FETCH;
        }
        if (!Objects.isNull(officalJoke)) {
            this.jokeSetup = officalJoke.getSetup();
            this.jokePunchLine = officalJoke.getPunchline();
        } else {
            this.jokeSetup = COULD_NOT_FETCH;
            this.jokePunchLine = COULD_NOT_FETCH;
        }
    }

    public String getChuckJoke() {
        return chuckJoke;
    }

    public void setChuckJoke(String chuckJoke) {
        this.chuckJoke = chuckJoke;
    }

    public String getChuckJokeID() {
        return chuckJokeID;
    }

    public void setChuckJokeID(String chuckJokeID) {
        this.chuckJokeID = chuckJokeID;
    }

    public String getDadJoke() {
        return dadJoke;
    }

    public void setDadJoke(String dadJoke) {
        this.dadJoke = dadJoke;
    }

    public String getDadJokeID() {
        return dadJokeID;
    }

    public void setDadJokeID(String dadJokeID) {
        this.dadJokeID = dadJokeID;
    }
}