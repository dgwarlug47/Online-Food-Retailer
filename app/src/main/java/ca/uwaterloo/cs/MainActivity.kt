package ca.uwaterloo.cs

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import ca.uwaterloo.cs.db.DBInterFaceTest
import ca.uwaterloo.cs.db.DBClient
import ca.uwaterloo.cs.destinations.ProductFormDestination
import ca.uwaterloo.cs.models.Address
import ca.uwaterloo.cs.ui.theme.InstagramPurple
import ca.uwaterloo.cs.ui.theme.OnlineFoodRetailTheme
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlineFoodRetailTheme {
                val context = LocalContext.current
                generateMockData(1, context = context)
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination(start = true)
@Composable
fun MainContent(nav: DestinationsNavigator) {
    val useTemplate: Boolean=true //farmer:true,worker:false
    Scaffold(

                content = { TableScreen(nav,useTemplate) },
                bottomBar = { NavigationBar()})

}

@Composable
fun TableScreen(nav: DestinationsNavigator, useTemplate: Boolean) {
    val context = LocalContext.current
//<<<<<<< Updated upstream
    if(useTemplate) {
//        CenterAlignedTopAppBar(
//            title = { Text("Catalogue", color = Color.White) },
//            navigationIcon = {
//                IconButton(onClick = {
//                    addItem(nav, context)
//                }) {
//                    Icon(
//                        imageVector = Icons.Filled.Add,
//                        contentDescription = "Catalogue",
//                        tint = Color.White
//                    )
//                }
//            },
//            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.InstagramPurple)
//        )
        CenterAlignedTopAppBar(
            title = { Text("Catalogue", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = {
                    addItem(nav, context)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Catalogue",
                        tint = Color.White
                    )
                }
            },
            actions = {
                val openDialog = remember { mutableStateOf(false) }
                var text by remember { mutableStateOf(TextFieldValue("")) }
                //var text: TextFieldValue = TextFieldValue("")
                IconButton(
                    onClick = {
                        openDialog.value = true
                    //addItem(nav, context)
                    },
                    content = {Icon(painterResource(id = R.drawable.search,), contentDescription = "search",
                        modifier= Modifier
                            .width(20.dp)
                            .height(20.dp),
                        tint = Color.White)}

                )
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { /*TODO*/ },
                        title = { Text(text = "Search") },
                        text = {
                            Column() {
                                TextField(
                                    value = text,
                                    onValueChange = {
                                        text = it
                                    }
                                )
                                //Log.d("", text.toString())
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    openDialog.value = false
                                    val tableData = readData(context)
                                    for (item in tableData) {
                                        Log.d("item", item.second.name)
                                        Log.d("text", text.text)
                                        if (item.second.name == text.text) {
                                            Log.d("", "HI")
                                            editItem(nav, item.second, useTemplate)
                                            break
                                        }
                                    }
                                    text = TextFieldValue("")

                                }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    openDialog.value = false
                                    text = TextFieldValue("")
                                }) {
                                Text("Cancel")
                            }
                        }
                    )

                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.InstagramPurple)
        )
    }
    else{
        CenterAlignedTopAppBar(
            title = { Text("Catalogue", color = Color.White) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.InstagramPurple)
        )
    }
