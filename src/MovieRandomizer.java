import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.time.LocalDate;


public class MovieRandomizer {
    //initializing my Random object and getting a reference to my txt
    public static File movies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesToWatch.txt");
    public static File watchListMovies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt");
    public static Random rand = new Random();
    public static Scanner listReader;
    public static Scanner listReaderWatched;


    public static void fillList(Scanner reader, ArrayList<String> mov)
    {
        //Adding strings to the Array List
        while (reader.hasNextLine()) {  //looks though each line in the movie txt until null
            String movieName = reader.nextLine();
            mov.add(movieName);
        }
        reader.close();
    }

    public static void writeTo(ArrayList<String> stringList, String r, FileWriter wri) //Will write string r to file with filewriter.
    {
        try {
            if (!stringList.contains(r)) // Won't write to file if String r is already there
            {
                wri.write(r);
                wri.write(System.lineSeparator());
                wri.close();

            } else {
                System.out.println("You've watched this already!");
            }
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }


    public static void main(String[] args) throws java.io.IOException
    {
        listReader = new Scanner(movies); //Will parse through my movie txt for their primitives
        ArrayList<String> movieList = new ArrayList<>(); //What will hold the string from movie txt
        listReaderWatched = new Scanner(watchListMovies);
        ArrayList<String> watchedMovieList = new ArrayList<>();

        fillList(listReader, movieList);
        fillList(listReaderWatched, watchedMovieList);

        String randomMovie = movieList.get(rand.nextInt(movieList.size()));
        FileWriter movieWatched = new FileWriter("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt",true);

        System.out.println(movieList);
        System.out.println(randomMovie + " on: " + LocalDate.now());
        writeTo(watchedMovieList, randomMovie, movieWatched);
        System.out.println(watchedMovieList);

    }


}