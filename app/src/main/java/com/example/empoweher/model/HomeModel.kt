package com.example.empoweher.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.empoweher.R

sealed class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: String,
) {
    object Home: BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        route = Screen.Home.route,
    )

    object Ask: BottomNavigationItem(
        title = "Ask",
        selectedIcon = Icons.Filled.Quiz,
        unselectedIcon = Icons.Outlined.Quiz,
        hasNews = false,
        route = Screen.Ask.route,
    )
    object Events: BottomNavigationItem(
        title = "Events",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
        hasNews = false,
        route = Screen.Events.route,
    )
    object Safety: BottomNavigationItem(
        title = "Safety",
        selectedIcon = Icons.Filled.Face,
        unselectedIcon = Icons.Outlined.Face,
        hasNews = false,
        route = Screen.Safety.route,
    )
}