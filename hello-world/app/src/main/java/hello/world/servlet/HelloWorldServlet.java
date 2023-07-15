package hello.world.servlet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hello.world.utils.io.DirectoryUtil;

@javax.ws.rs.Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldServlet {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldServlet.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path pathToJson;

    public HelloWorldServlet() throws IOException {
        String pathToCatalinaTmpDir = System.getenv("CATALINA_TMPDIR");
        Path pathToAppTmpDir = DirectoryUtil.ensureDirectoryExists(Paths.get(pathToCatalinaTmpDir, "hello-world-app"));
        this.pathToJson = Paths.get(pathToAppTmpDir.toString(), "greeting.json");

        if (!Files.exists(this.pathToJson)) {
            try (FileWriter fileWriter = new FileWriter(this.pathToJson.toFile())) {
                fileWriter.write("{}");
            }
        }
    }

    @GET
    public Response getGreeting() throws IOException {
        logger.debug("-------------getGreeting method-------------");

        try (FileReader fileReader = new FileReader(pathToJson.toString())) {
            JsonNode greetingJsonNode = mapper.readTree(fileReader);

            logger.info(greetingJsonNode.toString());
            return Response.ok(greetingJsonNode).build();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
            return Response.status(Status.NOT_FOUND).entity("File not found: " + e.getMessage()).build();
        } catch (IOException e) {
            logger.error("Io error", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error reading file: " + e.getMessage())
                    .build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response greet(JsonNode greetingJsonNode) throws IOException {
        logger.debug("-------------greet method-------------");

        try (FileWriter file = new FileWriter(pathToJson.toString())) {
            String greetingJsonStr = mapper.writeValueAsString(greetingJsonNode);
            file.write(greetingJsonStr);

            logger.info(greetingJsonStr);
            return Response.ok(greetingJsonNode).build();
        } catch (IOException e) {
            logger.error("Io error", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error writing to file: " + e.getMessage())
                    .build();
        }
    }
}
