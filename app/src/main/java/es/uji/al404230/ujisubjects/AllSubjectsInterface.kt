package es.uji.al404230.ujisubjects

interface AllSubjectsInterface {
    fun getSubjects(subjects: List<Subject>){}
    fun showMessage(message : String)

    fun showSubjects (subjects: List<Subject>)

    fun toUpdate(subject : Subject)

}

