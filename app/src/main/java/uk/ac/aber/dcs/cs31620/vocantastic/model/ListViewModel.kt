package uk.ac.aber.dcs.cs31620.vocantastic.model

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    var state by mutableStateOf(ListState())

    fun onAction(userAction: UserAction) {
        when(userAction) {
            UserAction.CloseIconClicked ->
                state = state.copy(
                    isSearchBarVisible = false
                )
            UserAction.SearchIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = true
                )
            }
        }
    }
    sealed class UserAction {
        object SearchIconClicked : UserAction()
        object CloseIconClicked : UserAction()
    }

    data class ListState(
        val isSearchBarVisible: Boolean = false,
        val searchText: String = "",
    )
}