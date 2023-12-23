package com.pavan.imagegallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pavan.imagegallery.model.Imageitem

class galleryadapter( private val imagelist : List<Imageitem>):
    RecyclerView.Adapter<galleryadapter.viewholder>() {
   inner class viewholder(v: View) : RecyclerView.ViewHolder(v) {
       val imageview : ImageView = v.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): galleryadapter.viewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: galleryadapter.viewholder, position: Int) {
        val imageItem = imagelist[position]
        Glide.with(holder.itemView)
            .load(imageItem.imageUrl)
            .into(holder.imageview)
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }

}