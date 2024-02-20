package es.uji.al404230.ujisubjects

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database( entities = [ Subject::class, MySubject::class, MyGroup::class],
    version = 1
)
private abstract class AbstractDatabase : RoomDatabase() {
     abstract fun getDAO() : MyDao
}

class MyDatabase private constructor(context: Context){

    val dao  = Room.databaseBuilder(context, AbstractDatabase::class.java, "subjects").build().getDAO()

    companion object: SingletonHolder<MyDatabase, Context>(::MyDatabase)
}

