package com.pavan.imagegallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pavan.imagegallery.Viewmodel.photoviewmodel
import com.pavan.imagegallery.adapter.galleryadapter


class searchfragment : Fragment() {

    private lateinit var viewModel: photoviewmodel
    private lateinit var adapter: galleryadapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_searchfragment, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(photoviewmodel::class.java)
        recyclerView = view.findViewById(R.id.recyclerview)
        adapter = galleryadapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.imagelist.observe(viewLifecycleOwner){imagelist ->
            adapter.submitData(lifecycle,imagelist)
        }

        val query = arguments?.getString("query")
        query?.let { viewModel.searchImages(it) }


        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            searchfragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}