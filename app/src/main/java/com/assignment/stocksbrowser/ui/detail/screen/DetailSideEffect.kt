package com.assignment.stocksbrowser.ui.detail.screen

sealed class DetailSideEffect {
    object NavigateHome : DetailSideEffect()
    object NetworkError : DetailSideEffect()
}
