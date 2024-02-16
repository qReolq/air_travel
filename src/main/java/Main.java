import parser.JsonTicketParser;

import java.time.Duration;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Usage: Main <JsonPath>");

        TicketProcessor processor = new TicketProcessor(
                new JsonTicketParser(args[0])
        );

        System.out.println("Minimum flight time between Vladivostok and Tel Aviv for each carrier:");
        Map<String, Duration> minFightTimes = processor.calculateMinFlightTime("VVO", "TLV");
        for (Map.Entry<String, Duration> minFightTime : minFightTimes.entrySet()) {
            System.out.printf(
                    "%s: %s hours %s minutes%n",
                    minFightTime.getKey(),
                    minFightTime.getValue().toHours(),
                    minFightTime.getValue().toMinutesPart()
            );
        }
        System.out.println("Difference between the average price and the median " +
                "price for a flight between Vladivostok and Tel Aviv");
        System.out.println(processor.calculatePriceDifference("VVO", "TLV"));
    }
}