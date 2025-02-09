package com.example.empoweher.model

import kotlinx.coroutines.flow.MutableStateFlow

sealed class DataState {
    class  Success(val data:MutableList<Event>):DataState()
    class  SuccessQuestion(val data:MutableList<Question>):DataState()
    class  SuccessAnswer(val data:MutableList<Answer>):DataState()
    class  SuccessUser(val data:MutableList<User>):DataState()
    class  SuccessSlots(val data:MutableList<Slot>):DataState()
    class  SuccessSlot(
        val data: MutableList<MutableList<Slot>>,
        val data2: MutableList<MutableList<Slot>>,
        val data3: MutableList<String>,
    ):DataState()
    class  Failure(val message:String):DataState()
    object Loading:DataState()
    object Empty:DataState()
}