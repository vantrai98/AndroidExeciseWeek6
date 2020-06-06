package com.vantrai.challengew6.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.vantrai.challengew6.R
import com.vantrai.challengew6.fragment.FavoriteFragment
import com.vantrai.challengew6.fragment.NowPlayingFragment
import com.vantrai.challengew6.fragment.TopRateFragment
import com.vantrai.challengew6.model.*
import com.vantrai.challengew6.room.AppDatabase
import kotlinx.android.synthetic.main.main_screen.*
import retrofit2.Callback
import retrofit2.Response


class MainActivity : FragmentActivity() {
    private var tabIndex: Int = 0
    private val favoriteFragment = FavoriteFragment()
    private val nowPlayingFragment = NowPlayingFragment()
    private val topRateFragment = TopRateFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        setContentFragment()
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.tab_playing -> {
                    tabIndex = 0
                }
                R.id.tab_top_rating -> {
                    tabIndex = 1
                }
                R.id.tab_favorite -> {
                    tabIndex = 2
                }
            }
            setContentFragment()
            true
        }
    }

    private fun setContentFragment() {
        when (tabIndex) {
            0 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, nowPlayingFragment)
                    .addToBackStack(null)
                    .commit()
            }
            1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, topRateFragment)
                    .addToBackStack(null)
                    .commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, favoriteFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}
