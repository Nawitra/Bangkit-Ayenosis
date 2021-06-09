package com.wp.ayenosis.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Detection(
    var normalPercent: Float? = null,
    var cataractPercent: Float? = null,
    var date: String? = null

): Parcelable