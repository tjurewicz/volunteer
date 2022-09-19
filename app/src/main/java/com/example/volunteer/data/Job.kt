import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    val id: Int,
    val day: String,
    val time: String,
    var isUserAttending: Boolean
) : Parcelable