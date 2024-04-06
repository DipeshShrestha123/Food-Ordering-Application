package com.example.foodexpress.adminfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodexpress.ViewModel.AdminViewModel
import com.example.foodexpress.adapter.AdminproductAdapter
import com.example.foodexpress.adapter.FoodcategoriesAdapter
import com.example.foodexpress.constants
import com.example.foodexpress.databinding.FragmentAdminHomeBinding
import com.example.foodexpress.model.categorie
import kotlinx.coroutines.launch

class adminHomeFragment : Fragment() {
    val viewModel : AdminViewModel by viewModels()
    private lateinit var binding: FragmentAdminHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminHomeBinding.inflate(inflater,container,false)
        setAllCategories()
        setAllProduct("All Food")
        return binding.root
    }

    private fun setAllCategories(){
        val categorieList = ArrayList<categorie>()

        for( i in 0 until constants.foodcategoriesname.size ){
            categorieList.add(categorie(constants.foodcategoriesname[i] , constants.foodcategoriesicon[i]))
        }
        binding.foodcategorierecyclerView.adapter = FoodcategoriesAdapter(categorieList, ::onCategorieIconClicked)

    }
    private fun onCategorieIconClicked(categorie: categorie){
        setAllProduct(categorie.foodcategoriesitems)
    }

    private fun setAllProduct(foodcategoriesitems: String?) {
        binding.shimmerframelayout.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.fetchAllProduct(foodcategoriesitems).collect{

                if(it.isEmpty()){
                    binding.productnotfound.visibility = View.VISIBLE
                }
                else{
                    binding.productnotfound.visibility = View.GONE
                }
                val adapterproduct = AdminproductAdapter()
                binding.popularsectionrecyclerView.adapter = adapterproduct
                adapterproduct.differ.submitList(it)
                binding.shimmerframelayout.visibility = View.GONE
            }
        }
    }



}
