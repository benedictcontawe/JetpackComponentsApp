package com.example.jetpackcompose

/**
 * Represents a paged response containing a list of items of type [T].
 *
 * @param data List of items for the current page.
 * @param total Total number of items across all pages.
 * @param page Number representing the current page.
 */
data class PagedResponse<T> (
    val data : List<T>,
    val total : Int,
    val page : Int,
)