package com.tiendito.swvlmovies.ui.movieslist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.swvlmovies.R
import com.tiendito.swvlmovies.db.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter (context: Context?, listener: MoviesItemClickListener?) :
    GenericRecyclerViewAdapter<
            Movie,
            MoviesAdapter.MoviesItemClickListener,
            MoviesAdapter.MovieItemViewHolder>(context, listener) {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(inflate(R.layout.movie_item, parent), listener)
    }

    inner class MovieItemViewHolder(
        itemView: View,
        listener: MoviesItemClickListener?
    ) : BaseViewHolder<Movie, MoviesItemClickListener>(itemView, listener),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Movie?) {
            itemView.movieTitle.text = item?.title
            itemView.movieYear.text = item?.year.toString()
            itemView.movieRate.text = item?.rating.toString()
        }

        override fun onClick(v: View?) {
            listener.onItemClick(getItem(bindingAdapterPosition))
        }

    }

    interface MoviesItemClickListener : OnRecyclerItemClickListener {
        fun onItemClick(item: Movie?)
    }

}