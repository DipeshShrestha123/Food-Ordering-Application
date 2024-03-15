package com.example.foodexpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexpress.databinding.PopularitemsBinding

class PopularItemsAdapter (private val popularitemsnames: List<String>, private val popularimages: List<Int>,private val popularratings: List<String>,private val popularprices: List<String> ): RecyclerView.Adapter<PopularItemsAdapter.PopularitemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularitemViewHolder {
        return PopularitemViewHolder(PopularitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: PopularitemViewHolder,
        position: Int
    ) {
        val popularitemsname = popularitemsnames[position]
        val popularfoodimage = popularimages[position]
        val popularrating = popularratings[position]
        val popularprice = popularprices[position]
        holder.bind(popularitemsname,popularfoodimage,popularrating,popularprice)
    }

    override fun getItemCount(): Int {
        return popularitemsnames.size
    }
    class PopularitemViewHolder  (private val binding:PopularitemsBinding ) : RecyclerView.ViewHolder(binding.root){
        private val image = binding.popularfoodimg
        fun bind(
            popularitemsname: String,
            popularfoodimage: Int,
            popularrating: String,
            popularprice: String
        ) {
            binding.popularfoodname.text = popularitemsname
            image.setImageResource(popularfoodimage)
            binding.popularfoodrating.text = popularrating
            binding.popularfoodprice.text = popularprice

        }

    }

}
