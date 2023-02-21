package com.example.maeassignemnt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.maeassignemnt.data.AnimeDataModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {
    /*val api_key =  "AIzaSyCVFskwDfJwwPpcecn4nl0adhZP1e-W3tk"*/
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
        var ranking = view.findViewById<TextView>(R.id.tv_ranking)
        var score = view.findViewById<TextView>(R.id.tv_score)
        var popularity = view.findViewById<TextView>(R.id.tv_popularity)
        var trailer = view.findViewById<YouTubePlayerView>(R.id.trailer_player)
        lifecycle.addObserver(trailer)

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
        if(animeData.title_english != null){
            information += "Title: ${animeData.title_english}\n"
        }
        if(animeData.title_japanese != null){
            information += "Japanese: ${animeData.title_japanese}\n"
        }
        if(animeData.type != null){
            information += "Type: ${animeData.type}\n"
        }
        if(animeData.episodes != null){
            information += "Episodes: ${animeData.episodes}\n"
        }
        if(animeData.season != null){
            information += "Season: ${animeData.season}\n"
        }
        if(animeData.year != null){
            information += "Year: ${animeData.year}\n"
        }
        if(animeData.status != null){
            information += "Status: ${animeData.status}\n"
        }
        if(animeData.duration != null){
            information += "Duration: ${animeData.duration}\n"
        }
        if(animeData.rating != null){
            information += "Rating: ${animeData.rating}\n"
        }
        if(animeData.producers != null){
            if(animeData.producers!!.size!! > 0){
                information += "Producers: "
                for(p in animeData.producers!!){
                    information += "${p.name},"
                }
                information += "\n";
            }
        }
        if(animeData.licensors != null){
            if(animeData.licensors!!.size!! > 0){
                information += "Licensors: "
                for(p in animeData.licensors!!){
                    information += "${p.name},"
                }
                information += "\n";
            }
        }
        if(animeData.themes != null){
            if(animeData.themes!!.size!! > 0){
                information += "Themes: "
                for(p in animeData.themes!!){
                    information += "${p.name},"
                }
                information += "\n";
            }
        }
        if(animeData.studios != null){
            if(animeData.studios!!.size!! > 0){
                information += "Studios: "
                for(p in animeData.studios!!){
                    information += "${p.name},"
                }
                information += "\n";
            }
        }
        if(animeData.score != null){
            score.text = "Score: ${animeData.score}"
        }
        else{
            score.text = "Score: Unknown"
        }
        if(animeData.rank != null){
            ranking.text = "Rank: #${animeData.rank}"
        }
        else{
            ranking.text = "Rank: #unknown"
        }
        if(animeData.popularity != null){
            popularity.text = "Popular: #${animeData.popularity}"
        }
        else{
            popularity.text = "Popular: #unknown"
        }

        info.text = information

        // set up video player
        if(animeData.trailer?.youtube_id != null) {
            trailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    val videoId = animeData.trailer?.youtube_id
                    if (videoId != null) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                }
            })

        }
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