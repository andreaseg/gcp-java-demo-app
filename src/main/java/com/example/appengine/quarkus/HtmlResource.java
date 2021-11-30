package com.example.appengine.quarkus;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.TemplateError;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

@OpenAPIDefinition(info = @Info(title = "Base Api", version = "1.0.0"))
@Path("/")
public class HtmlResource {

    static final String TEMPLATE = readResource("/template.html");


    @GET
    @Produces(MediaType.TEXT_HTML)
    public String createWebPage(@Context UriInfo uriInfo)  {

        var request = uriInfo.getQueryParameters();

        var title = request.getFirst("title");
        var body = request.getFirst("body");

        Objects.requireNonNull(title);
        Objects.requireNonNull(body);

        var jinjava = new Jinjava();
        var renderResult = jinjava.renderForResult(TEMPLATE, request);

        if (renderResult.hasErrors()) {
            throw new IllegalStateException(
                    "Render errors:" + lineSeparator() +
                    renderResult.
                            getErrors().
                            stream().
                            map(HtmlResource::stringifyError).
                            collect(Collectors.joining(lineSeparator())
            ));
        }

        return renderResult.getOutput();
    }

    private static String stringifyError(TemplateError error) {
        return String.format("Error at %s, %s: %s", error.getLineno(), error.getStartPosition(), error.getMessage());
    }

    private static String readResource(String path) {
        try (InputStream inputStream = Objects.requireNonNull(HtmlResource.class.getResourceAsStream(path));
             ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            for (int length; (length = inputStream.read()) != -1; ) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
