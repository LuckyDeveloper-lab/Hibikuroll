package com.hibikuroll.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Bg = Color(0xFF101114)
private val Surface = Color(0xFF17191E)
private val SurfaceAlt = Color(0xFF202632)
private val Blue = Color(0xFF178FF2)
private val Purple = Color(0xFF64559C)
private val TextMain = Color(0xFFF3F3F5)
private val TextSub = Color(0xFF9AA1AF)
private val Yellow = Color(0xFFFFD447)
private val OutlineGreen = Color(0xFF487354)

private data class AnimeUi(
    val title: String,
    val episodes: String,
    val rating: String,
    val views: String,
    val code: String,
    val a: Color,
    val b: Color
)

private val animes = listOf(
    AnimeUi("Sayonara Lara", "12 Eps", "7.7", "12.2K", "SL", Color(0xFF0D75D9), Color(0xFF7F3A78)),
    AnimeUi("One Piece", "1179 Eps", "8.7", "2.4M", "OP", Color(0xFF117CFF), Color(0xFF6548A2)),
    AnimeUi("Kuroneko to Majo no Kyoushitsu", "24 Eps", "7.2", "75.5K", "KM", Color(0xFF0C7EBA), Color(0xFF593A80)),
    AnimeUi("Nijusseiki Denki Mokuroku", "12 Eps", "7.5", "32K", "ND", Color(0xFFB17C3E), Color(0xFF5E4B40)),
    AnimeUi("Hyakkano Season 3", "12 Eps", "8.3", "103K", "HY", Color(0xFFD86B90), Color(0xFF774D87)),
    AnimeUi("Mushoku Tensei", "13 Eps", "8.8", "187.6K", "MT", Color(0xFF536D83), Color(0xFF252E51)),
    AnimeUi("Digimon Beatbreak", "48 Eps", "7.1", "16.5K", "DB", Color(0xFF733E85), Color(0xFF20253C)),
    AnimeUi("Mahou Shoujo Lyrical Nanoha", "12 Eps", "7.9", "41K", "ML", Color(0xFF6E8EA6), Color(0xFF9F4160)),
    AnimeUi("Saijo no Osewa", "12 Eps", "6.9", "178.7K", "SO", Color(0xFF9F5B83), Color(0xFFE29A61)),
    AnimeUi("Hanaori-san wa Tensei shitemo Kenka ga Shitai", "11 Eps", "7.2", "85.3K", "HA", Color(0xFF3E8AB5), Color(0xFF6E4E95)),
    AnimeUi("\"Kimi wo Aisuru Ki wa Nai\" to Itt...", "12 Eps", "6.8", "70.7K", "KI", Color(0xFFB5A05C), Color(0xFF5A6F87)),
    AnimeUi("Oni no Hanayome", "12 Eps", "7.0", "150.5K", "OH", Color(0xFFC96661), Color(0xFF493C59))
)

private val genres = listOf(
    "Action", "Adult Cast", "Adventure", "Avant Garde",
    "Award Winning", "Comedy", "Demons", "Drama",
    "Ecchi", "Fantasy", "Game", "Girls Love", "Gore",
    "Gourmet", "Harem", "Historical", "Horror", "Isekai",
    "Josei", "Magic", "Martial Arts", "Mecha", "Military",
    "Music", "Mystery", "Mythology", "Parody",
    "Psychological", "Reincarnation", "Romance", "Samurai",
    "School", "Sci-Fi", "Seinen", "Shoujo",
    "Shoujo Ai", "Shounen", "Slice Of Life", "Space",
    "Sports", "Super Power", "Supernatural", "Suspense",
    "Thriller", "Time Travel", "Tokusatsu", "Vampire", "Movie"
)

private enum class Tab(
    val label: String
) {
    HOME("Home"),
    SCHEDULE("Jadwal"),
    SUBSCRIBED("Subscribed"),
    HISTORY("Riwayat"),
    SOCIAL("SocialLine")
}

private val AppColors = darkColorScheme(
    primary = Blue,
    secondary = Purple,
    background = Bg,
    surface = Surface,
    onBackground = TextMain,
    onSurface = TextMain
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = AppColors) {
                HibikurollApp()
            }
        }
    }
}

