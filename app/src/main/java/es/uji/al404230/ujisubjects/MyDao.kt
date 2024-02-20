package es.uji.al404230.ujisubjects

import androidx.room.*

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertMyGroup(myGroup: MyGroup)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun InsertMySubject(mySubject: MySubject)

    //mirar
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun InsertSubject(subject: Subject)


    @Delete
    fun DeleteMyGroup(myGroup: MyGroup)
    @Delete
    fun DeleteMySubject(mySubject : MySubject)


    //A query to recover all the subjects.
    @Query("SELECT * FROM subject ")
    fun GetAllSubjects() : List<Subject>

    //A query to recover all the subjects that have a given prefix in the code.
     @Query("SELECT * FROM subject WHERE code LIKE '%' || :prefix || '%' ORDER BY code")
     fun GetAllSubjectByPrefix(prefix : String) : List<Subject>

     //A query to get all the subjects selected by the user.
     @Query("SELECT * FROM my_subject LEFT JOIN subject on my_subject.my_subject_code == subject.code ORDER BY subject.code")
     fun GetAllMySubjects() : List<Subject>

     //A query to know if a given subject is selected by the user. >0 =true
     @Query("SELECT COUNT(*) FROM  my_subject  WHERE my_subject.my_subject_code == :code")
     fun KnowIfMySubject(code : String) : Int

     // A query to get all the groups of a given subject
     @Query("SELECT * FROM my_group WHERE group_subject_code == :code ORDER BY group_subject_code")
     fun GetAllGoups(code : String) : List<MyGroup> //lo he metido que le den el codigo, pero quizas es más util con la subject de la clase subject


}