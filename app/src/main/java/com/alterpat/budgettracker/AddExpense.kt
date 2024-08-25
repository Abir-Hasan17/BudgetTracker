package com.alterpat.budgettracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alterpat.budgettracker.data.model.ExpenseEntity
import com.alterpat.budgettracker.viewmodel.AddExpenseViewModel
import com.alterpat.budgettracker.viewmodel.AddExpenseViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AddExpense(){

    val viewModel : AddExpenseViewModel =
        AddExpenseViewModelFactory(LocalContext.current)
            .create(AddExpenseViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (topBar, nameRow, card) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp, start = 10.dp, end = 10.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }){
                Text(
                    text = "Add Expense",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                Image(painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Image(painter = painterResource(id = R.drawable.dots_menu),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            DataForm(modifier = Modifier
                .padding(10.dp)
                .padding(top = 50.dp)
                .constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onAddEpenseClick = {
                    coroutineScope.launch {
                        viewModel.addExpense(it)
                    }
                }
            )
        }
    }
}

@Composable
fun DataForm(modifier: Modifier, onAddEpenseClick:(model:ExpenseEntity) -> Unit){

    val title = remember {
        mutableStateOf("")
    }
    val amounts = remember {
        mutableStateOf("0.00")
    }
    val date = remember {
        mutableStateOf(System.currentTimeMillis())
    }
    val datePickerDialogVisibility = remember {
        mutableStateOf(false)
    }
    val category = remember {
        mutableStateOf("")
    }
    val type = remember {
        mutableStateOf("Expense")
    }

    Column(modifier = modifier
        .padding(10.dp)
        .fillMaxWidth()
        .shadow(10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(color = Color.White)
        .padding(10.dp)){

        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Title", fontSize = 15.sp)
        OutlinedTextField(value = title.value,
            onValueChange = {
                title.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Amount", fontSize = 15.sp)
        OutlinedTextField(value = amounts.value,
            onValueChange = {
                amounts.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .height(50.dp)
        )

        //Date picker
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Date", fontSize = 15.sp)
        OutlinedTextField(value =
            if (date.value == 0L)
                Utils.toHumanReadableDate(System.currentTimeMillis())
            else
                Utils.toHumanReadableDate(date.value),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .height(50.dp)
                .clickable { datePickerDialogVisibility.value = true },
            enabled = false
        )

        //DropDown for category, We do not need it.
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Category", fontSize = 15.sp)
        ExpenseCategoryDropdown(
            ListOfItem = listOf("PayPal","Netflix","Starbucks"),
            onItemSelected = {
                category.value = it
            })

        //Type: This we need
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Type", fontSize = 15.sp)
        ExpenseCategoryDropdown(
            ListOfItem = listOf("Expense","Income"),
            onItemSelected = {
                type.value = it
            })

        Button(onClick = {
            val model = ExpenseEntity(
                null,
                title.value,
                amounts.value.toDoubleOrNull() ?: 0.0,
                date.value,
                type.value
            )
            onAddEpenseClick(model)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)) {
            Text(
                text = "Add",
                fontSize = 15.sp,
                color = Color.White,
            )
        }
        if(datePickerDialogVisibility.value){
            ExpenseDatePickerDialog(onDateSelected = {
                date.value = it
                datePickerDialogVisibility.value = false
            },
                onDismiss = {datePickerDialogVisibility.value = false})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(
    onDateSelected: (date:Long) -> Unit,
    onDismiss: () -> Unit
){
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L

    DatePickerDialog(onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseCategoryDropdown(ListOfItem: List<String>, onItemSelected: (item: String)-> Unit){
    val expanded = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf(ListOfItem[0])
    }

    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {expanded.value = it}) {
        TextField(value = selectedItem.value, onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .height(50.dp)
                    .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            }
            )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = {  }) {
            ListOfItem.forEach(){
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        selectedItem.value = it
                        onItemSelected(selectedItem.value)
                        expanded.value = false
                })
            }
            
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddExpensePreview(){
    AddExpense()
}