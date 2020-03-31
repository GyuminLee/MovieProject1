package com.gyumin.udacitymovieapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gyumin.udacitymovieapp.util.Constants;
import com.gyumin.udacitymovieapp.data.Movie;
import com.gyumin.udacitymovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private ArrayList<Movie> movies;
    private Context context;
    private MovieClickListener movieClickListener;



    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void clearData() {
        int size = getItemCount();
        movies.clear();
        notifyItemRangeRemoved(0, size);

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.thumbIv);

        holder.titleTv.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbIv;
        TextView titleTv;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbIv = itemView.findViewById(R.id.iv_thumb);
            titleTv = itemView.findViewById(R.id.tv_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (movieClickListener != null) {
                movieClickListener.onMovieClick(v, getAdapterPosition());
            }
        }
    }

    public void setMovieClickListener(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    public interface MovieClickListener {
        void onMovieClick(View view, int position);
    }
}