package com.orangebox.kit.authkey

import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class UserAuthKeyService {

    @Inject
    private lateinit var userAuthKeyDAO: UserAuthKeyDAO

    fun createKey(idUser: String, type: UserAuthKeyTypeEnum): UserAuthKey {
        var key = loadKeyByUser(idUser, type)
        if (key == null) {
            key = UserAuthKey()
            key.creationDate = Date()
            key.idUser = idUser
            key.type = type
            if (type == UserAuthKeyTypeEnum.EMAIL) {
                //ramdom uuid para email
                key.key = UUID.randomUUID().toString()
            } else {
                //4 digitos ramdomicos para SMS
                key.key = generateSMSNumber()
            }
            userAuthKeyDAO.insert(key)
        }
        return key
    }

    fun validateKey(key: UserAuthKey): Boolean {
        var validated = false
        val keyBase = loadKeyByUser(key.idUser!!, key.type!!)
        if (keyBase != null && keyBase.key == key.key) {
            validated = true
            userAuthKeyDAO.delete(keyBase)
        }
        return validated
    }


    private fun loadKeyByUser(idUser: String, type: UserAuthKeyTypeEnum): UserAuthKey? {
        return userAuthKeyDAO.retrieve(
            userAuthKeyDAO.createBuilder()
                .appendParamQuery("idUser", idUser)
                .appendParamQuery("type", type)
                .build()
        )
    }

    fun generateSMSNumber(): String {
        var numberStr = ""
        var numberFound = false
        while (!numberFound) {
            val number = (Math.random() * 10000).toInt()
            numberStr = number.toString()
            val key: UserAuthKey? = userAuthKeyDAO.retrieve(
                userAuthKeyDAO.createBuilder()
                    .appendParamQuery("key", numberStr)
                    .build()
            )
            numberFound = number > 999 && key == null
        }
        return numberStr
    }
}