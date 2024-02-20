package es.uji.al404230.ujisubjects


interface MySubjectsInterface {
    fun showSubjectDetails(subject: Subject, groups : List<MyGroup>)
    fun showSubjects (subjects: List<Subject>)
    fun showMessage(error: String)

    fun toAllSubjects ()

    fun toUpdate ()
}
