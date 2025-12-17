package com.example.thirdeye.ui.camouflage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdeye.data.localData.AppIcons
import com.example.thirdeye.databinding.IconItemBinding

class IconsAdapter(): RecyclerView.Adapter<IconsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding= IconItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
       val icons=differ.currentList[position]

        holder.binding.icon.setImageResource(icons.icon)
        holder.binding.iconName.text=icons.name
        holder.binding.root.setOnClickListener {
            onClick?.invoke(icons)


        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    val diffCallBack= object : DiffUtil.ItemCallback<AppIcons>(){
        override fun areItemsTheSame(
            oldItem: AppIcons,
            newItem: AppIcons
        ): Boolean {
           return oldItem.icon==newItem.icon

        }

        override fun areContentsTheSame(
            oldItem: AppIcons,
            newItem: AppIcons
        ): Boolean {
            return oldItem==newItem
        }


    }

    var onClick:((AppIcons)-> Unit)?=null
    val differ= AsyncListDiffer(this,diffCallBack)
    inner class ViewHolder(val binding: IconItemBinding): RecyclerView.ViewHolder(binding.root){



    }
}