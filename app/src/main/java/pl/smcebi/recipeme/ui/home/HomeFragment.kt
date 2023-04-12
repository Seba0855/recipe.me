package pl.smcebi.recipeme.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pl.smcebi.recipeme.R
import pl.smcebi.recipeme.databinding.FragmentHomeBinding
import pl.smcebi.recipeme.ui.common.viewbinding.viewBinding

internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
