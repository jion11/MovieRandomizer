package com.Movie.Randomizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping
public class MovieRandomizerController {

    private final MovieRandomizerService movieRandomizerService;

    @Autowired
    public MovieRandomizerController(MovieRandomizerService movieRandomizerService) {
        this.movieRandomizerService = movieRandomizerService;
    }

    @GetMapping(path = "/movie/random")

    public String getRandomMovie() throws IOException {

        return movieRandomizerService.getRandomMovie() + "<br>"
                + movieRandomizerService.getDirector() + "<br>"
                + movieRandomizerService.getYear() + "<br>"
                + movieRandomizerService.getDescription() + "<br>"
                + movieRandomizerService.getUrl();

    }

    @GetMapping("/movie")

    public ArrayList<String> getMovieList() {

        return movieRandomizerService.getMovies();
    }

    @GetMapping("/movie/watched")

    public ArrayList<String> getMovieWatchedList() {
        return movieRandomizerService.getMoviesWatched();
    }

    @GetMapping("/")

    public String helloWorld() {

        return "Want to watch a movie innit?";
    }




}