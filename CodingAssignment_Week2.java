import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Q. Write a Java Program for following requirements Take input from User for
 * ID, Name, Showdate, Showtime, Status Create movie object and populate above
 * details Store the object in an ArrayList Take input from user for ID and
 * display the details in the movie object of that ID. - User Interface should
 * be in class having main method - Storage should be in Data Access Object -
 * Ensure proper coding standards, naming conventions, OOP concepts
 */
public class CodingAssignment_Week2 {
	private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
	private static final MovieRepoImpl movielist = new MovieRepoImpl();

	public static void main(String[] args) {
		run();
	}

	private static void run() {
		boolean run = true;
		String cmd;
		menu();
		do {
			cmd = read("");
			if (cmd.equalsIgnoreCase("ADD")) {
				add();
			} else if (cmd.equalsIgnoreCase("DEL")) {
				del();
			} else if (cmd.equalsIgnoreCase("GET")) {
				get();
			} else if (cmd.equalsIgnoreCase("GAM")) {
				gam();
			} else if (cmd.equalsIgnoreCase("HELP")) {
				help();
			} else if (cmd.equalsIgnoreCase("EXIT")) {
				run = false;
			} else {
				System.out.println("Wrong Command");
			}
		} while (run);
	}

	public static Movie createMovie() {
		boolean create;
		long id = 0;
		try {
			id = Long.parseLong(read("Enter Movie ID: "));
			create = true;
		} catch (Exception e) {
			create = false;
			System.out.println("MovieID can only be a Integer");
		}
		String name = read("Enter Movie Name: ");
		String showDate = read("Enter Movie Show Date: ");
		String showTime = read("Enter Movie Show Time: ");
		boolean availability = read("Enter Availability(Available/Not Available): ").equalsIgnoreCase("Available");
		if (create) {
			return new Movie(id, name, showDate, showTime, availability);
		} else {
			return null;
		}
	}

	private static void gam() {
		List<Movie> l = movielist.getAllMovies();
		Iterator<Movie> mi = l.iterator();
		while (mi.hasNext()) {
			System.out.println(mi.next());
		}
	}

	private static void get() {
		boolean idOk;
		long id = 0;
		Movie m = null;
		try {
			id = Long.parseLong(read("Enter Movie ID: "));
			idOk = true;
		} catch (Exception e) {
			idOk = false;
		}
		if (idOk) {
			m = movielist.getMovieById(id);
			if (m != null) {
				System.out.println(m);
			} else {
				System.out.println("Cannot fetch any movies");
			}
		} else {
			System.out.println("MovieID can only be a Integer");
		}
	}

	private static void del() {
		Movie m = createMovie();
		if (m != null) {
			boolean del = movielist.deleteMovie(m);
			if (del) {
				System.out.println("Movie Deleted");
			} else {
				System.out.println("Movie could not be deleted");
			}
		} else {
			System.out.println("No Movies found to be Deleted");
		}
	}

	private static void add() {
		Movie m = createMovie();
		if (m != null) {
			boolean add = movielist.addMovie(m);
			if (add) {
				System.out.println("Movie Added: " + m);
			} else {
				System.out.println("Movie Updated: " + m);
			}
		} else {
			System.out.println("Movie could not be Added");
		}
	}

	private static void menu() {
		System.out.println("<<MENU>>");
		System.out.println("Enter any command from the list:");
		help();
	}

	private static void help() {
		System.out.println("HELP - To Get HELP");
		System.out.println("ADD - To Add a new Movie");
		System.out.println("DEL - To Delete a Movie");
		System.out.println("GET - To get Movie Details by MovieID");
		System.out.println("GAM - To get All Movies Details");
		System.out.println("EXIT - To exit the Program");
	}

	private static String read(String message) {
		System.out.print(message);
		try {
			return BR.readLine();
		} catch (IOException ioe) {
			return "";
		}
	}
}

class Movie {
	private long id;
	private String name, showDate, showTime;
	private boolean availability;

	public Movie(long id, String name, String showDate, String showTime, boolean availability) {
		super();
		this.id = id;
		this.name = name;
		this.showDate = showDate;
		this.showTime = showTime;
		this.availability = availability;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return id + "    " + name + "    " + showDate + "    " + showTime + "    "
				+ (availability ? "Available" : "Not Available");
	}

}

interface MovieRepo {

	boolean addMovie(Movie movie);

	boolean deleteMovie(Movie movie);

	Movie getMovieById(long movieId);

	List<Movie> getAllMovies();
}

class MovieRepoImpl implements MovieRepo {

	List<Movie> movies = new ArrayList<Movie>();

	@Override
	public boolean addMovie(Movie movie) {
		Movie obj = getMovieById(movie.getId());
		if (obj == null) {
			movies.add(movie);
			return true;
		} else {
			obj.setName(movie.getName());
			obj.setShowDate(movie.getShowDate());
			obj.setShowTime(movie.getShowTime());
			obj.setAvailability(movie.getAvailability());
			System.out.println("Movie Updated");
			return false;
		}
	}

	@Override
	public boolean deleteMovie(Movie movie) {
		Movie obj = getMovieById(movie.getId());
		if (obj != null) {
			movies.remove(obj);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Movie getMovieById(long movieId) {
		Movie out = null;
		Iterator<Movie> mi = movies.iterator();
		boolean found = false;
		while (!found && mi.hasNext()) {
			out = mi.next();
			found = out.getId() == movieId;
		}
		return found ? out : null;
	}

	@Override
	public List<Movie> getAllMovies() {
		return movies;
	}

	public List<Movie> getMovies() {
		return movies;
	}

}