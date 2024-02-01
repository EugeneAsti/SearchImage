package ru.aeyu.searchimagestest.domain.models

data class SearchQuery(
    val searchText: String,
    val searchFilter: SearchFilter
)