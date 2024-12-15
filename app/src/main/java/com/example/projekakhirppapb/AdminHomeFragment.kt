package com.example.projekakhirppapb

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projekakhirppapb.databinding.FragmentAdminHomeBinding
import com.example.projekakhirppapb.model.Novel
import com.example.projekakhirppapb.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class AdminHomeFragment : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        val apiService = ApiClient.getInstance()

        apiService.getAllNovel().enqueue(object : retrofit2.Callback<List<Novel>> {
            override fun onResponse(call: Call<List<Novel>>, response: Response<List<Novel>>) {
                // Periksa apakah binding masih valid
                if (isAdded && _binding != null) {
                    if (response.isSuccessful && response.body() != null) {
                        val events = response.body()!!.toMutableList()  // Mengubah List menjadi MutableList

                        // Atur adapter RecyclerView
                        val adapter = NovelAdapter(
                            events,
                            onEditClick = { novel ->
                                val intent = Intent(requireContext(), AdminEditNovel::class.java)
                                intent.putExtra("novel_id", novel.id)
                                intent.putExtra("judul", novel.judul)
                                intent.putExtra("penulis", novel.penulis)
                                intent.putExtra("tanggalTerbit", novel.tanggalTerbit)
                                intent.putExtra("isiNovel", novel.isiNovel)
                                startActivity(intent)
                            },
                            onDeleteClick = { event ->
                                deleteNovel(event, events)  // Panggil fungsi deleteEvent
                            }
                        )

                        // Menyambungkan adapter ke RecyclerView
                        _binding?.cardAdmin?.apply {
                            layoutManager = GridLayoutManager(requireContext(), 2) // 2 kolom
                            this.adapter = adapter
                        }
                    } else {
                        Toast.makeText(requireContext(), "Gagal mengambil data!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Novel>>, t: Throwable) {
                // Periksa apakah binding masih valid
                if (isAdded && _binding != null) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}