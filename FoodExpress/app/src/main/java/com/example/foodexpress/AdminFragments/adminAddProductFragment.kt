package com.example.foodexpress.adminfragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodexpress.Admin.adminmainhome
import com.example.foodexpress.R
import com.example.foodexpress.Utils
import com.example.foodexpress.ViewModel.AdminViewModel
import com.example.foodexpress.adapter.adminfoodimageadapter
import com.example.foodexpress.constants
import com.example.foodexpress.databinding.FragmentAdminAddProductBinding
import com.example.foodexpress.model.Product
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class AdminAddProductFragment : Fragment() {
    private val viewModel: AdminViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentAdminAddProductBinding
    private  val uriImages : ArrayList<Uri> = arrayListOf()

    private val selectedimages =  registerForActivityResult(ActivityResultContracts.GetMultipleContents()){
            listofUri ->
        run {

            val fiveimages = listofUri.take(5)
            if (uriImages.isNotEmpty()){
                uriImages.clear()
            }
            uriImages.addAll(fiveimages)
            binding.addedimagerecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            binding.addedimagerecyclerView.adapter = adminfoodimageadapter(uriImages)


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAdminAddProductBinding.inflate(inflater,container,false)
        onimageselected()
        onAddButtonClicked()
        setautocompletetextview()

        return binding.root
    }

    private fun fieldchecker(
        producttitle: String, productquantity: String, productunit: String, productprice: String,
        productstockno: String, productcategory: String, productdesc: String
    ) {
        

        if (producttitle.isEmpty()) {
            binding.producttitlelayout.error = "This field can't be empty"
        } else {
            binding.producttitlelayout.error = null
        }

        if (productquantity.isEmpty()) {
            binding.productquantitylayout.error = "This field can't be empty"
            
        } else {
            binding.productquantitylayout.error = null
        }

        if (productunit.isEmpty()) {
            binding.productunit.error = "This field can't be empty"
            
        } else {
            binding.productunit.error = null
        }

        if (productprice.isEmpty()) {
            binding.productpricelayout.error = "This field can't be empty"
            
        } else {
            binding.productpricelayout.error = null
        }

        if (productstockno.isEmpty()) {
            binding.productstocklayout.error = "This field can't be empty"
            
        } else {
            binding.productstocklayout.error = null
        }

        if (productcategory.isEmpty()) {
            binding.productcategorylayout.error = "This field can't be empty"
            
        } else {
            binding.productcategorylayout.error = null
        }

        if (productdesc.isEmpty()) {
            binding.productdesclayout.error = "This field can't be empty"
            
        } else {
            binding.productdesclayout.error = null
        }
    }


    private fun onimageselected() {
        binding.addimages.setOnClickListener{
           selectedimages.launch("image/*")
        }
    }

    private fun onAddButtonClicked(){
        binding.addproductbtn.setOnClickListener {
            val productTitle = binding.producttitle.text.toString()
            val productQuantity = binding.productquantity.text.toString()
            val productUnit = binding.productunit.text.toString()
            val productPrice = binding.productprice.text.toString()
            val productstock = binding.productstockno.text.toString()
            val productCategory = binding.productcategory.text.toString()
            val productDetails = binding.productdesc.text.toString()
            if (productTitle.isEmpty() || productQuantity.isEmpty() ||productUnit.isEmpty() ||productPrice.isEmpty() ||productstock.isEmpty() ||productCategory.isEmpty() ||productDetails.isEmpty()){
                fieldchecker(productTitle,productQuantity,productUnit,productPrice,productstock,productCategory,productDetails)
            }
            else if(uriImages.isEmpty()){
                Toast.makeText(requireContext(),"image field can't be empty",Toast.LENGTH_SHORT).show()
            }
            else{
                val product = Product(
                    productTitle = productTitle,
                    productQuantity = productQuantity.toInt(),
                    productUnit = productUnit,
                    productPrice = productPrice.toInt(),
                    productstock = productstock.toInt(),
                    productCategory = productCategory,
                    productDetails = productDetails,
                    itemCount = 0,
                    adminUid = Utils.getCurrentUserid(),
                    productRandomId = Utils.getRandomId()

                )
                saveImage(product)
            }
        }
    }

    private fun saveImage(product: Product) {
        Utils.showDialog(requireContext(),"Publishing Product...")
        viewModel.saveImagesinDB(uriImages)
        lifecycleScope.launch {
            viewModel.isImagesUploaded.collect{
                if(it){
                    Toast.makeText(requireContext(),"Image Saved",Toast.LENGTH_SHORT).show()
                    getUrls(product)
                }
            }
        }
    }

    private fun getUrls(product: Product) {
        lifecycleScope.launch {
            viewModel.downloadUrls.collect{
                val urls = it
                product.productimageUris = urls
                saveProdut(product)
            }
        }

    }

    private var isProductSavedHandled = false

    private fun saveProdut(product: Product) {
        viewModel.saveProduct(product)
        lifecycleScope.launch {
            viewModel.isProductSaved.collect { isSaved ->
                if (isSaved && !isProductSavedHandled) {
                    isProductSavedHandled = true
                    Toast.makeText(requireContext(), "Your Product is Live", Toast.LENGTH_SHORT).show()
                    Utils.hideDialog()
                    val intent = Intent(requireContext(), adminmainhome::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }



    private fun setautocompletetextview() {
        val unitslist = ArrayAdapter(requireContext(),R.layout.show_list,constants.units)
        val categorylist = ArrayAdapter(requireContext(),R.layout.show_list,constants.allproductcategory)

        binding.apply {
            productunit.setAdapter(unitslist)
            productcategory.setAdapter(categorylist)
        }
    }


}