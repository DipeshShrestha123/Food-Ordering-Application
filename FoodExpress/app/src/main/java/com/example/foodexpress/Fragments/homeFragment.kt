package com.example.foodexpress.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodexpress.R
import com.example.foodexpress.adapter.FoodcategoriesAdapter
import com.example.foodexpress.adapter.PopularItemsAdapter
import com.example.foodexpress.constants
import com.example.foodexpress.databinding.FragmentHomeBinding
import com.example.foodexpress.model.categorie
import com.example.foodexpress.model.popularproduct

class homeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding.shimmerframelayout.visibility = View.INVISIBLE
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        imageslider()
        setAllCategories()
        setAllProduct()
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
        getCategorieProduct(categorie.foodcategoriesitems)
    }

    private fun getCategorieProduct(foodcategoriesitems: String?) {

    }

    private fun setAllProduct(){
        val productList = ArrayList<popularproduct>()
        for( i in 0 until constants.popularitemname.size ) {
            productList.add(popularproduct(constants.popularitemname[i],constants.popularitemimg[i],constants.popularitemrating[i],constants.popularitemprice[i],))
        }
        binding.popularsectionrecyclerView.adapter = PopularItemsAdapter(productList)
    }


    private fun imageslider(){
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object: ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(position: Int) {
                val itempos = imageList[position]
//                val itemmessage = "Selected Item $itempos"
                Toast.makeText(requireContext(),"Selected item",Toast.LENGTH_SHORT).show()
            }
        })
    }



}