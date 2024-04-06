package com.example.foodexpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexpress.databinding.PopularitemsBinding
import com.example.foodexpress.model.popularproduct

class PopularItemsAdapter (private val productList: ArrayList<popularproduct>): RecyclerView.Adapter<PopularItemsAdapter.PopularitemViewHolder>() {

    class PopularitemViewHolder  (val binding:PopularitemsBinding ) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularitemViewHolder {
        return PopularitemViewHolder(PopularitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(
        holder: PopularitemViewHolder,
        position: Int
    ){
        val product = productList[position]
        holder.binding.apply {
            popularfoodname.text = product.popularitemname
            popularfoodimg.setImageResource(product.popularitemimg)
            popularfoodrating.text = product.popularitemrating
            popularfoodprice.text = product.popularitemprice

        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}
