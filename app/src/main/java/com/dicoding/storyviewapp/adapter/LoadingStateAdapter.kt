package com.dicoding.storyviewapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.storyviewapp.databinding.ItemStoryBinding

class LoadingStateAdapter : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {
    class LoadingStateViewHolder(private val binding: ItemStoryBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState){
            if (loadState is LoadState.Error){
                binding
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding)
    }
}