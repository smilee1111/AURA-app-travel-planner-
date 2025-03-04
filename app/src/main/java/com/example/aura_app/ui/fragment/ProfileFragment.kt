package com.example.aura_app.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura_app.R
import com.example.aura_app.getRealPathFromUri
import com.example.aura_app.repository.ProfileRepositoryImpl
import com.example.aura_app.repository.UserRepositoryImpl
import com.example.aura_app.ui.activity.LoginActivity
import com.example.aura_app.ui.adapter.ProfileAdapter
import com.example.aura_app.ui.viewmodel.ProfileViewModel
import com.example.aura_app.viewModel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var profileImageView: ImageView
    private lateinit var coverImageView: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var postsTextView: TextView  // NEW - To display post count
    private lateinit var logoutBtn: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize ProfileViewModel
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Initialize UserViewModel using custom factory
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    return UserViewModel(UserRepositoryImpl()) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        recyclerView = view.findViewById(R.id.ProfileRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        profileAdapter = ProfileAdapter(emptyList())
        recyclerView.adapter = profileAdapter

        profileImageView = view.findViewById(R.id.circularImg)
        coverImageView = view.findViewById(R.id.imageView6)
        usernameTextView = view.findViewById(R.id.textView16)
        postsTextView = view.findViewById(R.id.textView15)
        logoutBtn= view.findViewById(R.id.logoutBtn) // NEW - TextView for post count

        // Observe profile data
        profileViewModel.profile.observe(viewLifecycleOwner) { profile ->
            profileAdapter.updateData(profile.highlights)

            if (profile.profileImageUrl.isNotEmpty()) {
                Picasso.get().load(profile.profileImageUrl)
                    .error(R.drawable.imageplaceholder)
                    .into(profileImageView)
            }
            if (profile.coverImageUrl.isNotEmpty()) {
                Picasso.get().load(profile.coverImageUrl)
                    .error(R.drawable.imageplaceholder)
                    .into(coverImageView)
            }
            usernameTextView.text = "${profile.username}"
            postsTextView.text ="${profile.posts}" // Display post count
        }

        profileImageView.setOnClickListener { showImageOptions("profile", profileImageView) }
        coverImageView.setOnClickListener { showImageOptions("cover", coverImageView) }
        view.findViewById<FloatingActionButton>(R.id.addHighlightFab)?.setOnClickListener {
            selectImage("highlight")
        }
        logoutBtn.setOnClickListener {
            userViewModel.logout{ success, message ->
                // Handle the logout result here
                if (success) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    // Handle failure (e.g., show a toast or log the error message)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    private fun showImageOptions(type: String, view: ImageView) {
        val profile = profileViewModel.profile.value ?: return
        val hasImage = if (type == "profile") profile.profileImageUrl.isNotEmpty() else profile.coverImageUrl.isNotEmpty()

        if (hasImage) {
            Toast.makeText(context, "Click again to delete or long press to update $type", Toast.LENGTH_SHORT).show()
            view.setOnClickListener {
                if (type == "profile") profileViewModel.deleteProfileImage() else profileViewModel.deleteCoverImage()
                view.setOnClickListener { showImageOptions(type, view) }
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
                val imagePath = getRealPathFromUri(requireContext(), uri)
                if (imagePath.isNullOrEmpty()) {
                    Toast.makeText(context, "Error: Invalid image path", Toast.LENGTH_SHORT).show()
                    return
                }
                when (requestCode) {
                    PICK_PROFILE_REQUEST -> {
                        (profileViewModel.profileRepository as? ProfileRepositoryImpl)?.uploadProfileImage(imagePath)
                    }
                    PICK_COVER_REQUEST -> {
                        (profileViewModel.profileRepository as? ProfileRepositoryImpl)?.uploadCoverImage(imagePath)
                    }
                    PICK_HIGHLIGHT_REQUEST -> {
                        (profileViewModel.profileRepository as? ProfileRepositoryImpl)?.uploadHighlight(imagePath)
                    }
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
