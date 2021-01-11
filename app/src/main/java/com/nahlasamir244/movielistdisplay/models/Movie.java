package com.nahlasamir244.movielistdisplay.models;

import java.util.ArrayList;

public class Movie {
    private String name;
    private String description;

    public Movie(String name, String description ) {
        this.name = name;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public static ArrayList<Movie> getMovieList(int numOfMovies) {
        ArrayList<Movie> movies = new ArrayList();
        for (int i = 1; i <= numOfMovies; i++) {
            movies.add(new Movie("Toy Story "+i,"Example of Movie Description"));
        }

        return movies;
    }
}
