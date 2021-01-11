package com.nahlasamir244.movielistdisplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nahlasamir244.movielistdisplay.databinding.ActivityMainBinding;
import com.nahlasamir244.movielistdisplay.models.Movie;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel = new MainViewModel();
    MoviesAdapter moviesAdapter;
    Movie swipedMovie;
    LinearLayout noData ;
    RecyclerView moviesRV;
    int swipePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        activityMainBinding.setMainAC(this);
        DataBindingUtil.setContentView(this,R.layout.activity_main);
        noData = findViewById(R.id.noDataLayout);
        moviesRV = findViewById(R.id.moviesRecyclerView);
        moviesAdapter = new MoviesAdapter(mainViewModel.movies);
        moviesRV.setAdapter(moviesAdapter);
        moviesRV.setLayoutManager(new LinearLayoutManager(this));
        mainViewModel.movies.addAll(Movie.getMovieList(10));
        moviesAdapter.notifyDataSetChanged();
        moviesRV.scrollToPosition(mainViewModel.movies.size()-1);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        moviesRV.addItemDecoration(itemDecoration);
        MovieTouchHelper movieTouchHelper = new MovieTouchHelper(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(movieTouchHelper);
        itemTouchhelper.attachToRecyclerView(moviesRV);

    }
    class MovieTouchHelper extends ItemTouchHelper.SimpleCallback {

        public MovieTouchHelper(int dragDirections, int swipeDirections) {
            super(dragDirections, swipeDirections);
        }

        @Override
        public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float directionX, float directionY,
                                int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(canvas,
                    recyclerView, viewHolder, directionX, directionY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white))
                    .addSwipeLeftActionIcon(R.drawable.ic_launcher_background)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white))
                    .addSwipeRightActionIcon(R.drawable.ic_launcher_background)
                    .create()
                    .decorate();
            super.onChildDraw(canvas, recyclerView, viewHolder, directionX, directionY, actionState,
                    isCurrentlyActive);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            swipePosition = viewHolder.getAdapterPosition();
            swipedMovie = mainViewModel.movies.get(viewHolder.getAdapterPosition());
            switch (direction) {
                case ItemTouchHelper.LEFT: showMovie(viewHolder.getAdapterPosition());
                    break;
                case ItemTouchHelper.RIGHT: deleteMovie(viewHolder.getAdapterPosition());
                    break;
            }
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
    }

    private void showMovie(int position) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }
    private void deleteMovie(int position) {
        mainViewModel.movies.remove(swipedMovie);
        //TODO :: update animation in change of dataset
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_down_to_up);
        moviesRV.setLayoutAnimation(controller);
        moviesAdapter.notifyDataSetChanged();
        moviesRV.scheduleLayoutAnimation();
        Snackbar.make(moviesRV, R.string.confirmation, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("undo", "onSwiped: ");
                        noData.setVisibility(View.GONE);
                        mainViewModel.movies.add(swipePosition,swipedMovie);
                        moviesAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

}