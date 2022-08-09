package com.taki.todo.todocompose.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.taki.todo.todocompose.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {

    Scaffold(backgroundColor = Color(0xFF1D1D23), content = {
        Box(contentAlignment = Alignment.Center) {
            GifImage()
        }
        Move(navHostController)
    })

}

@Composable
fun Move(navHostController: NavHostController){
    LaunchedEffect(Unit){
        delay(5000)
        navHostController.navigate("Main")
    }
}

@Composable
fun GifImage() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(ImageRequest.Builder(context).data(data = R.drawable.alarm).apply(block = {
                size(coil.size.Size.ORIGINAL)}).build(),imageLoader = imageLoader),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFF1D1D23)),
    )
}