package utcluj.isp.curs3.inheritance;

class Ticket {
    private String event;
    private String venue;
    private String date;
    private int price;

    public Ticket(String event, String venue, String date, int price) {
        this.event = event;
        this.venue = venue;
        this.date = date;
        this.price = price;
    }

    public String getEvent() {
        return event;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public void printTicket() {
        System.out.println("Event: " + event);
        System.out.println("Venue: " + venue);
        System.out.println("Date: " + date);
        System.out.println("Price: " + price);
    }
}

class ConcertTicket extends Ticket {
    private String artist;

    public ConcertTicket(String event, String venue, String date, int price, String artist) {
        super(event, venue, date, price);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public void printTicket() {
        super.printTicket();
        System.out.println("Artist: " + artist);
    }
}

class SportsTicket extends Ticket {
    private String team1;
    private String team2;

    public SportsTicket(String event, String venue, String date, int price, String team1, String team2) {
        super(event, venue, date, price);
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    @Override
    public void printTicket() {
        super.printTicket();
        System.out.println("Teams: " + team1 + " vs. " + team2);
    }
}

public class TicketDemo {
    public static void main(String[] args) {
        Ticket ticket1 = new Ticket("Conference", "Convention Center", "2022-05-15", 50);
        ticket1.printTicket();

        ConcertTicket ticket2 = new ConcertTicket("Concert", "Stadium", "2022-06-20", 100, "The Rolling Stones");
        ticket2.printTicket();

        SportsTicket ticket3 = new SportsTicket("Sports Event", "Arena", "2022-07-01", 75, "Team A", "Team B");
        ticket3.printTicket();
    }
}
