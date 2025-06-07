package com.example.juansuarez_p1_ap2.presentation.tarea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juansuarez_p1_ap2.data.local.entities.TareaEntity
import com.example.juansuarez_p1_ap2.data.repository.TareaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val tareaRepository: TareaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTareas()
    }

    fun onEvent(event: TareaEvent){
        when (event){
            is TareaEvent.TareaChange -> onTareaIdChange(event.tareaId)
            is TareaEvent.DescripcionChange -> onDescripcionChange(event.descripcion)
            is TareaEvent.TiempoChange -> onTiempoChange(event.tiempo)
            TareaEvent.Save -> save()
            TareaEvent.Delete -> delete()
            TareaEvent.New -> nuevo()
        }
    }



    private fun save() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isNullOrBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "Es obligatorio rellenar el campo descripcion...")
                }
                return@launch
            }

            val descripcionYaExiste = tareaRepository.existeDescripcion(_uiState.value.descripcion!!)

            if (descripcionYaExiste) {
                _uiState.update {
                    it.copy(errorMessage = "La descripcion ya existe")
                }
                return@launch
            }

            val tiempoActual = System.currentTimeMillis()
            val tiempoSeleccionado = _uiState.value.tiempo ?: 0L

            if (tiempoSeleccionado < tiempoActual) {
                _uiState.update {
                    it.copy(errorMessage = "La fecha y hora seleccionada no puede ser menor a la fecha actual")
                }
                return@launch
            }

            try {
                tareaRepository.save(_uiState.value.toEntity())

                _uiState.update {
                    it.copy(errorMessage = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al guardar: ${e.message}")
                }
            }
        }
    }

    private fun nuevo() {
        _uiState.update {
            it.copy(
                tareaId = null,
                descripcion = "",
                tiempo = 0,
                errorMessage = null
            )
        }
    }

    fun selectedTarea(tareaId: Int){
        viewModelScope.launch {
            if(tareaId > 0){
                try {
                    val tarea = tareaRepository.getTarea(tareaId)
                    _uiState.update {
                        it.copy(
                            tareaId = tarea?.tareaId,
                            descripcion = tarea?.descripcion ?: "",
                            tiempo = tarea?.tiempo,
                            errorMessage = null
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(errorMessage = "Error al cargar tarea: ${e.message}")
                    }
                }
            }
        }
    }

    private fun delete() {
        viewModelScope.launch {
            try {
                tareaRepository.delete(_uiState.value.toEntity())
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al eliminar: ${e.message}")
                }
            }
        }
    }

    private fun getTareas() {
        viewModelScope.launch {
            tareaRepository.getTareas().collect { tareas ->
                _uiState.update {
                    it.copy(tareas = tareas)
                }
            }
        }
    }

    private fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(
                descripcion = descripcion,
                errorMessage = null
            )
        }
    }

    private fun onTiempoChange(tiempo: Long) {
        _uiState.update {
            it.copy(
                tiempo = tiempo,
                errorMessage = null
            )
        }
    }

    private fun onTareaIdChange(tareaId: Int) {
        _uiState.update {
            it.copy(tareaId = tareaId)
        }
    }

    fun onNavigationDone(){
        _uiState.update {
            it.copy(guardado = false)
        }
    }

}



fun TareaUiState.toEntity() = TareaEntity(
    tareaId = tareaId,
    descripcion = descripcion ?: "",
    tiempo = tiempo
)
