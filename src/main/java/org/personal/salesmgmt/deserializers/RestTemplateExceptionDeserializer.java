package org.personal.salesmgmt.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.personal.salesmgmt.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class RestTemplateExceptionDeserializer extends StdDeserializer<ApiException> {

    public RestTemplateExceptionDeserializer() {
        this(null);
    }

    public RestTemplateExceptionDeserializer(Class<?> v) {
        super(v);
    }

    @Override
    public ApiException deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return new ApiException(HttpStatus.BAD_REQUEST, node.get("message").asText(), node.get("debugMessage").asText());
    }
}
