package pl.smcebi.recipeme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.extensions.disableTooltipText
import pl.smcebi.recipeme.ui.home.R.id.homeFragment
import pl.smcebi.recipeme.ui.saved.R.id.savedRecipesFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val bottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationBar()
    }

    private fun setupNavigationBar() {
        bottomNavigationView.apply {
            setupWithNavController(getNavController())
            disableTooltipText()
        }

        getNavController().addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.isVisible =
                destination.id == homeFragment || destination.id == savedRecipesFragment
        }
    }

    private fun getNavController(): NavController {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        return (fragment as NavHostFragment).navController
    }
}
