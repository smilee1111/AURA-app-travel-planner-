package com.example.aura_app.repository

import com.example.aura_app.model.ItiineryModel
import com.google.android.gms.tasks.Task

interface ItiineryRepository {
    fun saveOrUpdateItiinery(itiinery: ItiineryModel): Task<Void>
    fun getItiinery(userId: String): Task<ItiineryModel>
}
