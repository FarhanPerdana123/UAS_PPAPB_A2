package com.example.projekakhirppapb

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.projekakhirppapb.databinding.ActivityAdminAddNovelBinding
import com.example.projekakhirppapb.model.Novel
import com.example.projekakhirppapb.network.ApiClient
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AdminAddNovel : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddNovelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menggunakan ViewBinding
        binding = ActivityAdminAddNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtTanggalTerbit.setOnClickListener {
            showDatePickerDialog()
        }

        binding.addButton.setOnClickListener {
            val judul = binding.txtJudul.text.toString()
            val penulis = binding.txtPenulis.text.toString()
            val tanggalTerbit = binding.txtTanggalTerbit.text.toString()
            val isiNovel = binding.txtIsiNovel.text.toString()

            addNovel(judul, penulis, tanggalTerbit, isiNovel)
        }

        binding.backButton.setOnClickListener {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            if (navHostFragment != null) {
                val navController = navHostFragment.navController
                navController.navigate(R.id.adminHomeFragment)
            } else {
                println("NavHostFragment not found!")
            }
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding.txtTanggalTerbit.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun addNovel(
        judul: String,
        penulis: String,
        tanggalTerbit: String,
        isiNovel: String
    ) {
        // Log all input values before sending
        Log.d("NovelUpload", "Judul: $judul")
        Log.d("NovelUpload", "Penulis: $penulis")
        Log.d("NovelUpload", "TanggalTerbit: $tanggalTerbit")
        Log.d("NovelUpload", "IsiNovel: $isiNovel")

        val jsonData = Gson().toJsonTree(
            mapOf(
                "judul" to judul,
                "penulis" to penulis,
                "tanggalTerbit" to tanggalTerbit,
                "isiNovel" to isiNovel
            )
        ).toString()

        val requestBody = jsonData.toRequestBody("application/json".toMediaType())

        ApiClient.getInstance().addNovel(requestBody).enqueue(object : Callback<Novel> {
            override fun onResponse(call: Call<Novel>, response: Response<Novel>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show()
                    finish()
                    Log.d("NovelUpload", "Data berhasil dikirim: ${response.body()}")
                } else {
                    Log.e("NovelUpload", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Novel>, t: Throwable) {
                Log.e("NovelUpload", "Koneksi gagal: ${t.message}")
            }
        })
    }
}