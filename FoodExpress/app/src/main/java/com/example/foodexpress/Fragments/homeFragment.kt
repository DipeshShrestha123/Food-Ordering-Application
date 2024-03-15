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
import com.example.foodexpress.databinding.FragmentHomeBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        val foodcategoriesname = listOf("All Food","Pizza","Bakery","Sea Food")
        val foodcategoriesimg = listOf(R.drawable.allfood,R.drawable.pizza,R.drawable.cake,R.drawable.shrimp)
        val foodcategorieadapter = FoodcategoriesAdapter(foodcategoriesname,foodcategoriesimg)
        binding.foodcategorierecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        binding.foodcategorierecyclerView.adapter = foodcategorieadapter


        val popularitemname = listOf("Herbal Pancake","Salad","Ice Cream","Curry","French Fry","Kathi Roll","Oats")
        val popularitemimg = listOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,R.drawable.menu5,R.drawable.menu6,R.drawable.menu7)
        val popularitemrating = listOf("4.0","3.0","5.0","3.3","4.3","3.9","3.3")
        val popularitemprice = listOf("40","30","50","303","90","100","190")
        val popularitemadapter = PopularItemsAdapter(popularitemname,popularitemimg,popularitemrating,popularitemprice)
        binding.popularsectionrecyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        binding.popularsectionrecyclerView.adapter = popularitemadapter





    }


}