@Composable
private fun HibikurollApp() {
    var tabName by rememberSaveable { mutableStateOf(Tab.HOME.name) }
    var profileOpen by rememberSaveable { mutableStateOf(false) }

    if (profileOpen) {
        ProfileScreen(onBack = { profileOpen = false })
        return
    }

    val selected = Tab.valueOf(tabName)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        when (selected) {
            Tab.HOME -> HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onProfile = { profileOpen = true }
            )
            Tab.SCHEDULE -> ScheduleScreen(Modifier.fillMaxSize())
            Tab.SUBSCRIBED -> SubscribedScreen(Modifier.fillMaxSize())
            Tab.HISTORY -> HistoryScreen(Modifier.fillMaxSize())
            Tab.SOCIAL -> SocialLineScreen(Modifier.fillMaxSize())
        }

        BottomDock(
            selected = selected,
            onSelected = { tabName = it.name },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun BottomDock(
    selected: Tab,
    onSelected: (Tab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Bg.copy(alpha = 0.97f))
            .padding(horizontal = 6.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Tab.values().forEach { tab ->
            val active = selected == tab
            val icon = when (tab) {
                Tab.HOME -> Icons.Outlined.Home
                Tab.SCHEDULE -> Icons.Outlined.CalendarMonth
                Tab.SUBSCRIBED -> Icons.Outlined.SmartDisplay
                Tab.HISTORY -> Icons.Outlined.History
                Tab.SOCIAL -> Icons.Outlined.Timeline
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(if (active) Purple else Color.Transparent)
                    .clickable { onSelected(tab) }
                    .padding(horizontal = 4.dp, vertical = 7.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = tab.label,
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = tab.label,
                    color = if (active) TextMain else TextSub,
                    fontSize = 10.sp,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    onProfile: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }

    val filtered = remember(query) {
        if (query.isBlank()) animes
        else animes.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 18.dp,
            bottom = 105.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            HomeProfileHeader(onProfile)
        }

        item {
            SearchBar(
                value = query,
                onValueChange = { query = it }
            )
        }

        item {
            PremiumBanner()
        }

        item {
            GiveawayCard()
        }

        item {
            DiscussionCard()
        }

        item {
            SectionHeader(
                title = "Terakhir Ditonton",
                action = "Selengkapnya"
            )
        }

        item {
            ContinueWatching()
        }

        item {
            SectionHeader(
                title = "New Anime Update",
                action = "Lihat Jadwal"
            )
        }

        item {
            AdaptiveAnimeGrid(filtered)
        }

        item {
            SectionHeader("Genre Series", "")
        }

        item {
            GenreCloud()
        }

        item {
            SectionHeader(
                title = "Weekly Anime",
                action = "Selengkapnya"
            )
        }

        item {
            WeeklyCarousel()
        }

        item {
            SectionHeader("Completed Anime", "")
        }

        item {
            CompletedRow()
        }
    }
}

@Composable
private fun HomeProfileHeader(
    onProfile: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProfile() }
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFC6A08F), Color(0xFF775A63))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "L",
                        color = Color.White,
                        fontSize = 31.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(13.dp))

                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Lucky",
                            color = TextMain,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(7.dp))
                        Text(
                            text = "#1485221",
                            color = TextSub,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(Modifier.height(5.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Cloud,
                            contentDescription = null,
                            tint = TextSub,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Lvl. 1",
                            color = TextSub,
                            fontSize = 13.sp
                        )
                    }
                }

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = "Notifikasi",
                        tint = TextMain,
                        modifier = Modifier.size(29.dp)
                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Cari",
                        tint = TextMain,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF5A4039), Color(0xFF3A3543))
                        )
                    )
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✦  0",
                    color = TextMain,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "Crystal",
                    color = TextSub,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFF62345E))
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                ) {
                    Text(
                        text = "✦ AniGames",
                        color = Yellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        placeholder = {
            Text(
                text = "Mencari anime",
                color = TextSub,
                fontSize = 17.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = TextSub,
                modifier = Modifier.size(25.dp)
            )
        },
        shape = RoundedCornerShape(34.dp)
    )
}

