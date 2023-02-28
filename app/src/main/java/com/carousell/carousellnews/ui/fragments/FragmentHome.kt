package com.carousell.carousellnews.ui.fragments

import android.view.*
import androidx.fragment.app.viewModels
import com.carousell.carousellnews.R
import com.carousell.carousellnews.databinding.DialogErrorBinding
import com.carousell.carousellnews.databinding.FragmentHomeBinding
import com.carousell.carousellnews.ui.adapters.ArticlesAdapter
import com.carousell.carousellnews.utils.toastS
import com.carousell.carousellnews.viewmodel.ArticlesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * This screen is responsible for showing home screen of the app which shows the articles fetched from API call.
 * It extends from FragmentBase which implements the function and properties of FragmentBase.
 * @author: Jagannath Acharya
 */
class FragmentHome: FragmentBase<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val adapter = ArticlesAdapter()
    private val articlesVm by viewModels<ArticlesViewModel>()

    /**
     * This function is responsible for showing lottie animation based on the error message.
     * @param: errorMessage as String, anim as Int
     * @author: Jagannath Acharya
     */
    private fun setErrorAnimation(errorMessage: String, anim: Int) {
        binding.rvArticlesList.visibility = View.GONE
        val dialogBinding = DialogErrorBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .show()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialogBinding.avError.setAnimation(anim)
        dialogBinding.tvErrorMessage.text = errorMessage
        dialogBinding.btnRetry.setOnClickListener {
            articlesVm.getArticles()
            dialog.dismiss()
        }
    }

    private fun setUpObserver() {
        articlesVm.articlesListLiveData.observe(viewLifecycleOwner) {
            binding.layoutShimmer.stopShimmer()
            binding.layoutShimmer.visibility = View.GONE
            binding.rvArticlesList.visibility = View.VISIBLE
            adapter.setItems(it)
        }
        articlesVm.error.observe(viewLifecycleOwner) { error ->
            if (error.error) {
                error.errorMessage?.let { message -> error.anim?.let { anim -> setErrorAnimation(message, anim) } }
            }
        }
    }

    /**
     * This function is responsible for setting up our UI.
     * It is an abstract function which is implemented by extending from FragmentBase.
     * @author: Jagannath Acharya
     */
    override fun setUpUi() {
        var isOpen = false
        binding.cvMore.setOnClickListener {
            isOpen = if (isOpen) {
                binding.layoutMotion.transitionToStart()
                false
            } else {
                binding.layoutMotion.transitionToEnd()
                true
            }
        }
        binding.cvPopular.setOnClickListener {
            if (adapter.list.isEmpty()) {
                context?.toastS(getString(R.string.articles_loading))
            } else {
                articlesVm.sortTheArticles(false)
                binding.layoutMotion.transitionToStart()
                isOpen = false
            }
        }
        binding.cvRecent.setOnClickListener {
            if (adapter.list.isEmpty()) {
                context?.toastS(getString(R.string.articles_loading))
            } else {
                articlesVm.sortTheArticles(true)
                binding.layoutMotion.transitionToStart()
                isOpen = false
            }
        }
        binding.rvArticlesList.adapter = adapter
        setUpObserver()
    }
}