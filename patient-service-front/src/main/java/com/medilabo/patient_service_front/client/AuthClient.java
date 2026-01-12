package com.medilabo.patient_service_front.client;


import com.medilabo.patient_service_front.models.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class AuthClient extends AbstractClient {

    private final static String LOGIN_PATH = "/login";

    public boolean isValidCredential(LoginRequest loginRequest) {

        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(LOGIN_PATH)
                .toUriString();

        try {
            HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest);
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Void.class
            );
            log.info("Credential were ok");
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                log.error("Wrong credential {}", e.getStatusCode(), e);
                return false;
            }
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error when checking credential with gateway", e);
            return false;
        }
    }

}
