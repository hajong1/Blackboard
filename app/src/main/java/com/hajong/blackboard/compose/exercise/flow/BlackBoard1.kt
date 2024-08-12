package com.hajong.blackboard.compose.exercise.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class BlackBoard1 {
    private val test = flow {
        emit(1)
        delay(3000L)
        emit(2)
        delay(3000L)
        emit(3)
    }

    private fun main(test: Flow<Int>) {
        CoroutineScope(coroutineContext).launch {
            test.collect {
                println(it)
            }
        }
    }

    init {
        main(test)
    }
}