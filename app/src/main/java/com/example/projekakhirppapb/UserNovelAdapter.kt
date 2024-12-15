package com.example.projekakhirppapb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekakhirppapb.databinding.ItemNovelBinding
import com.example.projekakhirppapb.model.Novel

class UserNovelAdapter (
    private val novels: List<com.example.projekakhirppapb.model.Novel>,
    private val onloveClick: (com.example.projekakhirppapb.model.Novel) -> Unit,
) : RecyclerView.Adapter<UserNovelAdapter.EventViewHolder>() {

    private var onItemClickListener: ((com.example.projekakhirppapb.model.Novel) -> Unit)? = null

    fun setOnItemClickListener(listener: (com.example.projekakhirppapb.model.Novel) -> Unit) {
        onItemClickListener = listener
    }

    inner class EventViewHolder(private val binding: ItemNovelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Memastikan item bisa diklik
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(novels[adapterPosition])
            }
        }

        fun bind(event: Novel) {
            binding.txtTitle.text = event.judul
            binding.txtWriter.text = event.penulis
            binding.txtDate.text = event.tanggalTerbit
            binding.txtSynopsis.text = event.isiNovel
            // Handle edit button click
            binding.bookmarkButton.setOnClickListener {
                onloveClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemNovelBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(novels[position])
    }

    override fun getItemCount(): Int = novels.size
}