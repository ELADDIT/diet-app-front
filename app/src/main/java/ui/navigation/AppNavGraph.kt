package nutrinexus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nutrinexus.ui.login.LoginScreen
import nutrinexus.ui.login.RegisterScreen
import nutrinexus.ui.main.HomeScreen
import nutrinexus.ui.main.AppointmentsScreen
import nutrinexus.ui.plans.DietPlanScreen
import nutrinexus.ui.plans.WorkoutPlanScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val APPOINTMENTS = "appointments"
    const val DIET_PLAN = "diet_plan"
    const val WORKOUT_PLAN = "workout_plan"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onRegisterClicked = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen()
        }
        composable(Routes.APPOINTMENTS) {
            AppointmentsScreen()
        }
        composable(Routes.DIET_PLAN) {
            DietPlanScreen()
        }
        composable(Routes.WORKOUT_PLAN) {
            WorkoutPlanScreen()
        }
    }
}
