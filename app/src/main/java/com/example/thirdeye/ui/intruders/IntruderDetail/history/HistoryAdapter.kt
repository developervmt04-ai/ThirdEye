package com.example.thirdeye.ui.intruders.IntruderDetail.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdeye.data.models.IntrudersImages
import com.example.thirdeye.databinding.HistoryRvItemBinding

class HistoryAdapter(): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding= HistoryRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
       val intruders=differ.currentList[position]
        holder.binding.intruderImage.setImageBitmap(intruders.bitmap)
        holder.binding.title.text=intruders.id
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    val diffCallback= object : DiffUtil.ItemCallback<IntrudersImages>()
    {
        override fun areItemsTheSame(
            oldItem: IntrudersImages,
            newItem: IntrudersImages
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: IntrudersImages,
            newItem: IntrudersImages
        ): Boolean {
          return oldItem==newItem
        }


    }
    val differ= AsyncListDiffer(this,diffCallback)

    inner class ViewHolder(val binding: HistoryRvItemBinding): RecyclerView.ViewHolder(binding.root){





    }
}