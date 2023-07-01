package hello.world.old;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@jakarta.ws.rs.Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldOldServlet {
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