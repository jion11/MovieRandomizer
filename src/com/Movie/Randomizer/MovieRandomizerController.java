package com.Movie.Randomizer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping
public class MovieRandomizerController {

    @GetMapping
    public String getRandomMovie() {

        return MovieRandomizer.randomMovieText();
    }

}
