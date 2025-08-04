package com.example.historialacreditacionprueba.data.dataCarreras

// Un ADT para representar Loading / Success / Error
sealed class UiState<out T> {
    object Idle    : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}