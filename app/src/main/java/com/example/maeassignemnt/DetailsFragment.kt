package com.example.maeassignemnt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.maeassignemnt.data.AnimeDataModel
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private lateinit var animeData: AnimeDataModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View =inflater.inflate(R.layout.fragment_details, container, false)
        // get bundle
        var bundle: Bundle? = this.arguments
        if (bundle != null) {
            animeData = bundle.getParcelable("anime")!!
        }
        var image = view.findViewById<ImageView>(R.id.iv_image)
        var title = view.findViewById<TextView>(R.id.tv_title)
        var sysnopsis = view.findViewById<TextView>(R.id.tv_sysnopsis)
        var genre = view.findViewById<TextView>(R.id.tv_genre)
        var info = view.findViewById<TextView>(R.id.tv_info)
        Picasso.get().load(animeData.images?.jpg?.image_url).into(image)
        title.text = animeData.title
        sysnopsis.text = animeData.synopsis
        if (animeData.genres?.size!! > 0) {
            var genres = ""
            for (item in animeData?.genres!!) {
                genres += (item.name.toString() + ",")
            }
            genre.text = "Genre: $genres"
        } else {
            genre.text = "Genre: Unknown"
        }
        var information = ""
        if(animeData.score != null){
            information = "Score: ${animeData.score}\n"
        }
        if(animeData.rank != null){
            information += "Rank: ${animeData.rank}\n"
        }
        if(animeData.rating != null){
            information += "Rating: ${animeData.rating}\n"
        }
        info.text = information
        // back button
        val btn_back: ImageButton = view.findViewById<ImageButton>(R.id.btn_back)
        btn_back.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.home)

        })
        return  view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            DetailsFragment().apply {

            }
    }
}