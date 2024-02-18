package ru.aeyu.searchimagestest.domain.models

import android.os.Parcel
import android.os.Parcelable

data class ContentItemDomain(
    val position: Int,
    val thumbnail: String,
    val relatedContentId: String,
    val serpapiRelatedContentLink: String,
    val source: String,
    val sourceLogo: String,
    val title: String,
    val link: String,
    val tag: String,
    val original: String,
    val originalWidth: Int,
    val originalHeight: Int,
    val isProduct: Boolean,
): Parcelable{
    constructor(parcel: Parcel) : this(
        position = parcel.readInt(),
        thumbnail = parcel.readString() ?: "",
        relatedContentId = parcel.readString() ?: "",
        serpapiRelatedContentLink = parcel.readString() ?: "",
        source = parcel.readString() ?: "",
        sourceLogo = parcel.readString() ?: "",
        title = parcel.readString() ?: "",
        link = parcel.readString() ?: "",
        tag = parcel.readString() ?: "",
        original = parcel.readString() ?: "",
        originalWidth = parcel.readInt(),
        originalHeight = parcel.readInt(),
        isProduct = parcel.readByte() != 0.toByte()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(position)
        parcel.writeString(thumbnail)
        parcel.writeString(relatedContentId)
        parcel.writeString(serpapiRelatedContentLink)
        parcel.writeString(source)
        parcel.writeString(sourceLogo)
        parcel.writeString(title)
        parcel.writeString(link)
        parcel.writeString(tag)
        parcel.writeString(original)
        parcel.writeInt(originalWidth)
        parcel.writeInt(originalHeight)
        parcel.writeByte(if (isProduct) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContentItemDomain> {
        override fun createFromParcel(parcel: Parcel): ContentItemDomain {
            return ContentItemDomain(parcel)
        }

        override fun newArray(size: Int): Array<ContentItemDomain?> {
            return arrayOfNulls(size)
        }
    }

}