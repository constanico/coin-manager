package com.example.myapplication.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.example.myapplication.db.TransactionEvent
import com.example.myapplication.db.TransactionState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.selects.select
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddTransactionScreen(
    navController: NavHostController,
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
             TopAppBar {
                 Button(
                     onClick = { navController.popBackStack() }
                 ) {
                     Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back to Home")
                 }
                 Text(text = "Add Transaction")
             }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        ) {
            RadioButtonSample()
            var pickedDate by remember {
                mutableStateOf(LocalDate.now())
            }
            val formattedDate by remember {
                derivedStateOf {
                    DateTimeFormatter
                        .ofPattern("E, dd MMM yyyy")
                        .format(pickedDate)
                }
            }
            val dateDialogState = rememberMaterialDialogState()
            Column {
                Text(text = "Date")
                Spacer(modifier = Modifier.padding(2.dp))
//                Column {
//                    ReadonlyTextField(
//                        value = ,
//                        onValueChange = ,
//                        onClick = { /*TODO*/ }
//                    ) {
//
//                    }
//                }
                Column {
                    Button(onClick = {
                        dateDialogState.show()
                    }) {
                        Text(text = formattedDate)
                    }
                }
                MaterialDialog(
                    dialogState = dateDialogState,
                    buttons = {
                        positiveButton(text = "Ok")
                        negativeButton(text = "Cancel")
                    }
                ) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Pick a date",
                    ) {
                        pickedDate = it
                    }
                }
    //            TextField(
    //                value = state.date,
    //                onValueChange = {
    //                    onEvent(TransactionEvent.SetDate(it))
    //                },
    //                placeholder = {
    //                    Text(text = "Date")
    //                }
    //            )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Column {
                Text(text = "Type")
                Spacer(modifier = Modifier.padding(2.dp))
                DropDownType(state = state, onEvent = onEvent)
//                OutlinedTextField(
//                    value = state.type,
//                    onValueChange = {
//                        onEvent(TransactionEvent.SetType(it))
//                    },
//                    shape = RoundedCornerShape(8.dp),
//                    placeholder = {
//                        Text(text = "Type")
//                    }
//                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Column {
                Text(text = "Method")
                Spacer(modifier = Modifier.padding(2.dp))
                OutlinedTextField(
                    value = state.method,
                    onValueChange = {
                        onEvent(TransactionEvent.SetMethod(it))
                    },
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(text = "Method")
                    }
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Column {
                Text(text = "Desc")
                Spacer(modifier = Modifier.padding(2.dp))
                OutlinedTextField(
                    value = state.desc,
                    onValueChange = {
                        onEvent(TransactionEvent.SetDesc(it))
                    },
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(text = "Desc")
                    }
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Column {
                Text(text = "Amount")
                Spacer(modifier = Modifier.padding(2.dp))
//            TextField(
//                value = state.amount,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//                onValueChange = {
//                    onEvent(TransactionEvent.SetAmount(it))
//                },
//                placeholder = {
//                    Text(text = "0")
//                }
//            )
                CurrencyTextField(
                    state,
                    onValueChange = { onEvent(TransactionEvent.SetAmount(state.amount)) }
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    shape = RoundedCornerShape(6.dp),
                    onClick = {
                        onEvent(TransactionEvent.SaveTransaction)
                        navController.popBackStack()
                    }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

//@Composable
//fun ReadonlyTextField(
//    value: LocalDate,
//    onValueChange: (TextFieldValue) -> Unit,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit,
//    label: @Composable () -> Unit
//) {
//    Box{
//        TextField(
//            value = value,
//            onValueChange = onValueChange,
//            modifier = modifier,
//            label = label
//        )
//        Box (
//            modifier = Modifier
//                .matchParentSize()
//                .alpha(0f)
//                .clickable { onClick = onClick }
//        )
//    }
//}

@Composable
fun CurrencyTextField(
    state: TransactionState,
    onValueChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { newText: String ->
            if (newText.length <= Long.MAX_VALUE.toString().length && newText.isDigitsOnly()) {
                text = newText
                onValueChange(newText)
            }
        },
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
        visualTransformation = NumberCommaTransformation(),
    )
}

class NumberCommaTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text = AnnotatedString(text.text.toLongOrNull().formatWithComma()),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return text.text.toLongOrNull().formatWithComma().length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.length
                }
            }
        )
    }
}

fun Long?.formatWithComma(): String =
    NumberFormat.getNumberInstance(Locale.US).format(this ?: 0)

@Composable
fun DropDownType(
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val list = listOf("Makanan", "Transportasi", "Pakaian", "Elektronik", "Olahraga", "Telepon", "Tagihan")
//    var selectedItem by remember { mutableStateOf("") }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if(expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column{
        OutlinedTextField(
            value = state.type,
            onValueChange = { onEvent(TransactionEvent.SetType(it)) },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()
                },
            shape = RoundedCornerShape(8.dp),
            trailingIcon = { Icon(icon, "", Modifier.clickable { expanded = !expanded }) }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { /*TODO*/ },
            modifier = Modifier
                .width(with(LocalDensity.current){textFiledSize.width.toDp()})
        ) {
            list.forEach {
                label ->
                DropdownMenuItem(onClick = {
                    state.type = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }

        }
    }
}

@Composable
fun RadioButtonSample() {
    val radioOptions = listOf("Pengeluaran", "Pemasukan")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }

    Row {
        radioOptions.forEach{ text ->
            Row(
                Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    .padding(12.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text
                )
            }
        }
    }
}