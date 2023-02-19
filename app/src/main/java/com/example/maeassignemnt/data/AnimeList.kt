package com.example.maeassignemnt.data

import com.google.gson.annotations.SerializedName

class AnimeList {
    @SerializedName("data")
    val data: List<AnimeDataModel>? = null
    @SerializedName("pagination")
    val pagination: Pagination? = null
}

class AnimeListItems{
    val count : Int? = null
    val total: Int? = null
    val per_page: Int? = null

}

class Pagination{
    val last_visible_page: Int? = null
    val has_next_page: Boolean? = true
    val current_page: Int? = null
    val items: AnimeListItems? = null
}