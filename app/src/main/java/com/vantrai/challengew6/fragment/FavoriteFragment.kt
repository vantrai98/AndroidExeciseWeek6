package com.vantrai.challengew6.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.vantrai.challengew6.R
import com.vantrai.challengew6.adapter.MovieAdapter
import com.vantrai.challengew6.model.Movie
import com.vantrai.challengew6.room.AppDatabase
import kotlinx.android.synthetic.main.content.*

class FavoriteFragment : Fragment() {
    private val GRIDLAYOUT: String = "gridLayout"
    private var gridLayout: Boolean = false
    private val MOVIELIST: String = "movieList"
    private var movieList = ArrayList<Movie>()
    private val title: String = "Favorite Movie"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieList = getListFavoriteMovie()
        fragmentTitle.text = title
        setContentLayout()
        setGridLayoutBtnResource()
        layoutBtn.setOnClickListener() {
            gridLayout = !gridLayout
            setContentLayout()
            setGridLayoutBtnResource()
        }
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

    private fun getListFavoriteMovie(): ArrayList<Movie> {
        val db = activity?.let {
            Room.databaseBuilder(it, AppDatabase::class.java, "mydatabase.db")
                .allowMainThreadQueries().build()
        }
        var data = ArrayList<Movie>()
        if (db != null) {
            data = db.movieDAO().getAll() as ArrayList<Movie>
        }
        return data
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
}