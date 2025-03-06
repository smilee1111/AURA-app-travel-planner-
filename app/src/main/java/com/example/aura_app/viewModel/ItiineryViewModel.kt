package com.example.aura_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aura_app.model.ItiineryModel
import com.example.aura_app.repository.ItiineryRepository
import com.example.aura_app.repository.ItiineryRepositoryImpl
import com.google.android.gms.tasks.Task

class ItiineryViewModel : ViewModel() {

    private val itiineryRepository: ItiineryRepository = ItiineryRepositoryImpl()

    private val _itiinery = MutableLiveData<ItiineryModel?>()
    val itiinery: LiveData<ItiineryModel?> = _itiinery

    fun saveOrUpdateItiinery(itiinery: ItiineryModel): LiveData<Task<Void>> {
        val result = MutableLiveData<Task<Void>>()
        result.value = itiineryRepository.saveOrUpdateItiinery(itiinery)
        return result
    }

    fun fetchItiinery(userId: String): LiveData<ItiineryModel?> {
        val result = MutableLiveData<ItiineryModel?>()
        itiineryRepository.getItiinery(userId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result.value = task.result
            } else {
                result.value = null
            }
        }
        return result
    }
}
