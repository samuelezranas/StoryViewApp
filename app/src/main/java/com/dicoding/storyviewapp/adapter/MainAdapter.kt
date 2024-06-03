package com.dicoding.storyviewapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem
import com.dicoding.storyviewapp.databinding.ItemStoryBinding
import com.dicoding.storyviewapp.ui.main.DetailStoryActivity

class MainAdapter : PagingDataAdapter<ListStoryItem, MainAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    inner class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            with(binding) {
                tvName.text = story.name
                tvDescription.text = story.description
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(ivItem)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.DETAIL_STORY, story)
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}
