package com.hibikuroll.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF090B10)
private val Surface = Color(0xFF151922)
private val Surface2 = Color(0xFF1D2330)
private val Accent = Color(0xFF00AEEF)
private val TextPrimary = Color(0xFFF5F7FA)
private val TextSecondary = Color(0xFF9CA5B3)

private data class AnimeItem(
    val title: String,
    val episode: String,
    val progress: Float = 0f,
    val color: Color
)

private val sampleAnime = listOf(
    AnimeItem("Skybound Chronicles", "Episode 12", 0.78f, Color(0xFF28364D)),
    AnimeItem("Moonlight Requiem", "Episode 08", 0.42f, Color(0xFF3B2D4A)),
    AnimeItem("Neon Samurai", "Episode 05", 0.15f, Color(0xFF26413C)),
    AnimeItem("Crimson Arc", "Episode 21", 0f, Color(0xFF49322E))
)

private enum class MainTab(
    val label: String,
    val symbol: String
) {
    HOME("Home", "⌂"),
    SCHEDULE("Jadwal", "▣"),
    HISTORY("History", "↺"),
    SUBSCRIBED("Subscribed", "★"),
    TIMELINE("Time Line", "≡")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HibikurollApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HibikurollApp() {

    var selectedTab by rememberSaveable {
        mutableStateOf(MainTab.HOME.name)
    }

    var showProfile by rememberSaveable {
        mutableStateOf(false)
    }

    val currentTab = MainTab.valueOf(selectedTab)

    if (showProfile) {
        ProfileScreen(
            onBack = {
                showProfile = false
            }
        )
        return
    }

    Scaffold(
        containerColor = Background,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hibikuroll",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                },

                actions = {

                    Text(
                        text = "⌕",
                        color = TextPrimary,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .padding(end = 14.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Accent)
                            .clickable {
                                showProfile = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "H",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },

        bottomBar = {

            NavigationBar(
                containerColor = Surface
            ) {

                MainTab.values().forEach { tab ->

                    NavigationBarItem(
                        selected = currentTab == tab,

                        onClick = {
                            selectedTab = tab.name
                        },

                        icon = {
                            Text(
                                text = tab.symbol,
                                fontSize = 20.sp
                            )
                        },

                        label = {
                            Text(
                                text = tab.label,
                                fontSize = 9.sp
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        when (currentTab) {

            MainTab.HOME -> HomeScreen(paddingValues)

            MainTab.SCHEDULE -> ScheduleScreen(paddingValues)

            MainTab.HISTORY -> HistoryScreen(paddingValues)

            MainTab.SUBSCRIBED -> SubscribedScreen(paddingValues)

            MainTab.TIMELINE -> TimelineScreen(paddingValues)
        }
    }
}

@Composable
private fun HomeScreen(
    paddingValues: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Background),

        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 24.dp
        ),

        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        item {

            Text(
                text = "Temukan tontonan favoritmu",
                color = TextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Selamat datang di Hibikuroll",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            FeaturedCard()
        }

        item {

            SectionTitle("Lanjutkan Menonton")

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.horizontalScroll(
                    rememberScrollState()
                )
            ) {

                sampleAnime.take(3).forEach { anime ->

                    HistoryCard(anime)

                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }

        item {

            SectionTitle("Trending")

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.horizontalScroll(
                    rememberScrollState()
                )
            ) {

                sampleAnime.forEach { anime ->

                    PosterCard(anime)

                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }

        item {

            SectionTitle("Episode Terbaru")
        }

        items(sampleAnime) { anime ->

            EpisodeRow(anime)
        }
    }
}

@Composable
private fun FeaturedCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface2
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp)
            ) {

                Text(
                    text = "FEATURED",
                    color = Accent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Skybound Chronicles",
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Petualangan baru menunggumu.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent
                    )
                ) {

                    Text(
                        text = "▶ Mulai Nonton",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String
) {

    Text(
        text = title,
        color = TextPrimary,
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun PosterCard(
    anime: AnimeItem
) {

    Column(
        modifier = Modifier.width(130.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(anime.color),
            contentAlignment = Alignment.BottomStart
        ) {

            Text(
                text = anime.title.take(3).uppercase(),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = anime.title,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HistoryCard(
    anime: AnimeItem
) {

    Card(
        modifier = Modifier.width(210.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {

        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
                    .background(anime.color),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "▶",
                    color = Color.White,
                    fontSize = 28.sp
                )
            }

            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Text(
                    text = anime.title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = anime.episode,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun EpisodeRow(
    anime: AnimeItem
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(anime.color),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "▶",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = anime.title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = anime.episode,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }

            Text(
                text = "›",
                color = TextSecondary,
                fontSize = 28.sp
            )
        }
    }
}

@Composable
private fun ScheduleScreen(
    paddingValues: PaddingValues
) {

    val days = listOf(
        "Sen",
        "Sel",
        "Rab",
        "Kam",
        "Jum",
        "Sab",
        "Min"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Background),

        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "Jadwal Anime",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.horizontalScroll(
                    rememberScrollState()
                )
            ) {

                days.forEachIndexed { index, day ->

                    Card(
                        modifier = Modifier
                            .padding(end = 8.dp),

                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (index == 0) Accent else Surface
                        )
                    ) {

                        Text(
                            text = day,
                            color = Color.White,
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 10.dp
                            )
                        )
                    }
                }
            }
        }

        items(sampleAnime) { anime ->

            ScheduleRow(anime)
        }
    }
}

@Composable
private fun ScheduleRow(
    anime: AnimeItem
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        shape = RoundedCornerShape(15.dp)
    ) {

        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "19:30",
                color = Accent,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = anime.title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Episode baru • ${anime.episode}",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun HistoryScreen(
    paddingValues: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Background),

        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "History",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(sampleAnime.take(3)) { anime ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(75.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(anime.color),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "▶",
                                color = Color.White,
                                fontSize = 22.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = anime.title,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = anime.episode,
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF303541))
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(anime.progress)
                                .height(4.dp)
                                .background(Accent)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubscribedScreen(
    paddingValues: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Background),

        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "Subscribed",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Anime yang kamu ikuti",
                color = TextSecondary
            )
        }

        items(sampleAnime) { anime ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(anime.color),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "★",
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = anime.title,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Update terbaru tersedia",
                            color = Accent,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = "›",
                        color = TextSecondary,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineScreen(
    paddingValues: PaddingValues
) {

    val updates = listOf(
        "Skybound Chronicles" to "Episode 12 telah ditambahkan",
        "Moonlight Requiem" to "Episode 08 telah ditambahkan",
        "Neon Samurai" to "Informasi anime diperbarui",
        "Crimson Arc" to "Episode 21 telah ditambahkan"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Background),

        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Text(
                text = "Time Line",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(updates) { update ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = update.first,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = update.second,
                        color = TextSecondary,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Baru saja",
                        color = Accent,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    onBack: () -> Unit
) {

    Scaffold(
        containerColor = Background,

        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Profil",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {

                    Text(
                        text = "‹",
                        color = TextPrimary,
                        fontSize = 34.sp,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable {
                                onBack()
                            }
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Background),

            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                            .background(Accent),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "H",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Hibikuroll User",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Member",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }
            }

            item {

                ProfileMenu("Watch History")
            }

            item {

                ProfileMenu("My Subscriptions")
            }

            item {

                ProfileMenu("Favorites")
            }

            item {

                ProfileMenu("Downloads")
            }

            item {

                ProfileMenu("Notifications")
            }

            item {

                ProfileMenu("Player Settings")
            }

            item {

                ProfileMenu("Appearance")
            }

            item {

                ProfileMenu("Language")
            }

            item {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "About Hibikuroll",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ProfileMenu(
    title: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        shape = RoundedCornerShape(14.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                color = TextSecondary,
                fontSize = 26.sp
            )
        }
    }
}
