package com.vantrai.challengew6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vantrai.challengew6.R
import com.vantrai.challengew6.activity.MovieService
import com.vantrai.challengew6.adapter.MovieAdapter
import com.vantrai.challengew6.model.DataCenter
import com.vantrai.challengew6.model.Movie
import com.vantrai.challengew6.model.MovieResponse
import com.vantrai.challengew6.model.TopRateMovie
import kotlinx.android.synthetic.main.content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRateFragment : Fragment() {
    private val GRIDLAYOUT: String = "gridLayout"
    private var gridLayout: Boolean = false
    private val MOVIELIST: String = "movieList"
    private var movieList = ArrayList<Movie>()
    private val title: String = "Top Rate"
    private var pageIndex: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieListFromApi()
        fragmentTitle.text = title
        setGridLayoutBtnResource()
        setContentLayout()
        layoutBtn.setOnClickListener() {
            gridLayout = !gridLayout
            setContentLayout()
        }

        movieListViewRCV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    pageIndex += 1
                    getMovieListFromApi()
                }
            }
        })
    }

    private fun setGridLayoutBtnResource() {
        layoutBtn.setImageResource(
            if (gridLayout) {
                R.drawable.ic_grid
            } else {
                R.drawable.ic_list
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            gridLayout = savedInstanceState.getBoolean(GRIDLAYOUT)
            movieList =
                savedInstanceState.getParcelableArrayList<Movie>(MOVIELIST) as ArrayList<Movie>
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(GRIDLAYOUT, gridLayout)
        outState.putParcelableArrayList(MOVIELIST, movieList)
    }

    private fun setContentLayout() {
        movieListViewRCV.apply {
            layoutManager = if (gridLayout) {
                GridLayoutManager(activity, 2)
            } else {
                LinearLayoutManager(activity)
            }
            adapter = MovieAdapter(context, movieList)
        }
    }

    private fun getMovieListFromDataCenter(): ArrayList<Movie> {
        val data = Gson().fromJson<TopRateMovie>(
            DataCenter.getTopRateMovieJson(),
            TopRateMovie::class.java
        )
        return data.results
    }

    private fun getMovieListFromApi() {
        var data = ArrayList<Movie>()
        MovieService.getInstance().getApi().getTopRateMovie(pageIndex).enqueue(
            object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                    Toast.makeText(
                        context,
                        "Something was wrong, please try later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<MovieResponse>?,
                    response: Response<MovieResponse>?
                ) {
                    response?.let {
                        movieList.addAll(it.body().results)
                        movieListViewRCV.adapter?.notifyDataSetChanged()
                    }
                }

            }
        )
    }
}