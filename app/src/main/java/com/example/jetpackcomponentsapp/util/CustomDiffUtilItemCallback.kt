package com.example.jetpackcomponentsapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.jetpackcomponentsapp.model.CustomHolderModel

object CustomDiffUtilItemCallback : DiffUtil.ItemCallback<CustomHolderModel>(){

    override fun areItemsTheSame(oldItem : CustomHolderModel, newItem : CustomHolderModel) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem : CustomHolderModel, newItem : CustomHolderModel) : Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.icon == newItem.icon
    }
}