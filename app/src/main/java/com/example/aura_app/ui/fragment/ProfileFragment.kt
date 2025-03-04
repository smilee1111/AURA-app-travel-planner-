package com.example.aura_app.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.getRealPathFromUri
import com.example.aura_app.repository.ProfileRepositoryImpl
import com.example.aura_app.ui.adapter.ProfileAdapter
import com.example.aura_app.ui.viewmodel.ProfileViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var viewModel: ProfileViewModel
    private lateinit var profileImageView: ImageView
    private lateinit var coverImageView: ImageView
    private lateinit var usernameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        recyclerView = view.findViewById(R.id.ProfileRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        profileAdapter = ProfileAdapter(emptyList())
        recyclerView.adapter = profileAdapter

        profileImageView = view.findViewById(R.id.circularImg)
        coverImageView = view.findViewById(R.id.imageView6)
        usernameTextView = view.findViewById(R.id.textView16)

        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            android.util.Log.d("ProfileFragment", "Profile observed: $profile")
            profileAdapter.updateData(profile.highlights)
            if (profile.profileImageUrl.isNotEmpty()) {
                android.util.Log.d("ProfileFragment", "Loading profile image: ${profile.profileImageUrl}")
                Picasso.get()
                    .load(profile.profileImageUrl)
                    .error(R.drawable.imageplaceholder) // Ensure you have a placeholder drawable
                    .into(profileImageView)
            }
            if (profile.coverImageUrl.isNotEmpty()) {
                android.util.Log.d("ProfileFragment", "Loading cover image: ${profile.coverImageUrl}")
                Picasso.get()
                    .load(profile.coverImageUrl)
                    .error(R.drawable.imageplaceholder)
                    .into(coverImageView)
            }
            usernameTextView.text = profile.username
        }

        profileImageView.setOnClickListener { showImageOptions("profile", profileImageView) }
        coverImageView.setOnClickListener { showImageOptions("cover", coverImageView) }
        view.findViewById<FloatingActionButton>(R.id.addHighlightFab)?.setOnClickListener {
            selectImage("highlight")
        }

        return view
    }

    private fun showImageOptions(type: String, view: ImageView) {
        val profile = viewModel.profile.value ?: return
        val hasImage = if (type == "profile") profile.profileImageUrl.isNotEmpty() else profile.coverImageUrl.isNotEmpty()

        if (hasImage) {
            Toast.makeText(context, "Click again to delete or long press to update $type", Toast.LENGTH_SHORT).show()
            view.setOnClickListener {
                if (type == "profile") viewModel.deleteProfileImage() else viewModel.deleteCoverImage()
                view.setOnClickListener { showImageOptions(type, view) } // Reset listener
            }
            view.setOnLongClickListener {
                selectImage(type)
                true
            }
        } else {
            selectImage(type)
        }
    }

    private fun selectImage(type: String) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, when (type) {
            "profile" -> PICK_PROFILE_REQUEST
            "cover" -> PICK_COVER_REQUEST
            else -> PICK_HIGHLIGHT_REQUEST
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            data.data?.let { uri ->
                val imagePath = getRealPathFromUri(requireContext(), uri) ?: return@let
                when (requestCode) {
                    PICK_PROFILE_REQUEST -> (viewModel.profileRepository as? ProfileRepositoryImpl)?.uploadProfileImage(imagePath)
                    PICK_COVER_REQUEST -> (viewModel.profileRepository as? ProfileRepositoryImpl)?.uploadCoverImage(imagePath)
                    PICK_HIGHLIGHT_REQUEST -> (viewModel.profileRepository as? ProfileRepositoryImpl)?.uploadHighlight(imagePath)
                }
                Toast.makeText(context, "Uploading image...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PICK_PROFILE_REQUEST = 1
        private const val PICK_COVER_REQUEST = 2
        private const val PICK_HIGHLIGHT_REQUEST = 3
    }
}