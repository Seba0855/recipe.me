package pl.smcebi.recipeme.ui.common.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private class FragmentViewBindingDelegate<T : ViewBinding>(
    private val binder: (View) -> T,
    fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { _viewLifecycleOwner ->
                    _viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding == null) binding = binder(thisRef.requireView())
        return binding!!
    }
}

fun <T : ViewBinding> Fragment.viewBinding(binder: (View) -> T): ReadOnlyProperty<Fragment, T> =
    FragmentViewBindingDelegate(binder, this)
