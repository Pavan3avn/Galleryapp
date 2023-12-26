package com.pavan.imagegallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pavan.imagegallery.R
import com.pavan.imagegallery.model.Imageitem

class galleryadapter():
    PagingDataAdapter<Imageitem,galleryadapter.viewholder>(PhotoDiffCallback()) {
   inner class viewholder(v: View) : RecyclerView.ViewHolder(v) {
       val imageview : ImageView = v.findViewById(R.id.imageView)
       val textview : TextView = v.findViewById(R.id.name)
       fun bind(photo: Imageitem) {
           Glide.with(itemView.context)
               .load(photo.imageUrl)
               .apply(RequestOptions.placeholderOf(R.drawable.man))
               .into(imageview)

              textview.text = photo.title

       }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val photo = getItem(position)
        photo?.let { holder.bind(it) }
    
    }


    }




    private class PhotoDiffCallback : DiffUtil.ItemCallback<Imageitem>() {
        override fun areItemsTheSame(oldItem: Imageitem, newItem: Imageitem): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Imageitem, newItem: Imageitem): Boolean {
            return oldItem == newItem
        }

}