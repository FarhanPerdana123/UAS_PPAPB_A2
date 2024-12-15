package com.example.projekakhirppapb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projekakhirppapb.database.NovelEntity
import com.example.projekakhirppapb.database.NovelRoomDatabase
import com.example.projekakhirppapb.databinding.FragmentUserBookmarkBinding
import com.example.projekakhirppapb.model.Novel
import kotlinx.coroutines.launch

class UserBookmarkFragment : Fragment() {
    private var _binding: FragmentUserBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserNovelAdapter // Mendeklarasikan adapter sebagai properti fragment
    private var favoriteEvents: MutableList<Novel> = mutableListOf() // List mutable untuk event favorit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBookmarkBinding.inflate(inflater, container, false)
        showBookmarkNovels() // Panggil fungsi ini untuk menampilkan data
        return binding.root
    }

    private fun showBookmarkNovels() {
        val db = NovelRoomDatabase.getDatabase(requireContext()) // Inisialisasi database
        val novelDao = db.novelDao()

        lifecycleScope.launch {
            // Ambil data favorit dari Room dan konversi ke List<Event>
            val favoriteEventsFromDb = novelDao.getAllNovel().map { convertToEvent(it) }
            Log.d("UserFavoritesFragment", "Favorite events size: ${favoriteEventsFromDb.size}")
            favoriteEventsFromDb.forEach {
                Log.d("UserFavoritesFragment", "Event: ${it.judul}")
            }

            // Cek jika favoriteEvents memiliki data
            if (favoriteEventsFromDb.isEmpty()) {
                Log.d("UserFavoritesFragment", "No favorite events found")
            }

            // Update favoriteEvents list
            favoriteEvents.clear()
            favoriteEvents.addAll(favoriteEventsFromDb)

            // Inisialisasi adapter dengan favoriteEvents dan onloveClick
            adapter = UserNovelAdapter(favoriteEvents) { event ->
                lifecycleScope.launch {
                    // Menghapus event dari Room
                    novelDao.deleteNovelById(event.id) // Menghapus berdasarkan ID
                    // Update list favoriteEvents setelah penghapusan
                    favoriteEvents.remove(event)
                    // Notifikasi adapter bahwa data telah berubah
                    adapter.notifyDataSetChanged() // Memperbarui tampilan
                }
            }

            // Set listener untuk klik item card
            adapter.setOnItemClickListener { event ->
                val intent = Intent(requireContext(), UserNovel::class.java)
                // Mengirim data ke Activity UserDetailAcara
                intent.putExtra("judul", event.judul)
                intent.putExtra("penulis", event.penulis)
                intent.putExtra("tanggalTerbit", event.tanggalTerbit)
                intent.putExtra("isiNovel", event.isiNovel)
                startActivity(intent)
            }

            // Mengatur RecyclerView
            binding.cardUser.apply {
                layoutManager = GridLayoutManager(requireContext(), 2) // 2 kolom
                this.adapter = this@UserBookmarkFragment.adapter
            }

            Log.d("UserFavoritesFragment", "RecyclerView adapter set with ${favoriteEvents.size} items.")
        }
    }


    // Fungsi untuk konversi EventEntity ke Event
    fun convertToEvent(eventEntity: NovelEntity): Novel {
        return Novel(
            eventEntity.id.toString(),
            eventEntity.judul,
            eventEntity.penulis,
            eventEntity.tanggalTerbit,
            eventEntity.isiNovel
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding ketika view dihancurkan
    }
}