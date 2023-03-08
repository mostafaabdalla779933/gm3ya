package com.gm3ya.gm3ya.features


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentImageBinding


class ImageFragment : BaseFragment<FragmentImageBinding, AnyViewModel>() {
    val args:ImageFragmentArgs by navArgs()
    override fun initBinding()=FragmentImageBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        Glide.with(requireContext()).load(args.url).into(binding.iv)
        binding.iv.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}