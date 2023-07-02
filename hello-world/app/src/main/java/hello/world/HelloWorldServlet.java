package hello.world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@javax.ws.rs.Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String tomcatHome = System.getenv("TOMCAT_HOME");
    private final Path pathToJson = Paths.get(tomcatHome, "temp", "greeting.json");

    @GET
    public Response doGet() throws IOException {
        System.out.println("-------------doGet method-------------");

        try (FileReader fileReader = new FileReader(pathToJson.toString())) {
            JsonNode greetingJsonNode = mapper.readTree(fileReader);

            return Response.ok(greetingJsonNode).build();
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity("File not found: " + e.getMessage()).build();
        } catch (IOException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error reading file: " + e.getMessage())
                    .build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost(JsonNode greetingJsonNode) throws IOException {
        try (FileWriter file = new FileWriter(pathToJson.toString())) {
            String greetingJsonStr = mapper.writeValueAsString(greetingJsonNode);
            file.write(greetingJsonStr);

            return Response.ok(greetingJsonNode).build();
        } catch (IOException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error writing to file: " + e.getMessage())
                    .build();
        }
    }
}
