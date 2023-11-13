package pl.smcebi.recipeme.ui.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.saved.databinding.FragmentSavedRecipesBinding

@AndroidEntryPoint
internal class SavedRecipesFragment : Fragment(R.layout.fragment_saved_recipes) {

    private val binding by viewBinding(FragmentSavedRecipesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}