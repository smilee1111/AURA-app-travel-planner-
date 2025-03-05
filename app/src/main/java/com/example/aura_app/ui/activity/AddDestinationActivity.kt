package com.example.aura_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.aura_app.R
import com.example.aura_app.model.DestinationModel
import com.example.aura_app.repository.DestinationRepositoryImpl
import java.util.concurrent.Executors

class AddDestinationActivity : AppCompatActivity() {

    private lateinit var imageBrowse: ImageView
    private lateinit var editName: EditText
    private lateinit var editPrice: EditText
    private lateinit var editDesc: EditText
    private lateinit var addButton: Button
    private var selectedImageUri: Uri? = null

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dvo87hsdg",
            "api_key" to "543825759132539",
            "api_secret" to "YaoUN2uz6A45CWG5e7OjNBvy9pA"
        )
    )

    private val destinationRepo = DestinationRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_destination)

        imageBrowse = findViewById(R.id.imagebrowse)
        editName = findViewById(R.id.editProductName)
        editPrice = findViewById(R.id.editProductPrice)
        editDesc = findViewById(R.id.editProductDesc)
        addButton = findViewById(R.id.LoginBtn)

        imageBrowse.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        addButton.setOnClickListener {
            uploadImageAndAddDestination()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data?.data != null) {
            selectedImageUri = data.data
            imageBrowse.setImageURI(selectedImageUri)
        }
    }

    private fun uploadImageAndAddDestination() {
        val name = editName.text.toString().trim()
        val price = editPrice.text.toString().trim()
        val desc = editDesc.text.toString().trim()

        if (name.isEmpty() || price.isEmpty() || desc.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            return
        }

        Executors.newSingleThreadExecutor().execute {
            try {
                val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                val uploadResult = cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap())

                val imageUrl = uploadResult["secure_url"] as String
                val destinationId = destinationRepo.ref.push().key ?: return@execute

                // ✅ Corrected field assignment
                val destination = DestinationModel(
                    destId = destinationId,
                    destName = name,
                    destDetail = desc,
                    destCost = price,
                    destImageUrl = imageUrl
                )

                runOnUiThread {
                    destinationRepo.addDestinationToDatabase(destinationId, destination) { success, message ->
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        if (success) {
                            finish() // Close activity after successful addition
                        }
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
