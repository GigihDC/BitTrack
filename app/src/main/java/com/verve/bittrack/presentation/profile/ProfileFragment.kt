package com.verve.bittrack.presentation.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.verve.bittrack.R
import com.verve.bittrack.databinding.FragmentProfileBinding
import com.verve.bittrack.presentation.login.LoginActivity
import com.verve.bittrack.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var count: Int = 0
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        getProfileData()
        changeEditMode()
    }

    private fun getProfileData() {
        profileViewModel.getCurrentUser()?.let {
            binding.tvName.setText(it.fullName)
            binding.tvEmail.setText(it.email)
        }
    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            count += 1
            profileViewModel.changeEditMode()
            if (count % 2 == 0) {
                val name = binding.tvName.text.toString().trim()
                binding.btnEdit.text = getString(R.string.text_edit_profile)
                changeProfileName(name)
            } else {
                binding.btnEdit.text = getString(R.string.text_save)
            }
        }

        binding.btnLogout.setOnClickListener {
            logoutUser()
        }

        binding.btnChangePw.setOnClickListener {
            changePasswordUser()
        }
    }

    private fun changeEditMode() {
        profileViewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.tvName.isEnabled = it
            binding.tvEmail.isEnabled = it
        }
    }

    private fun changePasswordUser() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog_change_password)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        profileViewModel.changePassword()
        val backBtn: Button = dialog.findViewById(R.id.btn_back)
        backBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun changeProfileName(fullName: String) {
        profileViewModel.changeProfile(fullName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_link_edit_profile_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                    profileViewModel.changeEditMode()
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_link_edit_profile_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun logoutUser() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog_logout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel_dialog)
        val btnLogout = dialog.findViewById<Button>(R.id.btn_logout_dialog)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnLogout.setOnClickListener {
            dialog.dismiss()
            profileViewModel.doLogout()
            navigateToLogin()
        }
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}
