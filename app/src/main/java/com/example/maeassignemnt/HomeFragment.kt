package com.example.maeassignemnt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maeassignemnt.data.AnimeList
import com.example.maeassignemnt.network.AnimeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var adapter: RecyclerViewAdapter
    lateinit var anime_recyclerview: RecyclerView
    private lateinit var pb_loading: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        anime_recyclerview =view.findViewById<RecyclerView>(R.id.recyclerview)
        pb_loading = view.findViewById(R.id.pb_loading)
        pb_loading.visibility = View.VISIBLE
        getTopAnime()
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