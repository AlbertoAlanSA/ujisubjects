package es.uji.al404230.ujisubjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "my_subject",
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["code"],
            childColumns = ["my_subject_code"]
        )]
)
data class MySubject(
    @PrimaryKey @ColumnInfo(name = "my_subject_code") val mySubjectCode: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mySubjectCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MySubject> {
        override fun createFromParcel(parcel: Parcel): MySubject {
            return MySubject(parcel)
        }

        override fun newArray(size: Int): Array<MySubject?> {
            return arrayOfNulls(size)
        }
    }

}