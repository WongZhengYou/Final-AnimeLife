package com.example.maeassignemnt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.maeassignemnt.data.AnimeDataModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(var context: Context?, var animeList: List<AnimeDataModel>?) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.anime_list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val anime = animeList?.get(position)
        if (anime != null) {
            holder.bindData(anime)
        }

        holder.itemView.setOnClickListener(View.OnClickListener { view ->

            // redirect to details page
            val bundle = Bundle()
            bundle.putParcelable("anime", anime)

            // navigate to checkin success and pass data
            findNavController(view).navigate(R.id.details, bundle)
            //Toast.makeText(context, "Clicked on: " + holder.title.text, Toast.LENGTH_SHORT).show();

        })

    }

    override fun getItemCount(): Int {
        return animeList?.size!!
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image = itemView.findViewById<ImageView>(R.id.iv_image)
        var title = itemView.findViewById<TextView>(R.id.tv_title)
        var sysnopsis = itemView.findViewById<TextView>(R.id.tv_sysnopsis)
        var genre = itemView.findViewById<TextView>(R.id.tv_genre)

        fun bindData(anime: AnimeDataModel) {
            Picasso.get().load(anime.images?.jpg?.image_url).into(image)
            title.text = anime.title
            sysnopsis.text = anime.synopsis
            if (anime.genres?.size!! > 0) {
                var genres = ""
                for (item in anime?.genres!!) {
                    genres += (item.name.toString() + ",")
                }
                genre.text = "Genre: $genres"
            } else {
                genre.text = "Genre: Unknown"
            }
        }
    }

}
