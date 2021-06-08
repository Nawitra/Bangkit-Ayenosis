package com.wp.ayenosis.model

import android.os.Parcelable
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class Detection(
    var normalPercent: Float? = null,
    var cataractPercent: Float? = null,
    var timeDate: String? = null

): Parcelable