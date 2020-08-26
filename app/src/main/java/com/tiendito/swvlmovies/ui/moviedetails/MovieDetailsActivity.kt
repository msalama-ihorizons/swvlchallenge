package com.tiendito.swvlmovies.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tiendito.swvlmovies.model.Status
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.swvlmovies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_activity.*

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val moviePhotosViewModel: MoviePhotosViewModel by viewModels()
    private lateinit var moviePhotosAdapter: MoviePhotosAdapter

    companion object {
        const val EXTRA_MOVIE_TITLE = "extraMovieTitle"
        private const val NUMBER_OF_COL = 2

        fun newIntent(context: Context?, movieTitle: String?): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_TITLE, movieTitle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)

        moviePhotosAdapter = MoviePhotosAdapter(this, OnRecyclerItemClickListener {})

        moviePhotosRV.layoutManager = GridLayoutManager(this, NUMBER_OF_COL)
        moviePhotosRV.adapter = moviePhotosAdapter

        moviePhotosViewModel.moviePhotosLiveData?.observe(this, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> {
                    moviePhotosAdapter.items = resources.data
                }
                Status.ERROR -> {
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.COMPLETE -> {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }
}