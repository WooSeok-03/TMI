package com.android.mtinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mtinfo.databinding.FragmentMovieBinding
import com.android.mtinfo.presentation.adapter.MovieAdapter
import com.android.mtinfo.presentation.viewmodel.MovieViewModel

class MovieFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMovieBinding = FragmentMovieBinding.bind(view)
        movieViewModel = (activity as MainActivity).movieViewModel
        movieAdapter = (activity as MainActivity).movieAdapter
        movieAdapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_movieFragment_to_informationFragment,
                Bundle().apply { putSerializable("selected_movie", it) }
            )
        }

        initRecyclerView()
        viewMovieList()
    }

    private fun initRecyclerView() {
        fragmentMovieBinding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun viewMovieList() {
        val responseLiveData = movieViewModel.getMovies()
        responseLiveData.observe(viewLifecycleOwner) {
            movieAdapter.differ.submitList(it?.toList())
        }
    }
}