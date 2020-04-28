package org.personal.salesmgmt.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.personal.salesmgmt.exceptions.custom.RestTemplateClientException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    @SneakyThrows
    public void handleError(ClientHttpResponse response) {



        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
                String httpBodyResponse = reader.lines().collect(Collectors.joining(""));
                ApiException apiException = new ObjectMapper().readValue(httpBodyResponse, ApiException.class);
                throw new RestTemplateClientException(apiException.getDebugMessage());
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}