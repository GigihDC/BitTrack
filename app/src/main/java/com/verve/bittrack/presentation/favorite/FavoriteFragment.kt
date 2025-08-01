package com.verve.bittrack.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.verve.bittrack.data.model.Favorite
import com.verve.bittrack.databinding.FragmentFavoriteBinding
import com.verve.bittrack.presentation.favorite.adapter.FavoriteListAdapter
import com.verve.bittrack.presentation.favorite.adapter.FavoriteListener
import com.verve.bittrack.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModel()
    private val adapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter(
            object : FavoriteListener {
                override fun onRemoveCartClicked(cart: Favorite) {
                    viewModel.removeFavorite(cart)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
//      if have some button
    }

    private fun setupList() {
        binding.rvFavorite.itemAnimator = null
        binding.rvFavorite.adapter = adapter
    }

    private fun observeData() {
        viewModel.getAllFavorites().observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.rvFavorite.isVisible = true
                result.payload?.let { (cart, _) ->
                    adapter.submitData(cart)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvFavorite.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.rvFavorite.isVisible = false
            }, doOnEmpty = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.rvFavorite.isVisible = false
            })
        }
    }
}
