package com.wp.ayenosis.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class Detection(
    var normalPercent: Float? = null,
    var cataractPercent: Float? = null,
    var timeDate: LocalDateTime? = null

): Parcelable