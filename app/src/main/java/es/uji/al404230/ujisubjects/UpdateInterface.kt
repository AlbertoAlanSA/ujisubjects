package es.uji.al404230.ujisubjects

import es.uji.al404230.ujisubjects.MyGroup

interface UpdateInterface {
    fun showGroups (groups: List<MyGroup>)

    fun showMessage(error : String)

    fun finishPressed ()
}
