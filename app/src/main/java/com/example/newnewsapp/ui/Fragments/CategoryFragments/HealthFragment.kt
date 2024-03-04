package com.example.newnewsapp.ui.Fragments.CategoryFragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newnewsapp.R
import com.example.newnewsapp.adapters.NewsAdapters
import com.example.newnewsapp.databinding.FragmentHeadlinesBinding
import com.example.newnewsapp.databinding.FragmentHealthBinding
import com.example.newnewsapp.databinding.FragmentScienceBinding
import com.example.newnewsapp.databinding.FragmentTechnologyBinding
import com.example.newnewsapp.ui.MainActivity
import com.example.newnewsapp.ui.NewsViewModel
import com.example.newnewsapp.util.Constants
import com.example.newnewsapp.util.Resource

class HealthFragment : Fragment(R.layout.fragment_health) {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapters
    lateinit var retryButton: Button
    lateinit var errorText: TextView
    lateinit var itemHealthError: CardView
    lateinit var binding: FragmentHealthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHealthBinding.bind(view)

        itemHealthError = view.findViewById(R.id.itemHealthError)

        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view:View = inflater.inflate(R.layout.nav_error,null)

        retryButton = view.findViewById(R.id.retryButton)
        errorText = view.findViewById(R.id.errorText)

        newsViewModel = (activity as MainActivity).newsViewModel
        setUpHealthRecycler()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_healthFragment_to_articleFragment, bundle)
        }

        newsViewModel.healthNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success<*> -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                        isLastpage = newsViewModel.healthPage == totalPages
                        if (isLastpage){
                            binding.recyclerHealth.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error<*> -> {

                    hideProgressBar()
                    response.message?.let {message ->
                        Toast.makeText(activity,"Sorry error: $message", Toast.LENGTH_SHORT).show()
                        showErrorMessage(message)
                    }

                }
                is Resource.Loading<*> ->{
                    showProgressBar()
                }
            }
        })

        retryButton.setOnClickListener{
            newsViewModel.fetchHealthNews("in")
        }
    }

    var isError= false
    var isLoading= false
    var isScrolling = false
    var isLastpage = false

    private fun hideProgressBar(){
        binding.paginationProgressBarHealth.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        binding.paginationProgressBarHealth.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage(){
        itemHealthError.visibility = View.INVISIBLE
        isError = false
    }
    private fun showErrorMessage(message:String){
        itemHealthError.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastpage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isNotAtBeginning && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                newsViewModel.fetchHealthNews("in")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true

            }
        }
    }

    private fun setUpHealthRecycler(){
        newsAdapter = NewsAdapters()
        binding.recyclerHealth.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HealthFragment.scrollListener)
        }
    }
}