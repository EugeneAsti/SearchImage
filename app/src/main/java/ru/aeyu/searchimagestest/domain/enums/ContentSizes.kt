package ru.aeyu.searchimagestest.domain.enums

enum class ContentSizes(val size: String) {
    IMAGE_ANY(""),
    IMAGE_LARGE("isz:l"),
    IMAGE_MEDIUM("isz:m"),
    IMAGE_SMALL("isz:s"),
    IMAGE_ICON("isz:i"),

    VIDEO_ANY(""),
    VIDEO_LARGE("dur:l"),
    VIDEO_MEDIUM("dur:m"),
    VIDEO_SMALL("dur:s"),
}