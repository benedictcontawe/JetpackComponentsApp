package com.example.jetpackcomponentsapp.view

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.model.CustomModel

class ViewPagerViewHolder : BaseViewPagerHolder {

    companion object {
        private val TAG = ViewPagerViewHolder::class.java.getSimpleName()
    }
    //region cell_custom
    private val image : AppCompatImageView
    private val text : AppCompatTextView
    //endregion
    constructor(itemView : View) : super(itemView) { Log.d(TAG, "constructor")
        image = itemView.findViewById(R.id.image)
        text = itemView.findViewById(R.id.text)
    }

    public override fun bindDataToViewHolder(customModel : CustomModel, position : Int) {
        image.setImageResource(customModel.icon)
        text.setText(customModel.name)
    }
}