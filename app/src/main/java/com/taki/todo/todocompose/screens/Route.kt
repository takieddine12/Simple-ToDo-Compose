package com.taki.todo.todocompose.screens

sealed class Routing(val route : String) {
      object Splash : Routing("Splash")
      object Main : Routing("Main")
      object Add : Routing("Add")
      object Edit : Routing("Edit")
}