package com.example.jetpackcomponentsapp

import androidx.recyclerview.widget.DiffUtil

object NasaDiffUtilItemCallback : DiffUtil.ItemCallback<NasaHolderModel>() {

    override fun areItemsTheSame(oldItem : NasaHolderModel, newItem : NasaHolderModel) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem : NasaHolderModel, newItem : NasaHolderModel) : Boolean {
        return oldItem.title == newItem.title
            && oldItem.copyright == newItem.copyright
            && oldItem.date == newItem.date
            && oldItem.explanation == newItem.explanation
            && oldItem.image == newItem.image
    }
}