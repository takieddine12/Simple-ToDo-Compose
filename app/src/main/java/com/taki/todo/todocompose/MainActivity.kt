package com.taki.todo.todocompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taki.todo.todocompose.mvvm.AlarmViewModel
import com.taki.todo.todocompose.screens.*
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val alarmViewModel = getViewModel<AlarmViewModel>()

            Scaffold(backgroundColor = Color(0xFF1D1D23), content = {
                CreateNavHost(navController = navController,alarmViewModel = alarmViewModel)
            })

          }
        }
    }




@Composable
fun CreateNavHost(navController : NavHostController,alarmViewModel: AlarmViewModel){
    NavHost(navController = navController, startDestination = Routing.Splash.route) {
          composable(route = Routing.Splash.route){
              SplashScreen(navController)
          }
          composable(route = Routing.Main.route){
              alarmViewModel.getAlarms()
              MainScreen(navController,alarmViewModel.state.collectAsState(),alarmViewModel)
          }
          composable(route = Routing.Add.route){
              AddAlarm(navController,alarmViewModel)
          }
          composable(route = Routing.Edit.route + "/{alarmId}" + "/{position}",
          arguments = listOf(navArgument("alarmId") { type = NavType.LongType },
              navArgument("position"){ type = NavType.IntType })){
              EditScreen(navController,alarmViewModel,it.arguments?.getLong("alarmId"),it.arguments!!.getInt("position"))
          }
    }
}
