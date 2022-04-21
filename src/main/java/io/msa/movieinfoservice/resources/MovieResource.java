package io.msa.movieinfoservice.resources;

import io.msa.movieinfoservice.models.Movie;
import io.msa.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;
    private static final String MOVIE_DB_API_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY_PARAM_HEADER = "?api_key=";
    private final RestTemplate restTemplate;

    @Autowired
    public MovieResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        String url = MOVIE_DB_API_URL + movieId + API_KEY_PARAM_HEADER + apiKey;
        MovieSummary movieSummary = restTemplate.getForObject(url, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

}
