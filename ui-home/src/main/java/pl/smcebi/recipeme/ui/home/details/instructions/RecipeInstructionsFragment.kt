package pl.smcebi.recipeme.ui.home.details.instructions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.FragmentRecipeInstructionsBinding

@AndroidEntryPoint
internal class RecipeInstructionsFragment : Fragment(R.layout.fragment_recipe_instructions) {

    private val binding by viewBinding(FragmentRecipeInstructionsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
        initViews()
    }

    private fun initViews() {
        with(binding) {

        }
    }
}