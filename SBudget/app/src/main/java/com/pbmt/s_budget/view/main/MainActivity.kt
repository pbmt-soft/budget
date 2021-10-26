package com.pbmt.s_budget.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.pbmt.s_budget.R
import androidx.navigation.ui.setupActionBarWithNavController
import com.pbmt.s_budget.data.local.AppDatabase
import com.pbmt.s_budget.databinding.ActivityMainBinding
import com.pbmt.s_budget.repo.TransactionRepo
import com.pbmt.s_budget.utils.viewModelFactory
import com.pbmt.s_budget.view.main.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    private lateinit var appBarConfiguration: AppBarConfiguration



    private val repo by lazy { TransactionRepo(AppDatabase(this)) }

    private val viewModel: TransactionViewModel by viewModels {

        viewModelFactory { TransactionViewModel(this.application, repo) }

    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)



        /**

         * Just so the viewModel doesn't get removed by the compiler, as it isn't used

         * anywhere here for now

         */

        viewModel



        initViews(binding)

        observeNavElements(binding, navHostFragment.navController)

    }



    private fun observeNavElements(

        binding: ActivityMainBinding,

        navController: NavController

    ) {

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {



                R.id.dashboardFragment -> {

                    supportActionBar!!.setDisplayShowTitleEnabled(false)

                }

                R.id.addTransactionFragment -> {

                    supportActionBar!!.setDisplayShowTitleEnabled(true)

                    binding.toolbar.title = "Add Transaction"

                }

                else -> {

                    supportActionBar!!.setDisplayShowTitleEnabled(true)

                }

            }

        }

    }



    private fun initViews(binding: ActivityMainBinding) {

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)



        navHostFragment = supportFragmentManager

            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

            ?: return



        with(navHostFragment.navController) {

            appBarConfiguration = AppBarConfiguration(graph)

            setupActionBarWithNavController(this, appBarConfiguration)

        }

    }



    override fun onSupportNavigateUp(): Boolean {

        navHostFragment.navController.navigateUp()

        return super.onSupportNavigateUp()

    }

}