package com.gm3ya.gm3ya.features.allassociations

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentAllAssociationsBinding
import com.gm3ya.gm3ya.features.allaccounts.AllAccountsAdapter

class AllAssociationsFragment : BaseFragment<FragmentAllAssociationsBinding, AnyViewModel>() {
    private var associations: List<AssociationModel>? = null

    private val adapter: AllAssociationsAdapter by lazy {
        AllAssociationsAdapter { association ->
            findNavController().navigate(AllAssociationsFragmentDirections.actionAllAssociationsFragmentToAssociationsDetailsFragment(association))
        }
    }

    override fun initBinding() = FragmentAllAssociationsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        setNavigationButton()
        binding.rvAssociations.adapter = adapter
        showLoading()
        getAllAssociations()
        binding.etSearchAssociationName.addTextChangedListener(textWatcher)
    }

    private fun setNavigationButton() {
        binding.toolbarAllAssociationsFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getAllAssociations() {
        FirebaseHelp.getAllObjects<AssociationModel>(FirebaseHelp.ASSOCIATION,{ allAssociations ->
            associations = allAssociations
            hideLoading()
            associations?.let {
                adapter.submitList(associations)
            }
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            Log.d("onTextChanged", binding.etSearchAssociationName.text.toString())
            if (binding.etSearchAssociationName.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                val searchedAssociations = associations?.filter { it.name!!.lowercase().contains(binding.etSearchAssociationName.text.toString().lowercase()) }
                adapter.submitList(searchedAssociations)
            } else {
                adapter.submitList(associations)
                binding.rvAssociations.layoutManager?.scrollToPosition(0)
            }
        }
        override fun afterTextChanged(s: Editable) {}
    }
}