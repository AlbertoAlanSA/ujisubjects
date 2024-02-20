package es.uji.al404230.ujisubjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

//TABLA MYGROUP
@Entity(
    tableName = "my_group",
    foreignKeys = [ForeignKey(
        entity = MySubject::class,
        parentColumns = ["my_subject_code"],
        childColumns = ["group_subject_code"]
    )],
    //indices = [Index(value = ["group_subject_code"])],
    primaryKeys = ["group_subject_code", "group_name", "room"]
)
data class MyGroup(
    @ColumnInfo(name = "group_subject_code") val groupSubjectCode: String,
    @ColumnInfo(name = "group_name") val groupName: String,
    val room: String,
    var selected: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(groupSubjectCode)
        parcel.writeString(room)
        parcel.writeByte(if (selected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyGroup> {
        override fun createFromParcel(parcel: Parcel): MyGroup {
            return MyGroup(parcel)
        }

        override fun newArray(size: Int): Array<MyGroup?> {
            return arrayOfNulls(size)
        }
    }
}