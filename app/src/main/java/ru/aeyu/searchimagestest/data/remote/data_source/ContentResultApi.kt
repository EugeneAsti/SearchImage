package ru.aeyu.searchimagestest.data.remote.data_source

import retrofit2.http.GET
import retrofit2.http.Query
import ru.aeyu.searchimagestest.BuildConfig
import ru.aeyu.searchimagestest.data.remote.model.SearchResultSerpApi
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.enums.Countries
import ru.aeyu.searchimagestest.domain.enums.ContentSizes
import ru.aeyu.searchimagestest.domain.enums.Languages

interface ContentResultApi {
        @GET("/search.json")
        fun findImages(
            @Query("q") searchText: String,
            @Query("engine") engine: String = "google_images",
            @Query("ijn") pageNumber: Int? = 0,
            @Query("hl") language: String? = Languages.ANY.code,
            @Query("gl") country: String? = Countries.ANY.code,
            @Query("device") device: String? = "mobile",
            @Query("tbm") contentType: String = ContentTypes.IMAGES.code,
            @Query("tbs") contentSize: String? = ContentSizes.IMAGE_ANY.size,
            @Query("api_key") apiKey: String? = BuildConfig.API_KEY,
        ) : SearchResultSerpApi

}