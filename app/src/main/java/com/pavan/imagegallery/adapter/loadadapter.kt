package com.pavan.imagegallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pavan.imagegallery.R

class loadadapter: LoadStateAdapter<loadadapter.viewholder>() {

    class viewholder(v : View): RecyclerView.ViewHolder(v){
        val bar = v.findViewById<ProgressBar>(R.id.progressbar)

        fun bind(loadState: LoadState){
            bar.isVisible =  loadState is LoadState.Loading
        }


    }

    override fun onBindViewHolder(holder: viewholder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loader_item,parent,false)
        return viewholder(view)
    }
}