package com.example.projekakhirppapb.model

import com.google.gson.annotations.SerializedName

data class Novel (

    @SerializedName("_id")
    val id: String,

    @SerializedName("judul")
    val judul: String,

    @SerializedName("penulis")
    val penulis: String,

    @SerializedName("tanggalTerbit")
    val tanggalTerbit: String,

    @SerializedName("isiNovel")
    val isiNovel: String
)