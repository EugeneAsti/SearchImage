package ru.aeyu.searchimagestest.ui.utils

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.iterator
import ru.aeyu.searchimagestest.R
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.enums.Countries
import ru.aeyu.searchimagestest.domain.enums.Languages
import ru.aeyu.searchimagestest.domain.enums.MenuContentSizes
import ru.aeyu.searchimagestest.domain.models.SearchFilter
import ru.aeyu.searchimagestest.ui.MainActivity

class MainMenuProvider(
    private val context: Context,
    private val filter: SearchFilter,
    private val onMenuSelected: (menuItem: MenuItem) -> Boolean,
    private val onMenuCollapsed: () -> Unit,
) : MenuProvider {

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        val searchManager =
            context.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.item_search)
        val contentTypeImage = menu.findItem(R.id.item_images)
        val contentTypeVideo = menu.findItem(R.id.item_videos)

        val anySizeItem = menu.findItem(R.id.item_size_any)
        val iconSizeItem = menu.findItem(R.id.item_size_icon)
        contentTypeImage.setOnMenuItemClickListener { _ ->
            iconSizeItem.isVisible = true
            false
        }
        contentTypeVideo.setOnMenuItemClickListener { _ ->
            iconSizeItem.isVisible = false
            anySizeItem.isChecked = true
            false
        }


        val searchView = searchItem.actionView as SearchView
        val component = ComponentName(context, MainActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(component)
        searchView.setSearchableInfo(searchableInfo)

        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                onMenuCollapsed()
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }
        }
        searchItem.setOnActionExpandListener(expandListener)
        restoreMenuState(menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return onMenuSelected(menuItem)
    }


    private fun restoreMenuState(menu: Menu) {
        val selectedContentType = filter.contentType
        val selectedContentSize = MenuContentSizes.getMenuSize(filter.contentSize)
        val selectedCountry = filter.country
        val selectedLanguage = filter.language

        for (menuItem in menu.iterator()) {
            //groups
            if (menuItem.hasSubMenu()) {
                val subMenu = menuItem.subMenu
                if (subMenu != null) {
                    for (subItem in subMenu.iterator()) {
                        when (subItem.itemId) {
                            R.id.item_images -> {
                                if (selectedContentType == ContentTypes.IMAGES)
                                    subItem.isChecked = true
                            }

                            R.id.item_videos -> {
                                if (selectedContentType == ContentTypes.VIDEOS)
                                    subItem.isChecked = true
                            }

                            R.id.item_size_any -> {
                                if (selectedContentSize == MenuContentSizes.ANY)
                                    subItem.isChecked = true
                            }

                            R.id.item_size_large -> {
                                if (selectedContentSize == MenuContentSizes.LARGE)
                                    subItem.isChecked = true
                            }

                            R.id.item_size_medium -> {
                                if (selectedContentSize == MenuContentSizes.MEDIUM)
                                    subItem.isChecked = true
                            }

                            R.id.item_size_small -> {
                                if (selectedContentSize == MenuContentSizes.SMALL)
                                    subItem.isChecked = true
                            }

                            R.id.item_size_icon -> {
                                if (selectedContentSize == MenuContentSizes.SMALL)
                                    subItem.isChecked = true
                            }

                            R.id.item_country_any -> {
                                if(selectedCountry == Countries.ANY)
                                    menuItem.isChecked = true
                            }

                            R.id.item_country_australia -> {
                                if(selectedCountry == Countries.AUSTRALIA)
                                    menuItem.isChecked = true
                            }

                            R.id.item_country_germany -> {
                                if(selectedCountry == Countries.GERMANY)
                                    menuItem.isChecked = true
                            }

                            R.id.item_country_russia -> {
                                if(selectedCountry == Countries.RUSSIA)
                                    menuItem.isChecked = true
                            }

                            R.id.item_country_spain -> {
                                if(selectedCountry == Countries.SPAIN)
                                    menuItem.isChecked = true
                            }

                            R.id.item_language_any -> {
                                if(selectedLanguage == Languages.ANY)
                                    menuItem.isChecked = true
                            }

                            R.id.item_language_german -> {
                                if(selectedLanguage == Languages.GERMAN)
                                    menuItem.isChecked = true
                            }

                            R.id.item_language_ru -> {
                                if(selectedLanguage == Languages.RUSSIAN)
                                    menuItem.isChecked = true
                            }

                            R.id.item_language_spanish -> {
                                if(selectedLanguage == Languages.SPANISH)
                                    menuItem.isChecked = true
                            }
                        }
                    }
                }
            }
        }
    }
}