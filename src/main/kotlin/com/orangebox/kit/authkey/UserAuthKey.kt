package com.orangebox.kit.authkey

import com.orangebox.kit.core.annotation.OKEntity
import com.orangebox.kit.core.annotation.OKId
import java.util.*

@OKEntity(name = "userAuthKey")
class UserAuthKey {

	@OKId
    var id: String? = null

	var idUser: String? = null

	var key: String? = null

	var type: UserAuthKeyTypeEnum? = null

	var creationDate: Date? = null

    constructor()
    constructor(id: String?) {
        this.id = id
    }
}