package org.personal.salesmgmt.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.personal.salesmgmt.model.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserPrincipalDeserializer extends StdDeserializer<UserPrincipal> {

    public UserPrincipalDeserializer() {
        this(null);
    }

    public UserPrincipalDeserializer(Class<?> v) {
        super(v);
    }

    @Override
    public UserPrincipal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final List<GrantedAuthority> authorities = new ArrayList<>();
        node.get("authorities").forEach(element -> {
            authorities.add(new SimpleGrantedAuthority(element.get("authority").asText()));
        });
        return new UserPrincipal(node.get("id").asLong(), node.get("username").asText(), authorities);
    }
}
