package pl.smcebi.recipeme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchView
import com.google.android.material.search.SearchView.TransitionState.SHOWING
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.BottomNavCommunicationBridge
import pl.smcebi.recipeme.ui.common.extensions.collectOnLifecycle
import pl.smcebi.recipeme.ui.common.extensions.disableTooltipText
import pl.smcebi.recipeme.ui.common.extensions.setKeyboardVisibilityListener
import pl.smcebi.recipeme.ui.home.R.id.homeFragment
import pl.smcebi.recipeme.ui.saved.R.id.savedRecipesFragment
import pl.smcebi.recipeme.ui.scanner.R.id.productListFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bottomNavCommunicationBridge: BottomNavCommunicationBridge

    private val bottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationBar()
        collectOnLifecycle(bottomNavCommunicationBridge.state, ::onNavVisibilityChanged)
    }

    private fun setupNavigationBar() {
        bottomNavigationView.apply {
            setupWithNavController(getNavController())
            disableTooltipText()
        }

        getNavController().addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.isVisible =
                destination.id == homeFragment || destination.id == savedRecipesFragment || destination.id == productListFragment
        }
    }

    private fun onNavVisibilityChanged(isVisible: Boolean) {
        bottomNavigationView.isVisible = isVisible
    }

    private fun getNavController(): NavController {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        return (fragment as NavHostFragment).navController
    }
}
