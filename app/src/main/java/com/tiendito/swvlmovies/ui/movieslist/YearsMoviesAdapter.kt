package com.tiendito.swvlmovies.ui.movieslist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.swvlmovies.R
import com.tiendito.swvlmovies.db.Movie
import com.tiendito.swvlmovies.model.YearMovies
import kotlinx.android.synthetic.main.year_movies_item.view.*

class YearsMoviesAdapter (context: Context?, listener: MoviesAdapter.MoviesItemClickListener?) :
    GenericRecyclerViewAdapter<
            YearMovies,
            MoviesAdapter.MoviesItemClickListener,
            YearsMoviesAdapter.MovieItemViewHolder>(context, listener) {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(inflate(R.layout.year_movies_item, parent), listener)
    }

    inner class MovieItemViewHolder(
        itemView: View,
        listener: MoviesAdapter.MoviesItemClickListener?

    ) : BaseViewHolder<YearMovies, MoviesAdapter.MoviesItemClickListener>(itemView, listener){
        override fun onBind(item: YearMovies?) {
            itemView.yearTxt.text = item?.year.toString()
           val moviesAdapter = MoviesAdapter(context, listener)

            itemView.moviesRV.setHasFixedSize(true)
            itemView.moviesRV.adapter = moviesAdapter

            moviesAdapter.items= item?.movies

            //itemView.movieTitle.text = item?.title + " "+ item?.year +" "+ item?.rating
        }

    }
}