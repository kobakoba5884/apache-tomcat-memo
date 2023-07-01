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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelloWorldOldServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String tomcatHome = System.getenv("TOMCAT_HOME");
    private final Path pathToJson = Paths.get(tomcatHome, "temp", "greeting.json");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (FileReader fileReader = new FileReader(pathToJson.toString())) {
            JsonNode greetingJsonNode = mapper.readTree(fileReader);

            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(greetingJsonNode));
        } catch (FileNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("File not found: " + e.getMessage());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error reading file: " + e.getMessage());
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonNode greetingJsonNode = mapper.readTree(request.getInputStream());

        try (FileWriter file = new FileWriter(pathToJson.toString())) {
            String greetingJsonStr = mapper.writeValueAsString(greetingJsonNode);
            file.write(greetingJsonStr);

            response.setContentType("application/json");
            response.getWriter().write(greetingJsonStr);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error writing to file: " + e.getMessage());
        }
    }
}
