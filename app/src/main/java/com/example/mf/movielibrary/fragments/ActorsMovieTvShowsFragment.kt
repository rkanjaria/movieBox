package com.example.mf.movielibrary.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.interfaces.ActorsMoviesSeriesListener
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.example.mf.movielibrary.files.*
import kotlinx.android.synthetic.main.fragment_actors_movie_series.*

class ActorsMovieTvShowsFragment : Fragment(), MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    private var mListener: ActorsMoviesSeriesListener? = null
    private var actorId = -1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_actors_movie_series, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actorId = activity.intent.getIntExtra(INT_ID, -1)

        if (arguments.getInt(POSITION, -1) == 0)
            mListener?.onCallActorsCreditsApi(actorId, MOVIE_CREDITS)
        else
            mListener?.onCallActorsCreditsApi(actorId, TV_CREDITS)
    }

    fun setMovieOrSeriesRecyclerView(moviesList: List<Movie?>?) {
        if (moviesList != null && moviesList.isNotEmpty()) {
            movieSeriesRecyclerView.visibility = View.VISIBLE
            movieSeriesRecyclerView.setHasFixedSize(true)
            movieSeriesRecyclerView.layoutManager = GridLayoutManager(context, 3)
            movieSeriesRecyclerView?.adapter = MovieRecyclerAdapter(moviesList, this)
            runLayoutAnimation(movieSeriesRecyclerView, R.anim.grid_layout_animation_fall_down)
        } else {
            movieSeriesRecyclerView.visibility = View.GONE
            noMovieSeriesImage.loadDrawable(R.drawable.no_movie_series)
            val moviesOrTvshows = if (arguments.getInt(POSITION, -1) == 0) "movies" else "tv shows"
            val name = activity.intent.getStringExtra(NAME)
            noMovieSeriesSubtitle.text = "No $moviesOrTvshows found for $name"
            noMovieSeriesLayout.visibility = View.VISIBLE
        }
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ActorsMoviesSeriesListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + "must implement ActorsMoviesSeriesListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        val movieSeriesIntent = Intent(context, MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        val mediaType = if (arguments.getInt(POSITION, -1) == 0) MOVIE else TV_SHOWS
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, mediaType)
        context.startActivity(movieSeriesIntent)
    }
}