@Composable
private fun PremiumBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4C3837))
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(67.dp)
                    .clip(RoundedCornerShape(21.dp))
                    .background(Yellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(
                    text = "BELI PREMIUM DI SINI",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun GiveawayCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF062E43)
        )
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = "♛ TOP GIVEAWAY USERS  ♛",
                color = Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                GiveawayAvatar("1", animes[1])
                GiveawayAvatar("2", animes[4])
                GiveawayAvatar("3", animes[5])
            }

            Spacer(Modifier.height(9.dp))

            GiveawayRow(1, "『冥』 Tama 愛 Acha", "172481d")
            GiveawayRow(2, "flyRoxy.`", "164333d")
            GiveawayRow(3, "Cl...", "93150d")
            GiveawayRow(4, "冬...", "80175d")
            GiveawayRow(5, "毒...", "69368d")
        }
    }
}

@Composable
private fun GiveawayAvatar(
    rank: String,
    anime: AnimeUi
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(78.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(listOf(anime.a, anime.b))
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = anime.code,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
        }

        Text(
            text = "#$rank",
            color = Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun GiveawayRow(
    rank: Int,
    user: String,
    points: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF3A596A))
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#$rank",
            color = if (rank <= 2) Yellow else TextMain,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(36.dp)
        )

        Text(
            text = user,
            color = TextMain,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = points,
            color = Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun DiscussionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 15.dp,
                vertical = 14.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "▣  Public Diskusi",
                color = TextMain,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Spacer(Modifier.width(9.dp))

            Text(
                text = "|",
                color = TextSub
            )

            Spacer(Modifier.width(9.dp))

            Text(
                text = "青 | Fabian. : TF??",
                color = TextSub,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                tint = Yellow,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    action: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = TextMain,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (action.isNotBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = action,
                    color = Blue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

@Composable
private fun ContinueWatching() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 8.dp)
    ) {
        items(animes.take(2)) { anime ->
            Card(
                modifier = Modifier.width(260.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column {
                    PosterArt(
                        anime = anime,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(145.dp),
                        showNew = false,
                        showRating = false,
                        showEpisodes = false
                    )

                    Column(Modifier.padding(12.dp)) {
                        Text(
                            text = anime.title,
                            color = TextMain,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "${anime.episodes}  •  01:19 / 23:52",
                            color = TextSub,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(7.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xFF343946))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.68f)
                                    .height(5.dp)
                                    .background(Blue)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdaptiveAnimeGrid(
    list: List<AnimeUi>
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val columns = if (maxWidth >= 650.dp) 5 else 3
        val rows = list.chunked(columns)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { anime ->
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            AnimeCard(anime)
                        }
                    }

                    repeat(columns - row.size) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeCard(
    anime: AnimeUi
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PosterArt(
            anime = anime,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.68f),
            showNew = true,
            showRating = true,
            showEpisodes = true
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = anime.title,
            color = TextMain,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = null,
                tint = TextSub,
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = anime.views,
                color = TextSub,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun PosterArt(
    anime: AnimeUi,
    modifier: Modifier,
    showNew: Boolean,
    showRating: Boolean,
    showEpisodes: Boolean
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    listOf(anime.a, anime.b)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.TopEnd)
                .offset(x = 35.dp, y = (-20).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.10f))
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-20).dp, y = 20.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.14f))
        )

        Text(
            text = anime.code,
            color = Color.White.copy(alpha = 0.92f),
            fontSize = 40.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(82.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.78f)
                        )
                    )
                )
        )

        if (showNew) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 18.dp
                        )
                    )
                    .background(Blue)
                    .padding(horizontal = 11.dp, vertical = 7.dp)
            ) {
                Text(
                    text = "New",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        if (showRating) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(7.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xD6000000))
                    .padding(horizontal = 9.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = anime.rating,
                        color = Yellow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (showEpisodes) {
            Text(
                text = anime.episodes,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            )
        }
    }
}

