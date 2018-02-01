package com.example.mf.movielibrary.activities.movieseriesscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.CastRecyclerAdapter
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.castmodel.Cast
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_movie_series.*

class MovieSeriesActivity : BaseActivity<MovieSeriesActivityContract.MovieSeriesView, MovieSeriesActivityPresenter>(),
        MovieSeriesActivityContract.MovieSeriesView, CastRecyclerAdapter.OnCastAdapterListener, MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    private var movieOrSeriesId = 0

    override var mPresenter = MovieSeriesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_series)

        val movieModel = intent.getParcelableExtra(PARCELABLE_OBJECT) as Movie
        initToolbar(toolbar as Toolbar, true, "")

        movieOrSeriesId = movieModel.id

        if (movieModel.backDroppath != null) {
            backdropImage.loadImage(backdropUrl + movieModel.backDroppath, placeholder = R.color.darkGrey)
        }

        if (movieModel.posterPath != null) {
            posterImage.loadImage(photoUrl + movieModel.posterPath, placeholder = R.color.darkGrey)
        }
        movieTitle.text = movieModel.title
        movieYear.text = getDateWithCustomFormat(movieModel.releaseDate)
        movieRating.text = movieModel.voteAverage.toString()
        movieOverview.text = movieModel.overview
        movieGenre.text = mPresenter.getMovieGenres(movieModel.genreIds)

        mPresenter.callgetMovieOrSeriesCastApi(intent.getStringExtra(MOVIE_OR_SERIES), movieOrSeriesId)
        mPresenter.callGetSimilarMovieOrSeriesApi(intent.getStringExtra(MOVIE_OR_SERIES), movieOrSeriesId)
    }

    override fun setCastRecyclerview(castList: List<Cast>) {

        castRecyclerview.setHasFixedSize(true)
        castRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castRecyclerview.adapter = CastRecyclerAdapter(castList, this)
        castRecyclerview.visibility = View.VISIBLE
        castTitle.visibility = View.VISIBLE
    }

    override fun onCastClicked(castModel: Cast) {
        mPresenter.launchActorActivity(castModel, intent.getStringExtra(MOVIE_OR_SERIES))
    }

    override fun setSimilarMoviesRecyclerview(moviesList: List<Movie?>, totalPages: Int) {

        similarMoviesRecyclerview.setHasFixedSize(true)
        similarMoviesRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarMoviesRecyclerview.adapter = MovieRecyclerAdapter(moviesList, this, true)
        similarMoviesRecyclerview.visibility = View.VISIBLE
        recommendationsTitle.visibility = View.VISIBLE
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel, intent.getStringExtra(MOVIE_OR_SERIES))
    }
}
