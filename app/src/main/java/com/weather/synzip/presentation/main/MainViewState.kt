package com.weather.synzip.presentation.main

data class MainViewState(val data: List<String> = emptyList(), val error: Exception? = null)