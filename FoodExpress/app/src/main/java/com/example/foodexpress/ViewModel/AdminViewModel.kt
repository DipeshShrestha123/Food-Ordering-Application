package com.example.foodexpress.ViewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.foodexpress.Utils
import com.example.foodexpress.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID


class AdminViewModel : ViewModel(){

    private val _isImagesUploaded = MutableStateFlow(false)
    var isImagesUploaded: StateFlow<Boolean> = _isImagesUploaded

    private val _downloadUrls = MutableStateFlow<ArrayList<String?>>(arrayListOf())
    var downloadUrls :StateFlow<ArrayList<String?>> = _downloadUrls

    private val _isProductSaved = MutableStateFlow(false)
    var isProductSaved : StateFlow<Boolean> = _isProductSaved

    fun saveImagesinDB( imageuri : ArrayList<Uri>){
        val downloadUris = ArrayList<String?>()

        imageuri.forEach{uri ->
            val imageRef = FirebaseStorage.getInstance().reference.child(Utils.getCurrentUserid().toString()).child("images")
                .child(UUID.randomUUID().toString())
            imageRef.putFile(uri).continueWithTask{
                imageRef.downloadUrl
            }.addOnCompleteListener {
                val url = it.result
                downloadUris.add(url.toString())

                if (downloadUris.size == imageuri.size){
                    _isImagesUploaded.value = true
                    _downloadUrls.value = downloadUris
                }
            }

        }
    }

    fun saveProduct(product: Product) {
        FirebaseDatabase.getInstance().getReference("Admins")
            .child("AllProducts/${product.productRandomId}").setValue(product)
            .addOnSuccessListener {
                FirebaseDatabase.getInstance().getReference("Admins")
                    .child("FoodCategories/${product.productRandomId}").setValue(product.productCategory)
                    .addOnSuccessListener {
                        if (!_isProductSaved.value) { // Check if it's not already set to true
                            _isProductSaved.value = true
                        }
                        else{
                            _isProductSaved.value = false
                        }
                    }
            }
            .addOnFailureListener {
                _isProductSaved.value = false
            }
    }

    fun fetchAllProduct(foodcategoriesitems: String?): Flow<List<Product>> = callbackFlow {
        val db = FirebaseDatabase.getInstance().getReference("Admins").child("AllProducts")

        val EventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val products = ArrayList<Product>()
            for (product in snapshot.children){
                val prod = product.getValue(Product::class.java)
                if (foodcategoriesitems == "All Food" ||  prod?.productCategory == foodcategoriesitems){
                    products.add(prod!!)
                }
            }
            trySend(products)
        }
        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }
        db.addValueEventListener(EventListener)
        awaitClose { db.removeEventListener(EventListener) }

    }

}