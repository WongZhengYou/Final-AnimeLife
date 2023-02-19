package com.example.maeassignemnt.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class AnimeDataModel() :Parcelable{
    @SerializedName("mal_id")
    var mal_id: Int? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("images")
    var images: Images? = null
    @SerializedName("trailer")
    var trailer = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("title_english")
    var title_english: String? = null
    @SerializedName("title_japanese")
    var title_japanese: String? = null
    @SerializedName("synopsis")
    var synopsis: String?= null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("episodes")
    var episodes: Int? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("airing")
    var airing: Boolean ? = false
    @SerializedName("duration")
    var duration: String?= null
    @SerializedName("rating")
    var rating: String? = null
    @SerializedName("score")
    var score: Float? = null
    @SerializedName("rank")
    var rank: Int? = null
    @SerializedName("popularity")
    var popularity: Int? = null
    @SerializedName("season")
    var season: String?=null
    @SerializedName("year")
    var year:Int? = null
    @SerializedName("producers")
    var producers: List<Info>? = null
    @SerializedName("studios")
    var studios: List<Info>?=null
    @SerializedName("genres")
    var genres: List<Info>?=null
    @SerializedName("themes")
    var themes: List<Info>?=null
    @SerializedName("licensors")
    var licensors: List<Info>?=null

    constructor(parcel: Parcel) : this() {
        mal_id = parcel.readValue(Int::class.java.classLoader) as? Int
        url = parcel.readString()
        title = parcel.readString()
        title_english = parcel.readString()
        title_japanese = parcel.readString()
        synopsis = parcel.readString()
        type = parcel.readString()
        episodes = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readString()
        airing = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        duration = parcel.readString()
        rating = parcel.readString()
        score = parcel.readValue(Float::class.java.classLoader) as? Float
        rank = parcel.readValue(Int::class.java.classLoader) as? Int
        popularity = parcel.readValue(Int::class.java.classLoader) as? Int
        season = parcel.readString()
        year = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(mal_id)
        parcel.writeString(url)
        parcel.writeString(title)
        parcel.writeString(title_english)
        parcel.writeString(title_japanese)
        parcel.writeString(synopsis)
        parcel.writeString(type)
        parcel.writeValue(episodes)
        parcel.writeString(status)
        parcel.writeValue(airing)
        parcel.writeString(duration)
        parcel.writeString(rating)
        parcel.writeValue(score)
        parcel.writeValue(rank)
        parcel.writeValue(popularity)
        parcel.writeString(season)
        parcel.writeValue(year)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeDataModel> {
        override fun createFromParcel(parcel: Parcel): AnimeDataModel {
            return AnimeDataModel(parcel)
        }

        override fun newArray(size: Int): Array<AnimeDataModel?> {
            return arrayOfNulls(size)
        }
    }

}

class Info{
    var mal_id: Int?=null
    var type: String?=null
    var name: String?=null
    var url: String?=null
}

class Images{
    val jpg:ImageUrl?=null
    val webp: ImageUrl?=null
}

class ImageUrl{
    val image_url: String?=null
    val small_image_url: String?=null
    val large_image_url: String?=null
}