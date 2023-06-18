package pl.smcebi.recipeme.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import pl.smcebi.recipeme.R
import pl.smcebi.recipeme.databinding.ActivityMainBinding.bind
import pl.smcebi.recipeme.databinding.FragmentRecipeDetailsBinding
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.notImplemented
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding

@AndroidEntryPoint
internal class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {
    private val binding by viewBinding(FragmentRecipeDetailsBinding::bind)
    private val args: RecipeDetailsFragmentArgs by navArgs()
    private var adapter: IngredientsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform()
        initViews()
    }

    private fun initViews() {
        with(binding) {
            adapter = IngredientsAdapter()
            val recipe = args.recipe
            recipeImage.load(data = recipe.imageUrl, fallback = R.drawable.recipe_placeholder)
            backButton.setSafeOnClickListener {
                findNavController().navigateUp()
            }
            saveButton.setSafeOnClickListener {
                notImplemented()
            }

            imageContainer.transitionName = recipe.imageUrl
            recipeNameTextView.text = recipe.title
            dishTypeTextView.text = buildString {  }
            durationTextView.text = buildString {  }
            descriptionTextView.text = recipe.description
            servingsTextView.text =
                getString(R.string.fragment_details_meal_servings, recipe.servings.toString())

            caloriesTextView.text
            proteinsTextView.text
            fatsTextView.text
            carbohydratesTextView.text

            ingredientsRecyclerView.adapter = adapter
            adapter?.submitList(recipe.ingredientsList)
        }
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}
