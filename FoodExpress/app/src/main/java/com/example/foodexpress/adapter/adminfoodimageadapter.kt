package com.example.foodexpress.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexpress.databinding.ImageviewstyleBinding

class adminfoodimageadapter (private val ImageUris: ArrayList<Uri>) :
    RecyclerView.Adapter<adminfoodimageadapter.adminimageViewHolder>() {
    class adminimageViewHolder (val binding: ImageviewstyleBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adminimageViewHolder {
        return adminimageViewHolder(ImageviewstyleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return ImageUris.size
    }

    override fun onBindViewHolder(holder: adminimageViewHolder, position: Int) {
        val foodimage = ImageUris[position]
        holder.binding.apply {
            foodimgfromuserinlayout.setImageURI(foodimage)
        }
        holder.binding.removephoto.setOnClickListener {
            if(position < ImageUris.size){
                ImageUris.removeAt(position)
                notifyItemRemoved(position)
            }

        }
    }
}