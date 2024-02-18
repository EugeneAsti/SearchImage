package ru.aeyu.searchimagestest.ui.images_carousel

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import ru.aeyu.searchimagestest.R

class ImagesMenuProvider(
    private val context: Context,
    private val onMenuSelected: (menuItem: MenuItem) -> Boolean,
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.images_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return onMenuSelected(menuItem)
    }
}