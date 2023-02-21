package com.example.maeassignemnt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maeassignemnt.data.AnimeList
import com.example.maeassignemnt.network.AnimeService
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    private lateinit var search: TextInputEditText
    private lateinit var btn_search: ExtendedFloatingActionButton
    lateinit var searchData: String
    lateinit var anime_recyclerview: RecyclerView
    private lateinit var pb_loading: ProgressBar
    lateinit var adapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

        search = view.findViewById(R.id.et_search)
        btn_search = view.findViewById<ExtendedFloatingActionButton>(R.id.btn_search)
        anime_recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview2)
        pb_loading = view.findViewById(R.id.pb_loading2)
        adapter = RecyclerViewAdapter(context, null)
        searchData = ""
        btn_search.setOnClickListener {
            if (search.text.isNullOrEmpty()) {

            } else {
                searchData = search.text.toString().trim()
                getSearchAnime()
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {

            }
    }

    private fun getSearchAnime() {
        val animeList: Call<AnimeList> = AnimeService.animeInstance.getAnimeSearch(searchData)
        bindRecyclerView(animeList)
    }

    private fun bindRecyclerView(animeList: Call<AnimeList>) {
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

