package com.example.foodexpress.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexpress.databinding.FoodcategoriesBinding

class FoodcategoriesAdapter(private val foodcategoriesitems: List<String>, private val foodcategoriesimages: List<Int> ): RecyclerView.Adapter<FoodcategoriesAdapter.FoodCategorieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCategorieViewHolder {
        return FoodCategorieViewHolder(FoodcategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FoodCategorieViewHolder, position: Int) {
        val foodcategoriesitem = foodcategoriesitems[position]
        val foodcategoriesimage = foodcategoriesimages[position]
        holder.bind(foodcategoriesitem,foodcategoriesimage)
    }

    override fun getItemCount(): Int {
        return foodcategoriesimages.size
    }
    class FoodCategorieViewHolder (private val binding: FoodcategoriesBinding) : RecyclerView.ViewHolder(binding.root){

        private val image = binding.foodimg
        fun bind(foodcategoriesitem: String, foodcategoriesimage: Int) {
            binding.foodname.text = foodcategoriesitem
            image.setImageResource(foodcategoriesimage)
        }
        

    }
}