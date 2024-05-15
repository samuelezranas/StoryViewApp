package com.dicoding.storyviewapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem
import com.dicoding.storyviewapp.databinding.ItemStoryBinding
import com.dicoding.storyviewapp.ui.main.DetailStoryActivity

class MainAdapter : ListAdapter<ListStoryItem, MainAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val storyItem = getItem(position)
        holder.bind(storyItem)
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storyItem: ListStoryItem) {
            with(binding) {
                tvName.text = storyItem.name
                tvDescription.text = storyItem.description
                Glide.with(itemView.context)
                    .load(storyItem.photoUrl)
                    .into(ivItem)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java).apply {
                        putExtra(DetailStoryActivity.DETAIL_STORY, storyItem)
                    }
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        androidx.core.util.Pair(ivItem, "profile"),
                        androidx.core.util.Pair(tvName, "name"),
                        androidx.core.util.Pair(tvDescription, "description")
                    )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
