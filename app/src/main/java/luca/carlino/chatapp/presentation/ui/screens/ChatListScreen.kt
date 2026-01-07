package luca.carlino.chatapp.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import luca.carlino.chatapp.domain.entities.Chat
import luca.carlino.chatapp.presentation.viewmodel.ChatListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import luca.carlino.chatapp.presentation.uistate.ChatListUiState
import androidx.compose.material3.HorizontalDivider
import coil3.compose.AsyncImage


// feature/chat_list/presentation/ChatListScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel = hiltViewModel(),
    onChatClick: (Chat) -> Unit,
    modifier: Modifier = Modifier
) {
    val chats by viewModel.chats.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        // Top App Bar
        TopAppBar(
            title = { Text("Chats") },
            modifier = Modifier.fillMaxWidth()
        )

        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::updateSearchQuery,
            onSearch = {}, // Added onSearch parameter
            active = false, // Assuming it's not active by default
            onActiveChange = {}, // Assuming no specific active change handling for now
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search chats...") }, // Added placeholder
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }, // Added leadingIcon
            content = {} // Added content lambda
        )

        // Content
        when (uiState) {
            is ChatListUiState.Loading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
            is ChatListUiState.Empty -> {
                EmptyScreen(
                    message = (uiState as ChatListUiState.Empty).message,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is ChatListUiState.Success -> {
                if (chats.isEmpty()) {
                    EmptyScreen(
                        message = "No chats found",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(chats) { chat ->
                            ChatItem(
                                chat = chat,
                                onClick = { onChatClick(chat) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            HorizontalDivider() // Changed Divider to HorizontalDivider
                        }
                    }
                }
            }
            is ChatListUiState.Error -> {
                ErrorScreen(
                    message = (uiState as ChatListUiState.Error).message,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun EmptyScreen(message: String, modifier: Modifier) {
    TODO("Not yet implemented")
}

@Composable
fun ChatItem(
    chat: Chat,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        AsyncImage(
            model = chat.imageUrl,
            contentDescription = chat.name,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Chat Info
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = chat.getFormattedLastMessageTime(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = chat.lastMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
