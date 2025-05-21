package com.revakovskyi.nytbooks.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.revakovskyi.auth.presentation.SignInScreenRoot
import com.revakovskyi.books.presentation.categories.CategoriesScreenRoot

@Composable
fun AppNavGraph(isSignedIn: Boolean) {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = if (isSignedIn) Graph.Books else Graph.Auth,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) }
    ) {
        authGraph(navController)
        booksGraph(navController)
    }

}


private fun NavGraphBuilder.authGraph(navController: NavHostController) {

    navigation<Graph.Auth>(startDestination = Graph.Auth.Destination.SignIn) {

        composable<Graph.Auth.Destination.SignIn> {
            SignInScreenRoot()
        }

    }

}


private fun NavGraphBuilder.booksGraph(navController: NavHostController) {

    navigation<Graph.Books>(startDestination = Graph.Books.Destination.Categories) {

        composable<Graph.Books.Destination.Categories> {
            CategoriesScreenRoot()
        }

        composable<Graph.Books.Destination.BookList> {
            // TODO: add a BookList screen
        }

        composable<Graph.Books.Destination.Store> {
            // TODO: add a Store screen
        }

    }

}
