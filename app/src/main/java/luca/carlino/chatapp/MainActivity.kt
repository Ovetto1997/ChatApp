package luca.carlino.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import luca.carlino.chatapp.presentation.ui.screens.ChatDetailScreen
import luca.carlino.chatapp.presentation.ui.screens.ChatListScreen
import luca.carlino.chatapp.presentation.ui.theme.ChatAppTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppTheme() {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "chat_list") {
                    composable("chat_list") {
                        ChatListScreen(
                            onChatClick = { chat ->
                                navController.navigate("chat_detail/${chat.id}")
                            }
                        )
                    }
                    composable(
                        "chat_detail/{chatId}",
                        arguments = listOf(
                            navArgument("chatId") { type = NavType.IntType}
                        )

                    ) { ChatDetailScreen(
                        onBackClick = { navController.popBackStack()}
                    )

                    }
                }

            }

        }
    }
}

