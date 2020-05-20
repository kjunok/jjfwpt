package com.jjkj.auth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.Map;

public class JJKJAuthExceptionSerializer extends StdSerializer<JJKJAuthException> {
    public JJKJAuthExceptionSerializer() {
        super(JJKJAuthException.class);
    }

    @Override
    public void serialize(JJKJAuthException e, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeStartObject();
        generator.writeObjectField("code", e.getHttpErrorCode());
        String message = e.getMessage();
        if (message != null) {
            message = HtmlUtils.htmlEscape(message);
        }
        generator.writeStringField("msg", message);
        if (e.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                generator.writeStringField(key, add);
            }
        }
        generator.writeEndObject();
    }
}