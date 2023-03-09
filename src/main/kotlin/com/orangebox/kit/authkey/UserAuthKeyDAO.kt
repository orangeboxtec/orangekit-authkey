package com.orangebox.kit.authkey

import com.orangebox.kit.core.dao.AbstractDAO
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserAuthKeyDAO : AbstractDAO<UserAuthKey>(UserAuthKey::class.java) {
    override fun getId(bean: UserAuthKey): Any? {
        return bean.id
    }
}