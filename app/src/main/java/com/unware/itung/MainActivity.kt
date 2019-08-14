package com.unware.itung

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.*
import com.unware.itung.libwrapper.Logger
import com.unware.itung.ui.extension.setupWithNavController
import android.os.Build
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private var currentNavController: LiveData<NavController>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.systemUiVisibility =
                // Tells the system that the window wishes the content to
                // be laid out at the most extreme scenario. See the docs for
                // more information on the specifics
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    // Tells the system that the window wishes the content to
                    // be laid out as if the navigation bar was hidden
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        // In Activity's onCreate() for instance

        setupToolbar()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar_activity_main)
    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser != null) {
            setupLoggedUser(currentUser)
        } else {
            showLoginScreen()
        }
    }

    private fun showLoginScreen() {

        Logger.warn("User need to login/register")

        // TODO TEST REGISTER
        firebaseAuth.createUserWithEmailAndPassword("anwargoog@gmail.com", "123456@7###")
            .addOnCompleteListener {
                taskResult ->
                if (taskResult.isSuccessful) {
                    Logger.info("User created. Yey!")
                    if (firebaseAuth.currentUser is FirebaseUser)
                        setupLoggedUser(firebaseAuth.currentUser as FirebaseUser)
                }
            }
            .addOnFailureListener {
                taskFailure ->
                Logger.error("Register failed: $taskFailure")
                Logger.error("Error msg: ${taskFailure.message}")

                if (taskFailure.cause is FirebaseAuthUserCollisionException) {
                    Logger.error("FirebaseAuthUserCollisionException")
                } else {
                    Logger.error("Other error msg: ${taskFailure.message}")
                }
            }

    }

    private fun setupLoggedUser(user: FirebaseUser) {

        // Name, email address, and profile photo Url
        val name = user.displayName
        val email = user.email
        val photoUrl = user.photoUrl

        // Check if user's email is verified
        val emailVerified = user.isEmailVerified

        // The user's ID, unique to the Firebase project. Do NOT use this value to
        // authenticate with your backend server, if you have one. Use
        // FirebaseUser.getIdToken() instead.
        val uid = user.uid

        Logger.info("User name: $name, email: $email, photoUrl: $photoUrl, emailVerified: $emailVerified, uid: $uid")

    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navGraphIds = listOf(
            R.navigation.navigation_home,
            R.navigation.navigation_dashboard,
            R.navigation.navigation_notifications
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            // setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_login -> {
                Logger.info("Login yelloww")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
