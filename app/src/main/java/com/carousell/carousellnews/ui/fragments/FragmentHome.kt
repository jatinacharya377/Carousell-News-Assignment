package com.carousell.carousellnews.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.carousell.carousellnews.R
import com.carousell.carousellnews.databinding.DialogErrorBinding
import com.carousell.carousellnews.databinding.FragmentHomeBinding
import com.carousell.carousellnews.ui.adapters.ArticlesAdapter
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
        binding.layoutShimmer.startShimmer()
        binding.layoutShimmer.visibility = View.VISIBLE
        binding.rvArticlesList.visibility = View.GONE
        val dialogBinding = DialogErrorBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .show()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogBinding.avError.setAnimation(anim)
        dialogBinding.tvErrorMessage.text = errorMessage
        dialogBinding.btnRetry.setOnClickListener {
            articlesVm.getArticles()
        }
    }

    private fun setUpObserver() {
        articlesVm.articlesListLiveData.observe(viewLifecycleOwner) {
            binding.layoutShimmer.stopShimmer()
            binding.layoutShimmer.visibility = View.GONE
            adapter.setItems(it)
        }
        articlesVm.error.observe(viewLifecycleOwner) { error ->
            if (error.error) {
                when (error.errorMessage) {
                    getString(R.string.bad_request_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_400)
                    getString(R.string.unauthorized_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_401)
                    getString(R.string.request_not_found_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_404)
                    getString(R.string.internal_server_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_500)
                    getString(R.string.bad_data_received_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_bad_data)
                    getString(R.string.no_internet_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_no_internet)
                    getString(R.string.slow_internet_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_slow_internet)
                    getString(R.string.request_timeout_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_request_timeout)
                    getString(R.string.list_is_empty_error) -> setErrorAnimation(getString(R.string.list_is_empty_error), R.raw.anim_data_not_found)
                    getString(R.string.something_went_wrong_error) -> setErrorAnimation(error.errorMessage, R.raw.anim_something_went_wrong)
                }
            }
        }
    }

    /**
     * This function is responsible for creating the views once they are properly initialized.
     */
    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home_screen, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_popular -> Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show()
                    R.id.item_recent -> {}
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }*/

    /**
     * This function is responsible for setting up our UI.
     * It is an abstract function which is implemented by extending from FragmentBase.
     * @author: Jagannath Acharya
     */
    override fun setUpUi() {
        binding.rvArticlesList.adapter = adapter
        setUpObserver()
    }
}