@Composable
private fun GenreCloud() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rows = genres.chunked(4)

        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { genre ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(Bg)
                            .border(
                                BorderStroke(1.dp, OutlineGreen),
                                RoundedCornerShape(22.dp)
                            )
                            .padding(
                                horizontal = 13.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Text(
                            text = genre,
                            color = TextMain,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyCarousel() {
    val state = rememberLazyListState()
    val loopItems = remember {
        List(12) { index -> animes[index % animes.size] }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2400)
            val next = state.firstVisibleItemIndex + 1
            state.animateScrollToItem(next)
            if (next >= loopItems.size - 1) {
                state.scrollToItem(0)
            }
        }
    }

    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(end = 12.dp)
    ) {
        items(loopItems) { anime ->
            WeeklyCard(anime)
        }
    }
}

@Composable
private fun WeeklyCard(
    anime: AnimeUi
) {
    Card(
        modifier = Modifier.width(280.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Box {
            PosterArt(
                anime = anime,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                showNew = false,
                showRating = true,
                showEpisodes = true
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 18.dp
                        )
                    )
                    .background(Yellow)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "#1",
                    color = Color.Black,
                    fontWeight = FontWeight.Black
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(top = 110.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Surface.copy(alpha = 0.95f)
                            )
                        )
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = anime.title,
                    color = TextMain,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Text(
                    text = "◉ ${anime.views} views",
                    color = TextSub,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun CompletedRow() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(end = 8.dp)
    ) {
        items(animes.take(6)) { anime ->
            Box(Modifier.width(150.dp)) {
                PosterArt(
                    anime = anime,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(215.dp),
                    showNew = false,
                    showRating = true,
                    showEpisodes = false
                )
            }
        }
    }
}

@Composable
private fun ScheduleScreen(
    modifier: Modifier
) {
    val days = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat")
    val times = listOf("--:--", "18:57", "19:00", "20:30", "21:30", "22:00")

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 22.dp,
            bottom = 110.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Jadwal Tayang",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(days) { day ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (day == "Senin") Blue else Surface
                            )
                            .padding(horizontal = 22.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = day,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        items(times.size) { index ->
            ScheduleTimelineRow(
                time = times[index],
                anime = animes[index % animes.size],
                first = index == 0
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(28.dp))
                        .background(SurfaceAlt)
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Selasa",
                            color = TextMain,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(9.dp))
                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            contentDescription = null,
                            tint = TextMain
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleTimelineRow(
    time: String,
    anime: AnimeUi,
    first: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.width(70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time,
                color = Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .size(13.dp)
                    .clip(CircleShape)
                    .background(Yellow)
            )

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(118.dp)
                    .background(Color(0xFF56524B))
            )
        }

        Spacer(Modifier.width(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceAlt)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PosterArt(
                    anime = anime,
                    modifier = Modifier.size(86.dp),
                    showNew = false,
                    showRating = false,
                    showEpisodes = false
                )

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = anime.title,
                        color = TextMain,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = anime.episodes,
                        color = TextSub,
                        fontSize = 13.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = null,
                            tint = TextSub,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = anime.views,
                            color = TextSub,
                            fontSize = 11.sp
                        )
                        Spacer(Modifier.width(9.dp))
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            tint = Yellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = anime.rating,
                            color = TextSub,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubscribedScreen(
    modifier: Modifier
) {
    var sort by rememberSaveable { mutableStateOf("Terbaru") }
    var menuOpen by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 22.dp,
            bottom = 110.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Subscribed Anime",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total (${animes.size})",
                    color = TextMain,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f)
                )

                Box {
                    TextButton(
                        onClick = { menuOpen = true }
                    ) {
                        Text(
                            text = sort,
                            color = TextMain,
                            fontSize = 15.sp
                        )
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null,
                            tint = TextSub
                        )
                    }

                    DropdownMenu(
                        expanded = menuOpen,
                        onDismissRequest = { menuOpen = false }
                    ) {
                        listOf("Terbaru", "A-Z", "Rating").forEach { value ->
                            DropdownMenuItem(
                                text = { Text(value) },
                                onClick = {
                                    sort = value
                                    menuOpen = false
                                }
                            )
                        }
                    }
                }
            }
        }

        items(animes) { anime ->
            SubscriptionRow(anime)
        }
    }
}

@Composable
private fun SubscriptionRow(
    anime: AnimeUi
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PosterArt(
                anime = anime,
                modifier = Modifier.size(92.dp),
                showNew = false,
                showRating = true,
                showEpisodes = false
            )

            Spacer(Modifier.width(13.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = anime.title,
                    color = TextMain,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "Update terbaru tersedia",
                    color = Blue,
                    fontSize = 13.sp
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = TextSub,
                modifier = Modifier.size(29.dp)
            )
        }
    }
}

