package com.example.foodexpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodexpress.databinding.AdminItemViewBinding
import com.example.foodexpress.model.Product

class AdminproductAdapter : RecyclerView.Adapter<AdminproductAdapter.Adminprodutviewholder>() {
    class Adminprodutviewholder(val  binding: AdminItemViewBinding) : ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productRandomId == newItem.productRandomId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminproductAdapter.Adminprodutviewholder {
       return Adminprodutviewholder(AdminItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: AdminproductAdapter.Adminprodutviewholder,
        position: Int
    ) {
        val product = differ.currentList[position]

        holder.binding.apply {
            val imageList = ArrayList<SlideModel>()
            val productImage = product.productimageUris

            for(i in 0 until productImage!!.size){
                imageList.add(SlideModel(productImage[i].toString()))
            }
            AdminFoodimageSlider.setImageList(imageList , ScaleTypes.FIT)
            Adminfoodname.text = product.productTitle
            Adminfoodprice.text = product.productPrice.toString()
            AdminFoodQty.text = product.productUnit
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}