package com.alterpat.budgettracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import com.alterpat.budgettracker.ui.theme.Green
import com.alterpat.budgettracker.ui.theme.Red
import com.alterpat.budgettracker.ui.theme.Zinc


@Composable
fun HomeScreen(){
    Surface(modifier = Modifier.fillMaxSize()){
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBar){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp, start = 10.dp, end = 10.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ){
                Column {
                    Text(text = "Welcome to Budget Tracker",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray)

                    Text(text = "Version-1.0.0",
                        fontSize = 13.sp,
                        color = Color.LightGray)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd))
            }
            CardItem(modifier = Modifier.constrainAs(card){
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            TransactionList(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                })
        }
    }
}

@Composable
fun CardItem(modifier: Modifier){
    Column (modifier = modifier
        .padding(20.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(color = Zinc)
        .padding(20.dp)
        .height(200.dp)){

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(text = "Total Balance", fontSize = 25.sp, color = Color.LightGray, fontWeight = FontWeight.Bold)
                Text(text = "5000tk", fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
            }
            Image(
                painter = painterResource(id = R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd))
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            CardRowItem(modifier = Modifier.align(Alignment.CenterStart),
                image = R.drawable.ic_income,
                title = "Income",
                amount = "7000tk")
            CardRowItem(modifier = Modifier.align(Alignment.CenterEnd),
                image = R.drawable.ic_expense,
                title = "Expense",
                amount = "2000tk")
        }

    }
}

@Composable
fun TransactionList(modifier: Modifier){
    Column (modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)){
        Box(modifier = Modifier.fillMaxWidth()){
            Text(text = "Recent Transactions", fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
            Text(
                text = "See All",
                fontSize = 20.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

        TransactionListItems(title = "Netflix",
            amount = "300tk",
            icon = R.drawable.ic_netflix,
            date = "22/08/2024",
            amountColor = Red)

        TransactionListItems(title = "Upwork",
            amount = "500tk",
            icon = R.drawable.ic_upwork,
            date = "22/08/2024",
            amountColor = Green)

        TransactionListItems(title = "Star Bucks",
            amount = "600tk",
            icon = R.drawable.ic_starbucks,
            date = "22/08/2024",
            amountColor = Red)
    }
}

@Composable
fun CardRowItem(modifier: Modifier, image: Int, title: String, amount: String){
    Column (modifier = modifier){
        Row {
            Image(painter = painterResource(id = image), contentDescription = null)
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = title, fontSize = 20.sp, color = Color.LightGray, fontWeight = FontWeight.Bold)
        }
        Text(text = amount, fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TransactionListItems(title: String, amount: String, icon: Int, date: String, amountColor: Color){
    Box(modifier = Modifier.fillMaxWidth()
        .padding(top = 10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(color = Color.LightGray)
        .padding(10.dp)){
        Row {
            Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.size(5.dp))
            Column {
                Text(text = title, fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                Text(text = date, fontSize = 20.sp, color = Color.DarkGray)
            }
        }
        Text(text = amount,
            fontSize = 20.sp,
            color = amountColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterEnd))
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    HomeScreen()
}
