package es.uji.al404230.ujisubjects

import android.os.Bundle

class UpdatePresenter (private val model: MainModel, private val view: UpdateActivity, private val subjetCode : String) {
    private lateinit var myGroups : List<MyGroup>

    init{
        getAllGroups()
    }

    private fun getAllGroups(){
        model.getGroups(subjetCode,
            { groups -> storeAndShowGroups(groups)},
            { error -> view.showMessage(error.toString())})
    }

    private fun storeAndShowGroups(groups: List<MyGroup>){
        myGroups = groups
        view.showGroups(groups)
    }

    fun onUpdateButtonClicked() {
        model.updateGroups(myGroups) { view.finishPressed() }
    }

    fun clickedUpdateAdapter(group: MyGroup) {
        group.selected = !group.selected
    }

    fun saveState(savedInstanceState: Bundle) {
        savedInstanceState.putParcelableArray(MY_GROUPS, myGroups.toTypedArray())
    }

    fun setSate(savedInstanceState: Bundle) {
        myGroups = savedInstanceState.getParcelableArray(MY_GROUPS)!!.map { it as MyGroup }.toList()
        view.showGroups(myGroups)
    }

    companion object{
        private const val MY_GROUPS = "MY_GROUPS"
    }
}
