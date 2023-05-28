package pl.smcebi.recipeme.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.R
import pl.smcebi.recipeme.databinding.ActivityMainBinding.bind
import pl.smcebi.recipeme.databinding.FragmentRecipeDetailsBinding
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding

@AndroidEntryPoint
internal class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {
    private val binding by viewBinding(FragmentRecipeDetailsBinding::bind)
    private val args: RecipeDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform()
        initViews()
    }

    private fun initViews() {
        with(binding) {
            val recipe = args.recipe
            recipeImage.load(recipe.imageUrl)
            imageContainer.transitionName = recipe.imageUrl
            recipeNameTextView.text = recipe.title
        }
    }
}
