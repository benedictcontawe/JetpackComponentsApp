package com.example.jetpackcomponentsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter : ArrayAdapter<CustomSpinnerModel>{

    private var CustomSpinnerItems: List<CustomSpinnerModel>

    init {}

    constructor(context: Context, resource: Int, pickerItems: List<CustomSpinnerModel>) :
            super(context, resource, pickerItems){
        this.CustomSpinnerItems = pickerItems
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return CustomSpinnerViewHolder(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return CustomSpinnerViewHolder(position, convertView, parent)
    }

    private fun CustomSpinnerViewHolder(position: Int, convertView: View?, parent: ViewGroup): View {
        //Getting the Layout Inflater Service from the system
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //Inflating out custom spinner view
        val customView = layoutInflater.inflate(R.layout.custom_spinner_items, parent, false)
        //Declaring and initializing the widgets in custom layout
        val imageView = customView.findViewById(R.id.imageView) as ImageView
        val textView = customView.findViewById(R.id.textView) as TextView
        //displaying the data
        //drawable items are mapped with name prefixed with 'zx_' also image names are containing underscore instead of spaces.
        //val imageRef = "zx_" + CustomSpinnerItems[position].name!!.toLowerCase().replace(" ", "_")
        //val resID = context.resources.getIdentifier(imageRef, "drawable", context.packageName)
        imageView.setImageResource(CustomSpinnerItems[position].image!!)
        //imageView.setImageDrawable();geResource(getApplicationContext(),getImageId(CustomSpinnerItems.get(position).getName()));
        textView.setText(CustomSpinnerItems[position].name)
        return customView
    }
}