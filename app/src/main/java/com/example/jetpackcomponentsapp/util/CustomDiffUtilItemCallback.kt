package com.example.jetpackcomponentsapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.jetpackcomponentsapp.model.CustomModel

object CustomDiffUtilItemCallback : DiffUtil.ItemCallback<CustomModel>() {

    override fun areItemsTheSame(oldItem : CustomModel, newItem : CustomModel) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem : CustomModel, newItem : CustomModel) : Boolean {
        //return oldItem.equals(newItem)
        return oldItem.id == newItem.id &&
               oldItem.name == newItem.name &&
               oldItem.icon == newItem.icon
    }
}