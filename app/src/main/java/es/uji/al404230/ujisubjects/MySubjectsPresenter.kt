package es.uji.al404230.ujisubjects

class MySubjectsPresenter(private val model: MainModel, private val view: MySubjectsActivity) {

    init {
        getMySubjects()
    }
    private fun getMySubjects() {
        model.getMySubjects{subjects -> view.showSubjects(subjects)}

    }

    fun onResume() = model.getMySubjects {view.showSubjects(it)}

    fun addSubjectPressed() {
        view.toAllSubjects()
    }

    fun clickedSubjectsAdapter(subject: Subject) {
         model.getGroups(subject.code,
             { groups -> view.showSubjectDetails(subject, groups)},
             { error -> view.showMessage(error.toString())})

    }
}