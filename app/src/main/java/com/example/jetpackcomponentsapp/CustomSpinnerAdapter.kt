package com.example.jetpackcomponentsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomSpinnerAdapter : ArrayAdapter<CustomSpinnerModel>{

    private var customSpinnerItems: List<CustomSpinnerModel>

    init {}

    constructor(context : Context, resource : Int, pickerItems : List<CustomSpinnerModel>) : super(context, resource, pickerItems){
        this.customSpinnerItems = pickerItems
    }

    override fun getView(position : Int, convertView : View?, parent : ViewGroup) : View {
        val layoutInflater : LayoutInflater = parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //Getting the Layout Inflater Service from the system
        val customView : View = convertView?:layoutInflater.inflate(R.layout.custom_spinner_items, parent, false) //Inflating out custom spinner view
        return CustomSpinnerViewHolder(position, customView)
    }

    override fun getDropDownView(position : Int, convertView : View?, parent : ViewGroup) : View {
        val layoutInflater : LayoutInflater = parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView : View = convertView?:layoutInflater.inflate(R.layout.custom_spinner_items, parent, false)
        return CustomSpinnerDropDownViewHolder(position, customView)
    }

    private fun CustomSpinnerViewHolder(position : Int, convertedView : View) : View {
        //Declaring and initializing the widgets in custom layout
        val textView : TextView? = convertedView.findViewById(R.id.textView)
        val imageView : ImageView? = convertedView.findViewById(R.id.imageView)
        //displaying the data
        //drawable items are mapped with name prefixed with 'zx_' also image names are containing underscore instead of spaces.
        //val imageRef = "zx_" + CustomSpinnerItems[position].name!!.toLowerCase().replace(" ", "_")
        //val resID = context.resources.getIdentifier(imageRef, "drawable", context.packageName)
        textView?.setText(customSpinnerItems[position].name)
        //imageView?.setVisibility(View.INVISIBLE)
        imageView?.setImageResource(customSpinnerItems[position].image!!)
        return convertedView
    }

    private fun CustomSpinnerDropDownViewHolder(position : Int, convertedView : View) : View {
        val textView : TextView? = convertedView.findViewById(R.id.textView)
        val imageView : ImageView? = convertedView.findViewById(R.id.imageView)
        textView?.setText(customSpinnerItems[position].name)
        imageView?.setImageResource(customSpinnerItems[position].image!!)
        return convertedView
    }
}