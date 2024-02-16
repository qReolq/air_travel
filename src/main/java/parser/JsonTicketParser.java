package parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import model.Ticket;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonTicketParser implements TicketParser {

    private final String filePath;

    public JsonTicketParser(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Ticket> parse() throws IOException {
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new FileReader(filePath));
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("tickets");

        List<Ticket> tickets = new ArrayList<>();
        jsonArray.forEach(it -> tickets.add(gson.fromJson(it, Ticket.class)));

        return tickets;
    }

}
