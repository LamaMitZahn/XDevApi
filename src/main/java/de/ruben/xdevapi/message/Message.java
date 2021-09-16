package de.ruben.xdevapi.message;

import java.io.Serializable;

public class Message implements Serializable {

    private String message;
    private Boolean withPrefix;

    public Message(String message, Boolean withPrefix) {
        this.message = message;
        this.withPrefix = withPrefix;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isWithPrefix() {
        return withPrefix;
    }

    public void setWithPrefix(Boolean withPrefix) {
        this.withPrefix = withPrefix;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", withPrefix=" + withPrefix +
                '}';
    }
}
