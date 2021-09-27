package domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class EmptyModel {
    public static final EmptyModel INSTANCE = new EmptyModel();

    private EmptyModel() {

    }
}
