package org.signal.verificationservice.api;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class VerificationClient {

    private final static String COMPLETE_PATH = "/v1/verification/complete";
    private final static String DELIVER_PATH = "/v1/verification/deliver";

    private final Client client;

    private final String completeURI;
    private final String deliverURI;

    private VerificationClient(Builder b, String hostname) throws InvalidHostnameException {
            this.client = b.client;
            this.completeURI = "https://" + hostname + COMPLETE_PATH;
            this.deliverURI = "https://" + hostname + DELIVER_PATH;
    }

    public Response completeVerification(@NotEmpty String phoneNumber) {
//        return client.target(completeURI.normalize().toString() + "/" + phoneNumber)
        return client.target(completeURI + "/" + phoneNumber)
                .request()
                .put(null);
    }

    public DeliveryResult deliver(@NotEmpty String verificationCode,
                                  @NotEmpty String phoneNumber,
                                  @NotEmpty String transport,
                                  @NotEmpty String clientOS,
                                  @Length(min = 2, max = 2) String isoCountry,
                                  @Length(min = 2, max = 2) String isoLanguage,
                                  @Length(min = 3, max = 3) String mcc,
                                  @Length(min = 1, max = 3) String mnc) {

        final DeliveryInfo deliveryInfo = new DeliveryInfo(verificationCode, phoneNumber, transport, clientOS,
                isoCountry, isoLanguage, mcc, mnc);

        final Response response = client.target(deliverURI)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(deliveryInfo, MediaType.APPLICATION_JSON_TYPE));

        return response.readEntity(DeliveryResult.class);
    }

    public static class Builder {
        private final Client client;
        private final String hostname;

        public Builder(@NotNull String hostname, Environment environment, JerseyClientConfiguration config) {
            this.client = new JerseyClientBuilder(environment).using(config).build(environment.getName());
            this.hostname = hostname;
        }

        public Builder(@NotNull String hostname, Environment environment) {
            this(hostname, environment, new JerseyClientConfiguration());
        }

        public VerificationClient build() throws InvalidHostnameException {
            return new VerificationClient(this, hostname);
        }
    }

    private class DeliveryInfo {
        private String verificationCode;
        private String destination;
        private String transport;
        private String clientOS;
        private String country;
        private String language;
        private String mcc;
        private String mnc;

        DeliveryInfo(String verificationCode, String destination, String transport, String clientOS, String country,
                     String language, String mcc, String mnc) {

            this.verificationCode = verificationCode;
            this.destination = destination;
            this.transport = transport;
            this.clientOS = clientOS;
            this.country = country;
            this.language = language;
            this.mcc = mcc;
            this.mnc = mnc;
        }
    }
}
