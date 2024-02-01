package ru.aeyu.searchimagestest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import ru.aeyu.searchimagestest.R
import ru.aeyu.searchimagestest.ui.base.BaseFragment
import ru.aeyu.searchimagestest.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {


    override val viewModel: MainViewModel by activityViewModels()
    private lateinit var menuHost: MenuHost
    override fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

    override fun showProgressIndicator(isLoading: Boolean) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuHost = requireActivity()
        menuHost.addMenuProvider(myMenuProvider, requireActivity())
    }

    private val myMenuProvider = object : MenuProvider{
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.tool_bar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

            return when(menuItem.itemId){
                R.id.item_search -> {
                    printLog("on menu item selected")
                    true
                }
                else -> false
            }
        }

    }
}