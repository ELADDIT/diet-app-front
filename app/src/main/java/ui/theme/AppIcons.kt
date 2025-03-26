package nutrinexus.ui.theme

import nutrinexus.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource


object AppIcons {
    val User: Painter
        @Composable
        get() = painterResource(R.drawable.user_vector)


    val Mail: Painter
        @Composable
        get() = painterResource(R.drawable.mail_vector)


    val Meal: Painter
        @Composable
        get() = painterResource(R.drawable.meal_vector)


    val Chart: Painter
        @Composable
        get() = painterResource(R.drawable.chart_vector)


    val Home: Painter
        @Composable
        get() = painterResource(R.drawable.home_vector)


    val Calendar: Painter
        @Composable
        get() = painterResource(R.drawable.calander_vector)


    val Weights: Painter
        @Composable
        get() = painterResource(R.drawable.wights_vec)
}
