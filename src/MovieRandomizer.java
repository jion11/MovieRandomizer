import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.util.Scanner;

public class MovieRandomizer {


    public static void main(String[] args) throws FileNotFoundException, java.io.IOException
    {
        Random rand = new Random();
        File movies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesToWatch.txt");
        Scanner listReader = new Scanner(movies);
        ArrayList<String> movieList = new ArrayList<String>();

        //Adding strings to the Array List
        while (listReader.hasNextLine()) {
            String movieName = listReader.nextLine();
            movieList.add(movieName);
            //System.out.println(movieName);
        }
        listReader.close();

        File watchListMovies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt");
        Scanner listReaderWatched = new Scanner(watchListMovies);
        ArrayList<String> watchedMovieList = new ArrayList<String>();

        while (listReaderWatched.hasNextLine()) {
            String movieName = listReaderWatched.nextLine();
            watchedMovieList.add(movieName);
        }
        listReaderWatched.close();


        String randomMovie = movieList.get(rand.nextInt(movieList.size()));
        FileWriter movieWatched = new FileWriter("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt",true);

        if (!watchedMovieList.contains(randomMovie))
        {
            movieWatched.write(randomMovie);
            movieWatched.write(System.lineSeparator());
            movieWatched.close();
        }

        //FileWriter movieWatched = new FileWriter("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt", true);
        //movieWatched.write(randomMovie);
        //movieWatched.write(System.lineSeparator());
        //movieWatched.close();


        System.out.println(movieList);
        System.out.println(randomMovie);
        System.out.println(watchedMovieList);

    }
}