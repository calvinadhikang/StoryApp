package com.example.storyapp.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.storyapp.databinding.RowStoryBinding
import com.example.storyapp.response.ListStoryItem

class StoryPagerAdapter(
    var cb : (data: ListStoryItem) -> Unit
): PagingDataAdapter<ListStoryItem, StoryPagerAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(viewHolder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        if (story!= null){
            viewHolder.bind(story)
        }

        viewHolder.itemView.setOnClickListener {
            cb(story!!)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowStoryBinding.inflate(inflater, parent, false)
        return StoryViewHolder(binding)
    }


    class StoryViewHolder(private val binding: RowStoryBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem){
            binding.ivItemName.text = data.name

            binding.progressStoryRow.visibility = View.VISIBLE

            Glide
                .with(binding.root.context)
                .load(data.photoUrl)
                .fitCenter()
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressStoryRow.visibility = View.INVISIBLE
                        return false
                    }

                })
                .into(binding.ivItemPhoto)
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}