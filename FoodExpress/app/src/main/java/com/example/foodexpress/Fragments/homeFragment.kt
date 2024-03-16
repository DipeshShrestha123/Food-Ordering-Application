package com.example.foodexpress.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodexpress.R
import com.example.foodexpress.adapter.FoodcategoriesAdapter
import com.example.foodexpress.adapter.PopularItemsAdapter
import com.example.foodexpress.constants
import com.example.foodexpress.databinding.FragmentHomeBinding
import com.example.foodexpress.model.categorie

class homeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        imageslider()
        setAllCategories()
        return binding.root
    }

    private fun setAllCategories(){
        val categorieList = ArrayList<categorie>()

        for( i in 0 until constants.foodcategoriesname.size ){
            categorieList.add(categorie(constants.foodcategoriesname[i] , constants.foodcategoriesicon[i]))
        }
        binding.foodcategorierecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        binding.foodcategorierecyclerView.adapter = FoodcategoriesAdapter(categorieList, ::onCategorieIconClicked)

    }
    private fun onCategorieIconClicked(categorie: categorie){
        getCategorieProduct(categorie.foodcategoriesitems)
    }

    private fun getCategorieProduct(foodcategoriesitems: String?) {

    }

    private fun imageslider(){
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularitemname = listOf("Herbal Pancake","Salad","Ice Cream","Curry","French Fry","Kathi Roll","Oats")
        val popularitemimg = listOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,R.drawable.menu5,R.drawable.menu6,R.drawable.menu7)
        val popularitemrating = listOf("4.0","3.0","5.0","3.3","4.3","3.9","3.3")
        val popularitemprice = listOf("40","30","50","303","90","100","190")
        val popularitemadapter = PopularItemsAdapter(popularitemname,popularitemimg,popularitemrating,popularitemprice)
        binding.popularsectionrecyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        binding.popularsectionrecyclerView.adapter = popularitemadapter





    }


}