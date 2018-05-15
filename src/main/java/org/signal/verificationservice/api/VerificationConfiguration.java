package org.signal.verificationservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class VerificationConfiguration {
    @JsonProperty
    private int percentage = 0;

    @NotNull
    @JsonProperty
    private String host;

    public int getPercentage() {
        return percentage;
    }

    public String getHost() {
        return host;
    }
}
