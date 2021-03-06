package com.Movie.Randomizer;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.time.LocalDate;

@SpringBootApplication

public class MovieRandomizer {

    //initializing my Random object and getting a reference to my txt
    private static File movies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesToWatch.txt");
    private static File watchListMovies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt");
    private static Random rand = new Random();
    private static BufferedReader listReaderWatched;
    private static BufferedReader listReader;
    private static String randomMovie;
    private static final String url = "jdbc:postgresql://localhost/movie";


    public static void fillList(BufferedReader reader, ArrayList<String> mov)
    {
        //Adding strings to the Array List
        try {
            String movieName = reader.readLine();
            while (movieName != null) {  //looks though each line in the movie txt until null
                movieName = reader.readLine();
                if (movieName == null)
                {
                    break;
                } else
                {
                    if (movieName.length() > 0 && !mov.contains(movieName)) //Won't add null or a movie if it's already in the list
                    {
                        mov.add(movieName);
                    }
                }

            }
            reader.close();
        } catch (IOException ioe) {
            System.out.println("Could not read file");
        }
    }

    public static ArrayList<String> GetMovies() {

        String SQL = "SELECT moviename FROM movielist where NOT (datewatched IS NOT NULL)";
        ArrayList<String> list = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while (rs.next()) {
                list.add(rs.getString("moviename"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public static void InsertDate(){
        String SQL = "SELECT datewatched FROM movielist where moviename = " + "'" + randomMovie + "'";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()){

                if (rs.wasNull()) {
                    String query = "UPDATE movielist SET datewatched = ? WHERE moviename = " + "'" + randomMovie + "'";
                    System.out.println(query);
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setDate(1, Date.valueOf(LocalDate.now()));
                    preparedStmt.executeUpdate();
                    System.out.println("Added date watched");
            } else {
                    System.out.println("You WATCHED THIS" + rs.getDate("datewatched"));
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<String> GetWatchedMovies() {

        String SQL = "SELECT datewatched FROM movielist";

        ArrayList<Date> listDate = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        HashSet<Date> h = new HashSet<>();

        try{ ResultSet rs = executeSql(SQL);
            while (rs.next()) {
                listDate.add(rs.getDate("datewatched"));
            }
            for (Date date : listDate){
                if (date !=null && h.add(date)) {
                    String query = "SELECT moviename FROM movielist where datewatched = '" + date + "'";
                    ResultSet rsname = executeSql(query);
                    while (rsname.next()){
                        list.add(rsname.getString("moviename"));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(list);
        return list;

    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static ResultSet executeSql(String SQL){
        ResultSet rs = null;
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public static String GetDescription(String movie) throws IOException {
        String movieHyphen = TransformString(movie);
        String description = "";
        Document doc = Jsoup.connect("https://letterboxd.com/film/" + movieHyphen).get();
        Elements els = doc.select("div.truncate > p");
        for(Element el : els) {
            //System.out.println(el.text());
            description = el.text();
        }
        return description;
    }

    public static String GetDirector(String movie) throws IOException {
        String movieHyphen = TransformString(movie);
        String director = "";
        Document doc = Jsoup.connect("https://letterboxd.com/film/" + movieHyphen).get();
        Elements els = doc.select("#featured-film-header > p > a > span");
        for(Element el : els) {
            //System.out.println(el.text());
            director = el.text();
        }
        return director;
    }

    public static String TransformString (String str){
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < str.length(); ++i){
            if (str.charAt(i) == ' '){
                s.append('-');
            } else {
                s.append(str.charAt(i));
            }
        }
        String newName = String.valueOf(s);
        newName = newName.replaceAll("[^a-zA-Z0-9--]","");
        return newName.toLowerCase();
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



    public static void main(String[] args)
    {
        ArrayList<String> movieList = GetMovies();
        if (movieList.size() > 0)
        {
            randomMovie = movieList.get(rand.nextInt(movieList.size()));
            System.out.println(movieList);
            System.out.println(randomMovie + " on: " + LocalDate.now());
            //InsertDate();
        } else {
            System.out.println("Add movies to the database first you dingus");
        }

        SpringApplication.run(MovieRandomizer.class, args);

    }

    public static String randomMovieText(){
        return randomMovie;
    }

    public static int yearOfMovie(){
        String SQL = "SELECT yearreleased FROM movielist where moviename = " + "'" + randomMovie + "'";
        int year = 0;
        try {
            ResultSet rs = executeSql(SQL);
            while (rs.next()){
                year = rs.getInt("yearreleased");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return year;
    }
    public static String urlMovie(){
        String SQL = "SELECT url FROM movielist where moviename = " + "'" + randomMovie + "'";
        String url = "";
        try {
            ResultSet rs = executeSql(SQL);
            while (rs.next()){
                url = rs.getString("url");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return url;
    }




}