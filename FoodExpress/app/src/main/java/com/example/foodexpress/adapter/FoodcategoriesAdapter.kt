package com.example.foodexpress.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexpress.databinding.FoodcategoriesBinding
import com.example.foodexpress.model.categorie

class FoodcategoriesAdapter(private val categorielist: ArrayList<categorie>,
                            val onCategorieIconClicked: (categorie) -> Unit): RecyclerView.Adapter<FoodcategoriesAdapter.FoodCategorieViewHolder>(){
    class FoodCategorieViewHolder (val binding: FoodcategoriesBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCategorieViewHolder {
        return FoodCategorieViewHolder(FoodcategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FoodCategorieViewHolder, position: Int) {
        val categorie = categorielist[position]
        holder.binding.apply {
            foodimg.setImageResource(categorie.foodcategoriesimages)
            foodname.text = categorie.foodcategoriesitems
        }
        holder.itemView.setOnClickListener{
            onCategorieIconClicked(categorie)
        }
    }

    override fun getItemCount(): Int {
        return categorielist.size
    }

}