package com.revakovskyi.nytbooks.navigation

import kotlinx.serialization.Serializable

sealed interface Graph {

    @Serializable
    data object Auth : Graph {

        sealed interface Destination {
            @Serializable
            data class SignIn(val forceSignOut: Boolean = false) : Destination
        }
    }


    @Serializable
    data object Books : Graph {

        sealed interface Destination {
            @Serializable
            data object Categories : Destination

            @Serializable
            data object BookList : Destination

            @Serializable
            data object Store : Destination
        }
    }

}
