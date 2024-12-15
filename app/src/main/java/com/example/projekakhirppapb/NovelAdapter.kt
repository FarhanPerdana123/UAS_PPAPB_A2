package com.example.projekakhirppapb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekakhirppapb.databinding.ItemAdminBinding
import com.example.projekakhirppapb.model.Novel

class NovelAdapter (
    private val novels: List<com.example.projekakhirppapb.model.Novel>,
    private val onEditClick: (com.example.projekakhirppapb.model.Novel) -> Unit,
    private val onDeleteClick: (com.example.projekakhirppapb.model.Novel) -> Unit
) : RecyclerView.Adapter<NovelAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: ItemAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Novel) {
            binding.txtTitle.text = event.judul
            binding.txtWriter.text = event.penulis
            binding.txtDate.text = event.tanggalTerbit
            binding.txtSynopsis.text = event.isiNovel

            binding.editButton.setOnClickListener {
                onEditClick(event)
            }

            // Handle delete button click
            binding.deleteButton.setOnClickListener {
                onDeleteClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemAdminBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(novels[position])
    }

    override fun getItemCount(): Int = novels.size
}