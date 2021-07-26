package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Organisation;
import domain.Ticket;
import domain.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataPreparation {
    public static final List<User> users = new ArrayList<>();
    public static final List<Ticket> tickets = new ArrayList<>();
    public static final List<Organisation> organisations = new ArrayList<>();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String filePath = "/";

    public static void loadData() {
        readJsonFile("./users.json", User.class, users);
        readJsonFile("./organizations.json", Organisation.class, organisations);
        readJsonFile("./tickets.json", Ticket.class, tickets);
    }

    private static void readJsonFile(String filePath, Class<?> clazz, List list) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader(filePath));
            for (Object object : array) {
                JSONObject person = (JSONObject) object;
                list.add(objectMapper.readValue(person.toJSONString(), clazz));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
