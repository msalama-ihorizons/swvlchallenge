package com.tiendito.swvlmovies.ui.movieslist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tiendito.swvlmovies.model.Status
import com.tiendito.swvlmovies.R
import com.tiendito.swvlmovies.db.Movie
import com.tiendito.swvlmovies.ui.moviedetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_list_activity.*


@AndroidEntryPoint
class MoviesListActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: YearsMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_list_activity)

        moviesAdapter =
            YearsMoviesAdapter(
                this,
                object : MoviesAdapter.MoviesItemClickListener {
                    override fun onItemClick(item: Movie?) {
                        startActivity(
                            MovieDetailsActivity.newIntent(
                                this@MoviesListActivity,
                                item?.title
                            )
                        )
                    }

                    override fun onItemClick(position: Int) {
                    }


                })

        moviesRV.adapter = moviesAdapter

        searchEdt.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                moviesViewModel.searchMovies(searchEdt.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        moviesViewModel.searchKeyword.observe(this, Observer {
            searchEdt.setText(it)
        })
        moviesViewModel.moviesList.observe(this, Observer { resources ->
            when (resources.status) {
                    Status.LOADING -> {
                        progressBar.visibility =  View.VISIBLE
                    }
                Status.SUCCESS -> {
                    Log.d("MainActivityyyy", resources.data?.size.toString())
                   // moviesAdapter.items = resources?.data
                    println(resources.data)
                    moviesAdapter.items =  resources.data
                   // val byLength = resources?.data?.groupBy { it.year }

                   /* val phonesByYearMutable = LinkedHashMap<Int, MutableList<Movie>>();

                    resources?.data?.groupByTo(phonesByYearMutable, { it.year }, { it.first })
                    println(phonesByYearMutable)*/

                }
                Status.ERROR -> {}
                Status.COMPLETE -> {
                    progressBar.visibility =  View.GONE
                }
            }

        })

    }
}