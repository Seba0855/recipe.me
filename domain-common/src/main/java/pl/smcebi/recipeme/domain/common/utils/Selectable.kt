package pl.smcebi.recipeme.domain.common.utils

data class Selectable<T : Any>(
    val value: T,
    val isSelected: Boolean
) {
    companion object {
        fun <T : Any> List<T>.toSelectable(selectedIndex: Int): List<Selectable<T>> =
            mapIndexed { index, item ->
                Selectable(
                    value = item,
                    isSelected = index == selectedIndex,
                )
            }
    }
}
