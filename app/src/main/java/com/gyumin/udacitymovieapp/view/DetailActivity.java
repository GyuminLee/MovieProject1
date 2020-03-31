package com.gyumin.udacitymovieapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyumin.udacitymovieapp.R;
import com.gyumin.udacitymovieapp.data.Movie;
import com.gyumin.udacitymovieapp.util.Constants;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView titleTv;
    ImageView thumbIv;
    TextView releaseDateTv;
    TextView rateTv;
    TextView overviewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //find view
        setView();

        //get movie data
        Movie movie = (Movie) getIntent().getSerializableExtra(Constants.MOVIE_DATA);

        if (movie != null) {
            //set data to the view
            titleTv.setText(movie.getTitle());
            Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(thumbIv);
            releaseDateTv.setText(movie.getReleaseDate());
            String rating = movie.getVoteAverage() + "/10";
            rateTv.setText(rating);
            overviewTv.setText(movie.getOverview());

        }



    }

    private void setView() {
        titleTv = findViewById(R.id.tv_title_detail);
        thumbIv = findViewById(R.id.iv_thumb_detail);
        releaseDateTv = findViewById(R.id.tv_release_date_detail);
        rateTv = findViewById(R.id.tv_rate_detail);
        overviewTv = findViewById(R.id.tv_overview);
    }
}
