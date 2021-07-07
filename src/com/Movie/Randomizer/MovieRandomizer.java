package com.Movie.Randomizer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import javax.mail.*;
import javax.mail.internet.*;


@SpringBootApplication
public class MovieRandomizer {

    //initializing my Random object and getting a reference to my txt
    private static File movies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesToWatch.txt");
    private static File watchListMovies = new File("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt");
    private static Random rand = new Random();
    private static BufferedReader listReaderWatched;
    private static BufferedReader listReader;
    private static String randomMovie;


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

    public static void sendEmail(String ran)
    {
        String host = "smtp.gmail.com";
        final String user = "cameron.dandyO33@gmail.com";
        final String password = "********";

        String to ="cameron.ohree@gmail.com";

        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Here is your random movie!");
            message.setText("The movie you will be watching is: \n" + ran + "\nThis was picked at " + LocalDate.now() );

            Transport.send(message);
            System.out.println("message sent!");
        }
        catch (MessagingException mex)
        {
            System.out.println("Error: unable to send message....");
            mex.printStackTrace();
        }
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
        listReader = new BufferedReader(new FileReader(movies)); //Will parse through my movie txt for their primitives
        ArrayList<String> movieList = new ArrayList<>(); //What will hold the string from movie txt
        listReaderWatched = new BufferedReader(new FileReader(watchListMovies));
        ArrayList<String> watchedMovieList = new ArrayList<>();

        fillList(listReader, movieList);
        fillList(listReaderWatched, watchedMovieList);
        FileWriter movieWatched = new FileWriter("D:\\CodingProjects\\Java\\Java Projects\\Movie Randomizer\\MovieRandomizer\\MoviesWatched.txt",true);

        if (movieList.size() > 0)
        {
            randomMovie = movieList.get(rand.nextInt(movieList.size()));
            System.out.println(movieList);
            System.out.println(randomMovie + " on: " + LocalDate.now());
            writeTo(watchedMovieList, randomMovie, movieWatched);
            System.out.println(watchedMovieList);
        } else {
            System.out.println("Add movies to MoviesToWatch.txt first you dingus");
        }

            SpringApplication.run(MovieRandomizer.class, args);


    }

    public static String randomMovieText(){
        return "Your movie is: " + randomMovie;
    }




}