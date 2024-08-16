package com.hajong.blackboard.compose.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hajong.blackboard.R
import com.hajong.blackboard.compose.common.LocalImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MyDataSource() {
    fun getFlow1() = flow {
        repeat(4) {
            emit("Flow 1: $it")
            delay(500)
            if (it == 2) {
                error("Flow 1: Error")
            }
        }
    }.catch {
        it.printStackTrace()
    }

    fun getFlow2() = flow {
        repeat(3) {
            emit("Flow 2: $it")
            delay(500)
            if (it == 2) {
                error("Flow 2: Error")
            }
        }
    }.catch {
        it.printStackTrace()
    }
}

class MyRepository() {
    private val datasource = MyDataSource()

    fun getFlow1(): Flow<String> {
        return datasource.getFlow1()
    }

    fun getFlow2(): Flow<String> {
        return datasource.getFlow1().zip(datasource.getFlow2()) { flow1, flow2 ->
            "$flow1 + $flow2"
        }
    }
}

class MyViewModel(): ViewModel() {
    private val repository = MyRepository()

    private val _number = MutableStateFlow(10)
    val number: StateFlow<Int> = _number
    private val _text1 = MutableStateFlow("This is StateFlow")
    val text1: StateFlow<String> = _text1
    private val _text2 = MutableStateFlow("This is StateFlow")
    val text2: StateFlow<String> = _text2

    private val newText = MutableStateFlow("This is NewStateFlow")

    init {
        viewModelScope.launch {
            newText.collect { value ->
                _text1.update { value }
            }
        }

        fetchFlow1()
        fetchFlow2()
    }

    fun plus() {
        _number.value++
    }

    fun minus() {
        _number.value--
    }

    private fun fetchFlow1() {
        viewModelScope.launch {
            val result = repository.getFlow1()

            result.collect { value ->
                _text1.update { value }
            }
        }
    }

    private fun fetchFlow2() {
        viewModelScope.launch {
            val result = repository.getFlow2()

            result.collect { value ->
                _text2.update { value }
            }
        }
    }

    fun anotherEmit1() {
        newText.value = "This is another emit 1"
    }

    fun anotherEmit2() {
        newText.value = "This is another emit 2"
    }
}

@Composable
fun EmptyContainer(
    viewModel: MyViewModel = viewModel()
) {

    val number = viewModel.number.collectAsState()
    val text1 = viewModel.text1.collectAsState()
    val text2 = viewModel.text2.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LocalImage(
            id = R.drawable.ic_launcher_foreground,
            scale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "My Flow level is low level",
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                viewModel.plus()
            }) {
                Text(
                    text = "Plus",
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            TextButton(onClick = {
                viewModel.minus()
            }) {
                Text(
                    text = "Minus",
                    modifier = Modifier
                )
            }
        }
        Text(
            text = "Number : ${number.value}",
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                viewModel.anotherEmit1()
            }) {
                Text(
                    text = "Another Emit 1",
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            TextButton(onClick = {
                viewModel.anotherEmit2()
            }) {
                Text(
                    text = "Another Emit 2",
                    modifier = Modifier
                )
            }
        }
        Text(
            text = text1.value,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = text2.value,
            modifier = Modifier
        )
    }
}