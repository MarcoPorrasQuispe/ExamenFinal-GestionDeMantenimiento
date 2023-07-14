import java.util.HashMap;
import java.util.List;

public class Customer {
    private String name;
    private List<MovieRental> rentals;

    public Customer(String name, List<MovieRental> rentals) {
        this.name = name;
        this.rentals = rentals;
    }

    public String getName() {
        return name;
    }

    public List<MovieRental> getRentals() {
        return rentals;
    }
}

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

        RentalInfo rentalInfo = new RentalInfo();
        String result = rentalInfo.statement(new Customer("C. U. Stomer", Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1))));

        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result);
        }

        System.out.println("Success");
    }
}

public class Movie {
    private String title;
    private String code;
    private double rentalPrice;

    public Movie(String title, String code, double rentalPrice) {
        this.title = title;
        this.code = code;
        this.rentalPrice = rentalPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }
}

public class MovieRental {
    private String movieId;
    private int days;

    public MovieRental(String movieId, int days) {
        this.movieId = movieId;
        this.days = days;
    }

    public String getMovieId() {
        return movieId;
    }

    public int getDays() {
        return days;
    }
}

public class RentalInfo {

    private HashMap<String, Movie> movies;

    public RentalInfo() {
        movies = new HashMap<>();
        movies.put("F001", new Movie("You've Got Mail", "regular", 2));
        movies.put("F002", new Movie("Matrix", "regular", 2));
        movies.put("F003", new Movie("Cars", "childrens", 1.5));
        movies.put("F004", new Movie("Fast & Furious X", "new", 3));
    }

    public String statement(Customer customer) {
        double totalAmount = 0;
        int frequentEnterPoints = 0;
        String result = "Rental Record for " + customer.getName() + "\n";
        for (MovieRental r : customer.getRentals()) {
            Movie movie = movies.get(r.getMovieId());
            double thisAmount = movie.getRentalPrice();

            // determine amount for each movie
            if (movie.getCode().equals("regular")) {
                if (r.getDays() > 2) {
                    thisAmount += (r.getDays() - 2) * 1.5;
                }
            } else if (movie.getCode().equals("new")) {
                thisAmount = movie.getRentalPrice() * r.getDays();
            } else if (movie.getCode().equals("childrens")) {
                if (r.getDays() > 3) {
                    thisAmount += (r.getDays() - 3) * 1.5;
                }
            }

            // add frequent bonus points
            frequentEnterPoints++;
            // add bonus for a two-day new release rental
            if (movie.getCode().equals("new") && r.getDays() > 2) {
                frequentEnterPoints++;
            }

            // print figures for this rental
            result += "\t" + movie.getTitle() + "\t" + thisAmount + "\n";
            totalAmount += thisAmount;
        }
        // add footer lines
        result += "Amount owed is " + totalAmount + "\n";
        result += "You earned " + frequentEnterPoints + " frequent points\n";

        return result;
    }
}
