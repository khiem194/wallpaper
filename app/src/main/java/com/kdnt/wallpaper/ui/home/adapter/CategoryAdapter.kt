package com.kdnt.wallpaper.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kdnt.wallpaper.data.model.CategoryModel
import com.kdnt.wallpaper.databinding.ItemCategoryBinding

class CategoryAdapter(private val mListCategory : MutableList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var onClickItemCategoryModel: ((categoryModel: CategoryModel) -> Unit)? = null

    fun setData(list: MutableList<CategoryModel>) {
        mListCategory.clear()
        mListCategory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryModel = mListCategory[position]

        holder.binding.tvCategory.text = categoryModel.nameCategory

        holder.itemView.setOnClickListener {
            onClickItemCategoryModel?.invoke(mListCategory[holder.layoutPosition])
        }

    }

    override fun getItemCount(): Int = mListCategory.size

    class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}