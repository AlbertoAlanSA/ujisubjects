package es.uji.al404230.ujisubjects

class AllSubjectsPresenter (private val model: MainModel, private val view: AllSubjectsActivity, prefix : String) {
    init {

        if(prefix == "") getAllSubjects()
        else onPrefixTextChanged(prefix)

    }
    private fun getAllSubjects(){
        model.getAllSubjects(
            { view.showSubjects(it) },
            { error -> view.showMessage(error.toString()) })
    }

    fun clickedSubjectsAdapter(subject: Subject){
        view.toUpdate(subject)
    }

    fun onPrefixTextChanged(prefix: String) {
        model.filterSubjectsWithPrefix(prefix) { view.showSubjects(it) }
    }


}