//=======
//    CenterAlignedTopAppBar(
//        title = { Text("Catalogue", color = Color.White) },
//        navigationIcon = {
//            IconButton(onClick = {
//                addItem(nav, context)
//            }) {
//                Icon(
//                    imageVector = Icons.Filled.Add,
//                    contentDescription = "Catalogue",
//                    tint = Color.White
//                )
//            }
//        },
//        actions = {
//            val openDialog = remember { mutableStateOf(false) }
//            var text by remember { mutableStateOf(TextFieldValue("")) }
//            //var text: TextFieldValue = TextFieldValue("")
//            IconButton(
//                onClick = {
//                    openDialog.value = true
//                //addItem(nav, context)
//                },
//                content = {Icon(painterResource(id = R.drawable.search,), contentDescription = "search",
//                    modifier= Modifier
//                        .width(20.dp)
//                        .height(20.dp),
//                    tint = Color.White)}
//
//            )
//            if (openDialog.value) {
//                AlertDialog(
//                    onDismissRequest = { /*TODO*/ },
//                    title = { Text(text = "Search") },
//                    text = {
//                        Column() {
//                            TextField(
//                                value = text,
//                                onValueChange = {
//                                    text = it
//                                }
//                            )
//                            //print(text)
//                        }
//                    },
//                    confirmButton = {
//                        Button(
//                            onClick = {
//                                openDialog.value = false
//                            }) {
//                            Text("Confirm")
//                        }
//                    },
//                    dismissButton = {
//                        Button(
//                            onClick = {
//                                openDialog.value = false
//                            }) {
//                            Text("Cancel")
//                        }
//                    }
//                )
//
//            }
//        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.InstagramPurple)
//    )
//>>>>>>> Stashed changes
    // TODO: REMOVE / UPGRADE MOCK DATA GENERATION IN FINAL PRODUCT
    val tableData = readData(context)
    // Each cell of a column must have the same weight.
    // The LazyColumn will be our table. Notice the use of the weights below
    Row() {
        Spacer(Modifier.width(22.dp))
        LazyColumn(
            Modifier
                .padding(66.dp)
                .background(Color.White)
                .heightIn(0.dp, 640.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Here are all the lines of your table.
            items(tableData, key = { it }) {
                Spacer(Modifier.height(10.dp))

                Row(
                    Modifier
                        .height(IntrinsicSize.Min)
                        .clickable { editItem(nav, it.second, useTemplate) }
                        .border(BorderStroke(3.dp, Color.InstagramPurple)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (it.second.image == "") {
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                text = it.second.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                        }
                    } else {
                        rememberImagePainter(it.second.image.toUri())
                        Image(
                            painter = rememberImagePainter(it.second.image.toUri()),
                            contentDescription = null,
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                        )
                    }

                }
            }
        }
    }
}


private fun editItem(nav: DestinationsNavigator, data: ProductInformation, useTemplate: Boolean) {
    nav.navigate(ProductFormDestination(data,useTemplate))
}

private fun addItem(nav: DestinationsNavigator, context: Context) {
    nav.navigate(ProductFormDestination())
}
/*
private fun addItem(nav: DestinationsNavigator, context: Context) {
    val options =
        arrayOf<CharSequence>(
            "From scratch",
            "From template",
            "Cancel"
        )
    val builder = android.app.AlertDialog.Builder(context)
    builder.setTitle("Create Product")
    builder.setItems(options) { dialog, item ->
        if (options[item] == "From scratch") {
            nav.navigate(ProductFormDestination())
        } else if (options[item] == "From template") {
            nav.navigate(ProductFormDestination(useTemplate = true))
        } else if (options[item] == "Cancel") {
            dialog.dismiss()
        }
    }
    builder.show()
}*/

private fun generateMockData(amount: Int = 7, context: Context) {
    val dir = File("${context.filesDir}/out")
    if (dir.exists()) {
        dir.deleteRecursively()
    }
    dir.mkdir()
    (1..amount).forEach { value ->
        ProductInformation(
            UUID.randomUUID().toString(),
            "apple $value",
            "apple $value description",
            100 * value + 1,
            10 * value + 1L,
            "",
            platform1 = false,
            platform2 = false
        ).exportData(context)
    }
}

private fun readData(context: Context): List<Pair<String, ProductInformation>> {
    // TODO: platform compatibility
    // TODO: load from platform
    val dir = File("${context.filesDir}/out")
    if (!dir.exists()) {
        return emptyList()
    }
    val list = ArrayList<Pair<String, ProductInformation>>()
    for (saveFile in dir.walk()) {
        if (saveFile.isFile && saveFile.canRead() && saveFile.name.contains("Product-")) {
            val fileIS = FileInputStream(saveFile)
            val inStream = ObjectInputStream(fileIS)
            val productInformation = inStream.readObject() as ProductInformation
            list.add(Pair(productInformation.id, productInformation))
            inStream.close()
            fileIS.close()
        }
    }
    return list
}
