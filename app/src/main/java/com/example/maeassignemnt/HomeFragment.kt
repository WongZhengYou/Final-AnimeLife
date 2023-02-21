package com.example.maeassignemnt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.maeassignemnt.data.AnimeList
import com.example.maeassignemnt.network.AnimeService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var adapter: RecyclerViewAdapter
    lateinit var anime_recyclerview: RecyclerView
    private lateinit var pb_loading: ProgressBar
    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout
    private var tabsTitle = arrayListOf<String>("All", "Top Airing", "Top Upcoming","Most Popular", "Top TV Series", "Top Movies" )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        anime_recyclerview =view.findViewById<RecyclerView>(R.id.recyclerview)
        pb_loading = view.findViewById(R.id.pb_loading)
        viewPager = view.findViewById(R.id.viewPager)
        tabs = view.findViewById(R.id.tabsLayout)



        // initialize tabs
        for(tabTitle in tabsTitle) {
            tabs.addTab(tabs.newTab().setText(tabTitle))
        }

        tabs.selectTab(tabs.getTabAt(0))
        getTopAnime()
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                when(tab?.position){
                    0-> {
                        getTopAnime()
                    }
                    1->{
                        getTopAiring()
                    }
                    2->{
                        getTopUpcoming()
                    }
                    3->{
                        getTopPopular()
                    }
                    4->{
                        getTopTV()
                    }
                    5->{
                        getTopMovies()
                    }
                    else->Toast.makeText(context, tab?.position!!,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {

            }
    }

    private fun getTopAnime() {
        val animeList: Call<AnimeList> = AnimeService.animeInstance.getTopAnimeAll(1, 100)
        bindRecyclerView(animeList)
    }

    private fun getTopAiring(){
        val animeList: Call<AnimeList> = AnimeService.animeInstance.getTopAnimeByFilter(1, 100,"airing")
        bindRecyclerView(animeList)
    }

    private fun getTopUpcoming(){
        val animeList: Call<AnimeList> = AnimeService.animeInstance.getTopAnimeByFilter(1, 100,"upcoming")
        bindRecyclerView(animeList)
    }

    private fun getTopPopular(){
        val animeList: Call<AnimeList> = AnimeService.animeInstance.getTopAnimeByFilter(1, 100,"bypopularity")
        bindRecyclerView(animeList)
    }

    private fun getTopTV(){
        val animeList: Call<AnimeList> = AnimeService.animeInstance.getTopAnimeByType(1, 100,"tv")
        bindRecyclerView(animeList)
    }
    private fun getTopMovies(){
        val animeList: Call<AnimeList> = AnimeService.animeInstance.getTopAnimeByType(1, 100,"movie")
        bindRecyclerView(animeList)
    }

    private fun bindRecyclerView(animeList: Call<AnimeList>){
        pb_loading.visibility = View.VISIBLE
        animeList.enqueue(object : Callback<AnimeList> {
            override fun onResponse(call: Call<AnimeList>, response: Response<AnimeList>) {
                val animeList = response.body()
                if (animeList != null) {
                    Log.d("HomeData", animeList.toString())
                    adapter = RecyclerViewAdapter(context, animeList.data)
                    anime_recyclerview.adapter = adapter
                    anime_recyclerview.layoutManager = LinearLayoutManager(
                        view?.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    pb_loading.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<AnimeList>, t: Throwable) {
                Log.d("HomeData", t.toString())
            }

        })
    }
}