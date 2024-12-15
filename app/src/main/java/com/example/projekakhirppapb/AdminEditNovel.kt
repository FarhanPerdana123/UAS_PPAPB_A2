package com.example.projekakhirppapb

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projekakhirppapb.databinding.ActivityAdminEditNovelBinding
import com.example.projekakhirppapb.model.Novel
import com.example.projekakhirppapb.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminEditNovel : AppCompatActivity() {
    private lateinit var binding: ActivityAdminEditNovelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val novelId = intent.getStringExtra("novel_id")?.toIntOrNull()
        val judul = intent.getStringExtra("judul")
        val penulis = intent.getStringExtra("penulis")
        val tanggalTerbit = intent.getStringExtra("tanggalTerbit")
        val isiNovel = intent.getStringExtra("isiNovel")

        binding.txtJudul.setText(judul)
        binding.txtPenulis.setText(penulis)
        binding.txtTanggalTerbit.setText(tanggalTerbit)
        binding.txtIsiNovel.setText(isiNovel)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.editButton.setOnClickListener {
            val updatedJudul = binding.txtJudul.text.toString()
            val updatedPenulis = binding.txtPenulis.text.toString()
            val updatedTanggalTerbit = binding.txtTanggalTerbit.text.toString()
            val updatedIsiNovel = binding.txtIsiNovel.text.toString()

            if (novelId != null) {
                val updatedNovel = Novel(
                    id = novelId.toString(),
                    judul = updatedJudul,
                    penulis = updatedPenulis,
                    tanggalTerbit = updatedTanggalTerbit,
                    isiNovel = updatedIsiNovel
                )
                Log.d("API Request", "Updating Novel with ID: $novelId")
                Log.d("Updated Novel", "ID: ${updatedNovel.id}, Judul: ${updatedNovel.judul}, Lokasi: ${updatedNovel.penulis}, Tanggal: ${updatedNovel.tanggalTerbit}")


                val apiService = ApiClient.getInstance()
                apiService.updateNovel(novelId.toString(), updatedNovel).enqueue(object : Callback<Novel> {
                    override fun onResponse(call: Call<Novel>, response: Response<Novel>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@AdminEditNovel, "Novel berhasil diupdate", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@AdminEditNovel, "Gagal mengupdate novel", Toast.LENGTH_SHORT).show()
                            Log.e("API Error", "Response Code: ${response.code()}, Message: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<Novel>, t: Throwable) {
                        Toast.makeText(this@AdminEditNovel, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@AdminEditNovel, "Novel ID tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}