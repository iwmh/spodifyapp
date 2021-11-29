package com.iwmh.spodifyapp.view.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.spodifyapp.repository.MainPagingSource
import com.iwmh.spodifyapp.repository.MainRepository
import com.iwmh.spodifyapp.repository.model.ShowsFeed
import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.repository.model.api.PagingObject
import com.iwmh.spodifyapp.repository.model.api.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.openid.appauth.AuthorizationException
import okhttp3.internal.wait
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel @Inject constructor (
    private val mainPagingSource: MainPagingSource
): ViewModel() {

//    private val uiState = MutableStateFlow(LibraryViewUiState(isLoading = true))

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ){
        mainPagingSource
    }.flow.cachedIn(viewModelScope)

//    val uiState = viewModelState
//        .map { it.toUiState() }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.Eagerly,
//            viewModelState.value.toUiState()
//        )

    init {
        refreshShows()
    }

    private fun refreshShows(){
/*
        val authExceptionHandler = CoroutineExceptionHandler{ _, exception ->
            // TODO: Unconfirmed that the AuthenticationException is caught here.
            // TODO: Navigate the user to login again.
            uiState.update {
                it.copy(isLoading = false, errorMessages = listOf("An error occurred. Please try again."))
            }
        }

        uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(authExceptionHandler) {
            mainRepository.refreshTokensIfNecessary()
//            var result = mainRepository.getUsersSavedShows()

            uiState.update {
                it.copy(isLoading = false, followingShows = result.items))
            }
        }
*/


//        try {
//            viewModelScope.launch {
//                mainRepository.refreshTokensIfNecessary()
//                var result = mainRepository.getUsersSavedShows()
//                viewModelState.update {
//                    it.copy(isLoading = false, showsFeed = ShowsFeed(followingShows = result.items))
//                }
//            }
//        } catch (e: AuthorizationException){
//            // TODO: Navigate and let the user to login again.
//            var a = ""
//        } catch (e: Exception){
//            // TODO: 'Error occurred. Please try again.'
//            viewModelState.update {
//                it.copy(isLoading = false, errorMessages = listOf("An error occurred. Please try again."))
//            }
//        }
//    }
}

//sealed interface LibraryViewUiState {
//    val isLoading: Boolean
//    val errorMessages: List<String>
//    data class NoShows(
//        override  val isLoading: Boolean,
//        override val errorMessages: List<String>,
//    ): LibraryViewUiState
//
//    data class HasShows(
//        val followingShows: List<ItemShow>,
//        override  val isLoading: Boolean,
//        override val errorMessages: List<String>,
//    ): LibraryViewUiState
//}

data class LibraryViewUiState (
    val isLoading: Boolean,
    val errorMessages: List<String>,
    val followingShows: List<ItemShow>,
)


//private data class LibraryViewModelState(
//    val showsFeed: ShowsFeed = ShowsFeed(followingShows = emptyList()),
//    val isLoading: Boolean = false,
//    val errorMessages: List<String> = emptyList(),
//){
//    fun toUiState(): LibraryViewUiState =
//            LibraryViewUiState(
//                isLoading = isLoading,
//                errorMessages = errorMessages,
//                followingShows = showsFeed.followingShows
//            )

//    fun toUiState(): LibraryViewUiState =
//        if (showsFeed == null){
//            LibraryViewUiState.NoShows(
//                isLoading = isLoading,
//                errorMessages = errorMessages
//            )
//        } else {
//            LibraryViewUiState.HasShows(
//                isLoading = isLoading,
//                errorMessages = errorMessages,
//                followingShows = showsFeed.followingShows
//            )
//        }

}