import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Scanner;

public class MovieRandomizer {

    public static void main(String[] args) throws FileNotFoundException
    {

        File movieList = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesToWatch.txt");
        Scanner myReader = new Scanner(movieList);
        while (myReader.hasNextLine()) {
            String movieName = myReader.nextLine();
            System.out.println(movieName);
            System.out.println("Hi");
        }
        myReader.close();


    }
}