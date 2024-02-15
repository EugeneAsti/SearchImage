package ru.aeyu.searchimagestest.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.aeyu.searchimagestest.R
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.enums.Countries
import ru.aeyu.searchimagestest.domain.enums.Languages
import ru.aeyu.searchimagestest.domain.enums.MenuContentSizes
import ru.aeyu.searchimagestest.ui.main.MainViewModel
import ru.aeyu.searchimagestest.ui.utils.MainMenuProvider

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var menuHost: MenuHost
    private lateinit var myMenuProvider: MainMenuProvider
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        handleIntent(intent)
        menuHost = this
        myMenuProvider = MainMenuProvider(this, viewModel.currentSearchFilter, onMenuItemSelected) {
            invalidateOptionsMenu()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> menuHost.addMenuProvider(myMenuProvider, this)
                else -> menuHost.removeMenuProvider(myMenuProvider)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.i("!!!###!!!", "Search query was: $query")
            viewModel.onNewSearchQuery(query)
        }
    }

    private val onMenuItemSelected: (menuItem: MenuItem) -> Boolean = { menuItem ->
        when (menuItem.itemId) {
            R.id.item_images -> {
                menuItem.isChecked = true
                viewModel.onContentTypeChange(ContentTypes.IMAGES)
                true
            }

            R.id.item_videos -> {
                menuItem.isChecked = true
                viewModel.onContentTypeChange(ContentTypes.VIDEOS)
                true
            }

            R.id.item_size_any -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.ANY)
                true
            }

            R.id.item_size_large -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.LARGE)
                true
            }

            R.id.item_size_medium -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.MEDIUM)
                true
            }

            R.id.item_size_small -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.SMALL)
                true
            }

            R.id.item_size_icon -> {
                menuItem.isChecked = true
                viewModel.onContentSizeChange(MenuContentSizes.ICON)
                true
            }

            R.id.item_country_any -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.ANY)
                true
            }

            R.id.item_country_australia -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.AUSTRALIA)
                true
            }

            R.id.item_country_germany -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.GERMANY)
                true
            }

            R.id.item_country_russia -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.RUSSIA)
                true
            }

            R.id.item_country_spain -> {
                menuItem.isChecked = true
                viewModel.onCountryResultChange(Countries.SPAIN)
                true
            }

            R.id.item_language_any -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.ANY)
                true
            }

            R.id.item_language_german -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.GERMAN)
                true
            }

            R.id.item_language_ru -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.RUSSIAN)
                true
            }

            R.id.item_language_spanish -> {
                menuItem.isChecked = true
                viewModel.onLanguageResultChange(Languages.SPANISH)
                true
            }

            else -> false
        }
    }
}