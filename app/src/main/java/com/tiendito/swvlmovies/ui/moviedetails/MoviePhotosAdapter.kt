package com.tiendito.swvlmovies.ui.moviedetails

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.swvlmovies.R
import com.tiendito.swvlmovies.api.Photo
import kotlinx.android.synthetic.main.movie_photo_item.view.*

class MoviePhotosAdapter (context: Context?, listener: OnRecyclerItemClickListener?) :
    GenericRecyclerViewAdapter<
            Photo,
            OnRecyclerItemClickListener,
            MoviePhotosAdapter.ItemViewHolder>(context, listener) {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(inflate(R.layout.movie_photo_item, parent), listener)
    }

    inner class ItemViewHolder(
        itemView: View,
        listener: OnRecyclerItemClickListener?
    ) : BaseViewHolder<Photo, OnRecyclerItemClickListener>(itemView, listener) {


        override fun onBind(item: Photo?) {
            var urlTxt : String? = ""
            item?.let {photo->
                urlTxt = context?.getString(R.string.flicker_photo_url,
                    photo.farm, photo.server, photo.id, photo.secret
                )
            }

            context?.let {
                Glide.with(it).load(urlTxt)
                    .into(itemView.moviePhoto)
            }

        }
    }

}