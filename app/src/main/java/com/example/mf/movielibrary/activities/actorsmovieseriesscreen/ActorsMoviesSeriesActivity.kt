package com.example.mf.movielibrary.activities.actorsmovieseriesscreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.ActorsMoviesSeriesAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.fragments.ActorsMovieTvShowsFragment
import com.example.mf.movielibrary.interfaces.ActorsMoviesSeriesListener
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.example.mf.movielibrary.files.MOVIE_CREDITS
import com.example.mf.movielibrary.files.NAME
import kotlinx.android.synthetic.main.activity_actors_movies_series.*

class ActorsMoviesSeriesActivity : BaseActivity<ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesView,
        ActorsMoviesSeriesActivityPresenter>(), ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesView, ActorsMoviesSeriesListener {

    override fun onCallActorsCreditsApi(actorId: Int, moviesOrSeriesCredits: String) {
        mPresenter.callActorsCreditsApi(actorId, moviesOrSeriesCredits)
    }

    override var mPresenter = ActorsMoviesSeriesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_movies_series)
        initToolbar(toolbar as Toolbar, true, intent.getStringExtra(NAME) + "'s")
        mPresenter.requestViewPager()
    }

    override fun setViewPager() {
        val adapter = ActorsMoviesSeriesAdapter(supportFragmentManager)
        adapter.addFragment("Movies")
        adapter.addFragment("Tv shows")
        movieSeriesViewpager.adapter = adapter
        tabLayout.setupWithViewPager(movieSeriesViewpager)
    }

    override fun setMovieOrSeriesRecyclerView(moviesList: List<Movie?>?, totalResults: Int, moviesOrSeriesCredits: String) {
        if (moviesOrSeriesCredits == MOVIE_CREDITS)
            getMoviesFragment().setMovieOrSeriesRecyclerView(moviesList) else
            getSeriesFragment().setMovieOrSeriesRecyclerView(moviesList)
    }

    override fun hideProgressBar(moviesOrSeriesCredits: String) {
        if (moviesOrSeriesCredits == MOVIE_CREDITS)
            getMoviesFragment().hideProgressBar() else
            getSeriesFragment().hideProgressBar()
    }

    override fun showProgressBar(moviesOrSeriesCredits: String) {
        if (moviesOrSeriesCredits == MOVIE_CREDITS)
            getMoviesFragment().showProgressBar() else
            getSeriesFragment().showProgressBar()
    }

    fun getMoviesFragment() = movieSeriesViewpager.adapter.instantiateItem(movieSeriesViewpager, 0) as ActorsMovieTvShowsFragment
    fun getSeriesFragment() = movieSeriesViewpager.adapter.instantiateItem(movieSeriesViewpager, 1) as ActorsMovieTvShowsFragment
}
