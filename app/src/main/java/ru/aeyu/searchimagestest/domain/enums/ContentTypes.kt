package ru.aeyu.searchimagestest.domain.enums

enum class ContentTypes(val code: String) {
    IMAGES("isch"),
    VIDEOS("vid");

    companion object GetTypeByCode {
        fun getTypeByCode(code: String): ContentTypes {
            return when (code) {
                "isch" -> IMAGES
                "vid" -> VIDEOS
                else -> IMAGES
            }
        }
    }
}