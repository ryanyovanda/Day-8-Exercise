import java.util.ArrayList;
import java.util.Scanner;

// Event class to store event information
class Event {
    private String name;
    private String date;
    private int availableSeats;

    public Event(String name, String date, int availableSeats) {
        this.name = name;
        this.date = date;
        this.availableSeats = availableSeats;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean bookSeats(int numberOfSeats) {
        if (availableSeats >= numberOfSeats) {
            availableSeats -= numberOfSeats;
            return true;
        }
        return false;
    }

    public void returnSeats(int numberOfSeats) {
        availableSeats += numberOfSeats;
    }

    @Override
    public String toString() {
        return "Event: " + name + " | Date: " + date + " | Available Seats: " + availableSeats;
    }
}

// Booking class to store booking details
class Booking {
    private Event event;
    private int numberOfTickets;
    private boolean confirmed;

    public Booking(Event event, int numberOfTickets) {
        this.event = event;
        this.numberOfTickets = numberOfTickets;
        this.confirmed = false;
    }

    public Event getEvent() {
        return event;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void confirm() {
        confirmed = true;
    }

    public void cancel() {
        event.returnSeats(numberOfTickets);
    }

    @Override
    public String toString() {
        return "Booking: " + numberOfTickets + " tickets for " + event.getName() + " on " + event.getDate() + " | Confirmed: " + confirmed;
    }
}

// Main event booking system
public class Main {
    private ArrayList<Event> events;
    private ArrayList<Booking> bookings;
    private Booking pendingBooking;

    public Main() {
        events = new ArrayList<>();
        bookings = new ArrayList<>();
        initializeEvents();
        pendingBooking = null;
    }

    private void initializeEvents() {
        events.add(new Event("Concert A", "2024-10-01", 50));
        events.add(new Event("Concert B", "2024-10-15", 100));
        events.add(new Event("Theater C", "2024-11-05", 75));
    }

    public void displayEvents() {
        System.out.println("Available Events:");
        for (int i = 0; i < events.size(); i++) {
            System.out.println((i + 1) + ". " + events.get(i));
        }
    }

    public void bookEvent(int eventIndex, int numberOfTickets) {
        if (eventIndex >= 0 && eventIndex < events.size()) {
            Event event = events.get(eventIndex);
            if (event.bookSeats(numberOfTickets)) {
                pendingBooking = new Booking(event, numberOfTickets);
                System.out.println("Booking successful! Now confirm your booking.");
            } else {
                System.out.println("Booking Failed: Not enough seats available.");
            }
        } else {
            System.out.println("Invalid event selection.");
        }
    }

    public void confirmBooking() {
        if (pendingBooking == null) {
            System.out.println("No pending booking to confirm.");
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println(pendingBooking);
            System.out.print("Do you want to confirm the booking? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();

            if (response.equals("yes")) {
                pendingBooking.confirm();
                bookings.add(pendingBooking);
                System.out.println("Booking confirmed: " + pendingBooking);
                pendingBooking = null;
            } else if (response.equals("no")) {
                pendingBooking.cancel();
                System.out.println("Booking canceled, seats returned.");
                pendingBooking = null;
            } else {
                System.out.println("Invalid input, returning to main menu.");
            }
        }
    }

    public static void main(String[] args) {
        Main system = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. View Events\n2. Book Event\n3. Confirm Pending Booking\n0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    system.displayEvents();
                    break;
                case 2:
                    system.displayEvents();
                    System.out.print("Select an event number to book: ");
                    int eventChoice = scanner.nextInt();
                    System.out.print("Enter the number of tickets to book: ");
                    int tickets = scanner.nextInt();
                    system.bookEvent(eventChoice - 1, tickets);
                    break;
                case 3:
                    system.confirmBooking();
                    break;
                case 0:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