@Composable
private fun HistoryScreen(
    modifier: Modifier
) {
    var multi by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 22.dp,
                bottom = 150.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Riwayat Menonton",
                    color = TextMain,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "Tahan series untuk pilih & hapus",
                    color = TextSub,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            item {
                DayChip("Hari ini")
            }

            items(animes.take(2)) { anime ->
                HistoryRow(anime)
            }

            item {
                DayChip("Kemarin")
            }

            items(animes.drop(2).take(2)) { anime ->
                HistoryRow(anime)
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 92.dp
                )
                .clip(RoundedCornerShape(30.dp))
                .background(SurfaceAlt)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(if (!multi) Blue else Color.Transparent)
                    .clickable { multi = false }
                    .padding(horizontal = 21.dp, vertical = 11.dp)
            ) {
                Text(
                    text = "SINGLE",
                    color = if (!multi) Color.White else TextSub,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(if (multi) Blue else Color.Transparent)
                    .clickable { multi = true }
                    .padding(horizontal = 21.dp, vertical = 11.dp)
            ) {
                Text(
                    text = "MULTI",
                    color = if (multi) Color.White else TextSub,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun DayChip(
    text: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(Blue)
            .padding(horizontal = 20.dp, vertical = 11.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HistoryRow(
    anime: AnimeUi
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PosterArt(
                anime = anime,
                modifier = Modifier.size(90.dp),
                showNew = false,
                showRating = false,
                showEpisodes = false
            )

            Spacer(Modifier.width(13.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = anime.title,
                    color = TextMain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${anime.episodes} • Hari ini",
                    color = TextSub,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(9.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF363A44))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(
                                if (anime.title == "One Piece") 1f else 0.35f
                            )
                            .height(5.dp)
                            .background(Blue)
                    )
                }
            }
        }
    }
}

@Composable
private fun SocialLineScreen(
    modifier: Modifier
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 22.dp,
                bottom = 115.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFC6A08F),
                                        Color(0xFF775A63)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "L",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Lucky",
                            color = TextMain,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Belum ada aktivitas pertemanan.",
                            color = TextSub,
                            fontSize = 13.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Pengaturan",
                        tint = TextMain,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            item {
                Spacer(Modifier.height(235.dp))
                Text(
                    text = "Belum ada aktivitas pertemanan.",
                    color = TextSub,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = "Mulai follow teman untuk lihat update mereka",
                    color = TextSub,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 94.dp
                )
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF235EC0))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.People,
                contentDescription = "Teman",
                tint = Color.White,
                modifier = Modifier.size(29.dp)
            )
        }
    }
}

@Composable
private fun ProfileScreen(
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 22.dp,
                bottom = 30.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onBack) {
                        Text(
                            text = "‹",
                            color = TextMain,
                            fontSize = 34.sp
                        )
                    }

                    Spacer(Modifier.width(3.dp))

                    Text(
                        text = "Profil",
                        color = TextMain,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        tint = TextMain,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(82.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFC6A08F),
                                        Color(0xFF775A63)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "L",
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "Lucky",
                            color = TextMain,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Lvl. 1  •  #1485221",
                            color = TextSub
                        )
                    }
                }
            }

            item { ProfileEntry("Watch History", Icons.Outlined.History) }
            item { ProfileEntry("My Subscriptions", Icons.Outlined.BookmarkBorder) }
            item { ProfileEntry("Favorites", Icons.Outlined.Star) }
            item { ProfileEntry("Downloads", Icons.Outlined.Download) }
            item { ProfileEntry("Notifications", Icons.Outlined.NotificationsNone) }
            item { ProfileEntry("Player Settings", Icons.Outlined.SmartDisplay) }
            item { ProfileEntry("Appearance", Icons.Outlined.Palette) }
            item { ProfileEntry("Language", Icons.Outlined.Language) }
        }
    }
}

@Composable
private fun ProfileEntry(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextSub,
                modifier = Modifier.size(23.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = title,
                color = TextMain,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = TextSub,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
