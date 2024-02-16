import model.Ticket;
import parser.TicketParser;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketProcessor {

    private final List<Ticket> tickets;

    public TicketProcessor(TicketParser ticketParser) {
        try {
            this.tickets = ticketParser.parse();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error parsing tickets", e);
        }
    }


    public Map<String, Duration> calculateMinFlightTime(String origin, String destination) {
        Map<String, Duration> minFightTimes = new HashMap<>();

        for (Ticket ticket : tickets) {
            if (ticket.getOrigin().equals(origin)
                    && ticket.getDestination().equals(destination)) {
                String carrier = ticket.getCarrier();
                Duration curTime = calculateFlightDuration(
                        ticket.getDepartureDateTime(), ticket.getArrivalDateTime()
                );
                if (!minFightTimes.containsKey(carrier)
                        || minFightTimes.get(carrier).compareTo(curTime) > 0) {
                    minFightTimes.put(carrier, curTime);
                }
            }
        }
        return minFightTimes;
    }

    public double calculatePriceDifference(String origin, String destination) {
        List<Double> prices = tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination))
                .map(Ticket::getPrice)
                .collect(Collectors.toList());
        double sum = prices.stream().mapToDouble(Double::doubleValue).sum();

        double median = calcMedian(prices);

        return (sum / prices.size()) - median;
    }

    private double calcMedian(List<Double> prices) {
        Collections.sort(prices);
        int mid = prices.size() / 2;

        if (prices.size() % 2 == 0)
            return (prices.get(mid - 1) + prices.get(mid)) / 2;
        return prices.get(mid);
    }

    private Duration calculateFlightDuration(String departureTime, String arrivalTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        LocalDateTime departureDateTime = LocalDateTime.parse(departureTime, formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse(arrivalTime, formatter);
        return Duration.between(departureDateTime, arrivalDateTime);
    }

}
