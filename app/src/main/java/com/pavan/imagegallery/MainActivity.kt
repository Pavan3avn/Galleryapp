package com.pavan.imagegallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.pavan.imagegallery.Viewmodel.photoviewmodel
import com.pavan.imagegallery.adapter.galleryadapter

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var viewModel: photoviewmodel
    private lateinit var adapter: galleryadapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar:Toolbar
    private lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.searchview)



        drawerLayout = findViewById(R.id.drawerlayout)
        viewModel = ViewModelProvider(this).get(photoviewmodel::class.java)


        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toggle = ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.toolbar), R.string.open_nav, R.string.close_nav)
        toggle.setDrawerIndicatorEnabled(true)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)



        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = galleryadapter()
        recyclerView.adapter = adapter

        observeViewmodel()


        viewModel.snackbarMessage.observe(this) { message ->
            message?.let {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        }

        viewModel.loadrecentimages()



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    recyclerView.visibility = View.GONE
                    val fragment = searchfragment()

                    val bundle = Bundle()
                    bundle.putString("query", query)
                    fragment.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()

                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

    }

    private fun observeViewmodel() {
        viewModel.imagelist.observe(this) { imageList ->
            adapter.submitData(lifecycle, imageList)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        recyclerView.visibility = View.VISIBLE
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}
