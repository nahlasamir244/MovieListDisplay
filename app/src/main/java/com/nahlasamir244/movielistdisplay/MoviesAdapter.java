package com.nahlasamir244.movielistdisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nahlasamir244.movielistdisplay.models.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    //define the data source
    private ArrayList<Movie> movies;
    //define paramterize constructor to take the data collection and provide the adapter with data
    public MoviesAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.row_single_movie, parent, false);
        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(movieView) {
        };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieName.setText(movie.getName());
        holder.movieDescription.setText(movie.getDescription());
        holder.itemView.setOnClickListener(v ->
                Toast.makeText(holder.itemView.getContext(), movie.getName()+" clicked", Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        //define here the view components of each row  to be accessible to the holder
        TextView movieName, movieDescription;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
            movieDescription=itemView.findViewById(R.id.movieDescription);
        }
    }
    //swipe right , left
}
