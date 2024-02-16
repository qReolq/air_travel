package parser;

import model.Ticket;

import java.io.IOException;
import java.util.List;

public interface TicketParser {

    List<Ticket> parse() throws IOException;

}
