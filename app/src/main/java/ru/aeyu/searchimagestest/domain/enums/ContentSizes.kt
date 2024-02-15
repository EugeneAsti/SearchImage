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
    VIDEO_SMALL("dur:s");

    companion object GetCorrectSizeByType {
        fun getSize(contentType: ContentTypes, menuContentSize: MenuContentSizes): ContentSizes {

            val contentSizeImages = when (menuContentSize) {
                MenuContentSizes.ANY -> IMAGE_ANY
                MenuContentSizes.LARGE -> IMAGE_LARGE
                MenuContentSizes.MEDIUM -> IMAGE_MEDIUM
                MenuContentSizes.SMALL -> IMAGE_SMALL
                MenuContentSizes.ICON -> IMAGE_ICON
            }
            val contentSizeVideos = when (menuContentSize) {
                MenuContentSizes.ANY -> VIDEO_ANY
                MenuContentSizes.LARGE -> VIDEO_LARGE
                MenuContentSizes.MEDIUM -> VIDEO_MEDIUM
                MenuContentSizes.SMALL -> VIDEO_SMALL
                MenuContentSizes.ICON -> VIDEO_SMALL
            }
            return if (contentType == ContentTypes.IMAGES) {
                contentSizeImages
            } else
                contentSizeVideos
        }
    }
}

enum class MenuContentSizes {
    ANY,
    LARGE,
    MEDIUM,
    SMALL,
    ICON;

    companion object GetCorrectMenuSizeByType {
        fun getMenuSize(contentSize: ContentSizes): MenuContentSizes {
            return when (contentSize) {
                ContentSizes.IMAGE_ANY, ContentSizes.VIDEO_ANY -> ANY
                ContentSizes.IMAGE_LARGE, ContentSizes.VIDEO_LARGE -> LARGE
                ContentSizes.IMAGE_MEDIUM, ContentSizes.VIDEO_MEDIUM -> MEDIUM
                ContentSizes.IMAGE_SMALL, ContentSizes.VIDEO_SMALL -> SMALL
                ContentSizes.IMAGE_ICON -> ICON
            }
        }
    }
}