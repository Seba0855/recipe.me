package pl.smcebi.recipeme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationBar()
    }

    private fun setupNavigationBar() {
        with(findViewById<BottomNavigationView>(R.id.bottomNavigationBar)) {
            setupWithNavController(getNavController())
        }
    }

    private fun getNavController(): NavController {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        return (fragment as NavHostFragment).navController
    }
}
