package com.example.projekakhirppapb

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.projekakhirppapb.databinding.ActivityUserNovelBinding

class UserNovel : AppCompatActivity() {
    private lateinit var binding: ActivityUserNovelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val originalImageUrl = intent.getStringExtra("imgId")
        Glide.with(this@UserNovel)
            .load(originalImageUrl)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.imgDetailPoster)
        val title = binding.detailJudul
        val director = binding.detailPenulis
        val time = binding.detailRilis
        val synopsis = binding.detailSinopsis

        title.text = intent.getStringExtra("judul") ?: "Unknown Title"
        director.text = intent.getStringExtra("penulis") ?: "Unknown Author"
        time.text = intent.getStringExtra("tanggalTerbit") ?: "Unknown Date"
        synopsis.text = intent.getStringExtra("isiNovel") ?: "No Synopsis Available"

        binding.btnDetailBack.setOnClickListener {
            // Navigate to AdminDashboardActivity
            val intent = Intent(this@UserNovel, UserActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the current activity to avoid going back to it with the back button
        }
    }
}