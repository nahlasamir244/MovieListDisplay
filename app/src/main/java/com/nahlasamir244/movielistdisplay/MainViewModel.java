package com.nahlasamir244.movielistdisplay;

import androidx.lifecycle.ViewModel;

import com.nahlasamir244.movielistdisplay.models.Movie;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    ArrayList<Movie> movies = Movie.getMovieList(30);
}
