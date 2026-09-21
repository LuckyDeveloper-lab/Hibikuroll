package com.hibikuroll.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

private val AppBackground = Color(0xFF101114)
private val AppSurface = Color(0xFF17191E)
private val AppSurface2 = Color(0xFF202530)
private val AppBlue = Color(0xFF1494F5)
private val AppPurple = Color(0xFF62549A)
private val AppText = Color(0xFFF4F5F8)
private val AppSubText = Color(0xFF9DA5B4)
private val AppYellow = Color(0xFFFFD54A)
private val AppGreen = Color(0xFF6EAF73)

private data class Anime(
    val title: String,
    val episode: String,
    val rating: String,
    val views: String,
    val short: String,
    val start: Color,
    val end: Color
)

private val animeList = listOf(
    Anime("One Piece", "1179 Eps", "8.7", "2.4M", "OP", Color(0xFF1179FF), Color(0xFF6B2D80)),
    Anime("Black Clover", "170 Eps", "8.1", "56.8K", "BC", Color(0xFF171B29), Color(0xFFB42E35)),
    Anime("Naruto: Shippuuden", "500 Eps", "8.3", "63.8K", "NS", Color(0xFFB67F45), Color(0xFF40332A)),
    Anime("Bleach", "365 Eps", "7.9", "46K", "BL", Color(0xFF202020), Color(0xFF8C214E)),
    Anime("Mushoku Tensei", "13 Eps", "8.8", "187.6K", "MT", Color(0xFF304D56), Color(0xFF263245)),
    Anime("Neon Samurai", "24 Eps", "7.2", "75.5K", "NS", Color(0xFF1E4B91), Color(0xFF823E91)),
    Anime("Moonlight Requiem", "12 Eps", "8.3", "103K", "MR", Color(0xFF633F76), Color(0xFF202A63)),
    Anime("Crimson Arc", "13 Eps", "7.6", "85.3K", "CA", Color(0xFF913A41), Color(0xFF23243D))
)

private val genres = listOf(
    "Action", "Adult Cast", "Adventure", "Comedy",
    "Demons", "Drama", "Fantasy", "Game",
    "Girls Love", "Horror", "Isekai", "Josei",
    "Magic", "Martial Arts", "Mecha", "Military",
    "Music", "Mystery", "Mythology", "Parody",
    "Psychological", "Reincarnation", "Romance", "Samurai",
    "School", "Sci-Fi", "Seinen", "Shoujo",
    "Shounen", "Slice Of Life", "Space", "Sports",
    "Supernatural", "Suspense", "Thriller", "Time Travel",
    "Tokusatsu", "Vampire", "Movie"
)

private enum class MainTab(
    val title: String,
    val icon: String
) {
    HOME("Home", "⌂"),
    SCHEDULE("Jadwal", "▣"),
    SUBSCRIBED("Subscribed", "▶"),
    HISTORY("Riwayat", "↶"),
    SOCIAL("SocialLine", "⌁")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                HibikurollApp()
            }
        }
    }
}

@Composable
private fun HibikurollApp() {

    var currentTab by rememberSaveable {
        mutableStateOf(MainTab.HOME.name)
    }

    var profileOpen by rememberSaveable {
        mutableStateOf(false)
    }

    if (profileOpen) {
        ProfileScreen(
            onBack = { profileOpen = false }
        )
        return
    }

    val tab = MainTab.valueOf(currentTab)

    Scaffold(
        containerColor = AppBackground,

        topBar = {
            if (tab == MainTab.HOME) {
                HomeHeader(
                    onProfile = { profileOpen = true }
                )
            } else {
                SimpleHeader(
                    title = tab.title
                )
            }
        },

        bottomBar = {
            BottomDock(
                selected = tab,
                onSelected = {
                    currentTab = it.name
                }
            )
        }
    ) { padding ->

        when (tab) {
            MainTab.HOME -> HomeScreen(padding)
            MainTab.SCHEDULE -> ScheduleScreen(padding)
            MainTab.SUBSCRIBED -> SubscribedScreen(padding)
            MainTab.HISTORY -> HistoryScreen(padding)
            MainTab.SOCIAL -> SocialLineScreen(padding)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeHeader(
    onProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppBackground)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = AppSurface
            ),
            shape = RoundedCornerShape(24.dp)
        ) {

            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFC39A86),
                                        Color(0xFF765E63)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "L",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Lucky",
                                color = AppText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(7.dp))

                            Text(
                                text = "#1485221",
                                color = AppSubText,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "☁ Lvl. 1",
                            color = AppSubText,
                            fontSize = 13.sp
                        )
                    }

                    TextButton(
                        onClick = {},
                        modifier = Modifier.size(46.dp)
                    ) {
                        Text(
                            text = "♟",
                            fontSize = 24.sp,
                            color = AppText
                        )
                    }

                    TextButton(
                        onClick = {},
                        modifier = Modifier.size(46.dp)
                    ) {
                        Text(
                            text = "⌕",
                            fontSize = 30.sp,
                            color = AppText
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF594039),
                                    Color(0xFF3A3544)
                                )
                            )
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 10.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "✦  0",
                        color = AppText,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = " Crystal",
                        color = AppSubText,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF5A335A))
                            .padding(
                                horizontal = 18.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Text(
                            text = "✦ AniGames",
                            color = AppYellow,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SimpleHeader(
    title: String
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                color = AppText,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppBackground
        )
    )
}

@Composable
private fun BottomDock(
    selected: MainTab,
    onSelected: (MainTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppBackground)
            .padding(horizontal = 6.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainTab.values().forEach { tab ->

            val active = selected == tab

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (active) AppPurple
                        else Color.Transparent
                    )
                    .clickable {
                        onSelected(tab)
                    }
                    .padding(vertical = 7.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tab.icon,
                    color = Color.White,
                    fontSize = 23.sp
                )

                Text(
                    text = tab.title,
                    color = if (active) AppText else AppSubText,
                    fontSize = 10.sp,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    padding: PaddingValues
) {

    var search by rememberSaveable {
        mutableStateOf("")
    }

    val filtered = remember(search) {
        if (search.isBlank()) {
            animeList
        } else {
            animeList.filter {
                it.title.contains(
                    search,
                    ignoreCase = true
                )
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(AppBackground),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 10.dp,
            bottom = 25.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                placeholder = {
                    Text(
                        text = "Mencari anime",
                        color = AppSubText
                    )
                },
                leadingIcon = {
                    Text(
                        text = "⌕",
                        color = AppSubText,
                        fontSize = 26.sp
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
        }

        item {
            PromoCard()
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
            LastWatched()
        }

        item {
            SectionHeader(
                title = "New Anime Update",
                action = "Lihat Jadwal"
            )
        }

        item {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(
                    minSize = 145.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(540.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                gridItems(filtered) { anime ->
                    AnimeGridCard(anime)
                }
            }
        }

        item {
            SectionHeader(
                title = "Genre Series",
                action = ""
            )
        }

        item {

            Row(
                modifier = Modifier.horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                genres.take(12).forEach { genre ->
                    GenreChip(genre)
                }
            }
        }

        item {

            Row(
                modifier = Modifier.horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                genres.drop(12).take(12).forEach { genre ->
                    GenreChip(genre)
                }
            }
        }

        item {
            SectionHeader(
                title = "Weekly Anime",
                action = "Selengkapnya"
            )
        }

        item {
            AutoCarousel()
        }

        item {
            SectionHeader(
                title = "Completed Anime",
                action = ""
            )
        }

        item {
            Row(
                modifier = Modifier.horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                animeList.take(5).forEach { anime ->
                    SmallPoster(anime)
                }
            }
        }
    }
}

@Composable
private fun PromoCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4D3734)
        ),
        shape = RoundedCornerShape(22.dp)
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppYellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "♟",
                    color = Color.Black,
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppBlue
                ),
                shape = RoundedCornerShape(30.dp)
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
private fun DiscussionCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppSurface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "▣  Public Diskusi",
                color = AppText,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "|",
                color = AppSubText
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "青 | Fabian. : TF??",
                color = AppSubText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "→",
                color = AppYellow,
                fontSize = 22.sp
            )
        }
    }
}

@Composable
private fun LastWatched() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(androidx.compose.foundation.rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        animeList.take(2).forEach { anime ->

            Card(
                modifier = Modifier.width(260.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppSurface
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column {

                    PosterSurface(
                        anime = anime,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(145.dp)
                    )

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = anime.title,
                            color = AppText,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${anime.episode}  •  01:19 / 23:52",
                            color = AppSubText,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF343A45))
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.68f)
                                    .height(4.dp)
                                    .background(AppBlue)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeGridCard(
    anime: Anime
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Box {

            PosterSurface(
                anime = anime,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(205.dp)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 16.dp
                        )
                    )
                    .background(AppBlue)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 7.dp
                    )
            ) {

                Text(
                    text = "New",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(7.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xB9000000))
                    .padding(
                        horizontal = 9.dp,
                        vertical = 5.dp
                    )
            ) {

                Text(
                    text = "★ ${anime.rating}",
                    color = AppYellow,
                    fontSize = 12.sp
                )
            }

            Text(
                text = anime.episode,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = anime.title,
            color = AppText,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "◉ ${anime.views}",
            color = AppSubText,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun PosterSurface(
    anime: Anime,
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        anime.start,
                        anime.end
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = anime.short,
            color = Color.White.copy(alpha = 0.90f),
            fontSize = 38.sp,
            fontWeight = FontWeight.Black
        )
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
            color = AppText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (action.isNotBlank()) {

            Text(
                text = "$action  ›",
                color = AppBlue,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun GenreChip(
    text: String
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(AppBackground)
            .padding(
                horizontal = 16.dp,
                vertical = 9.dp
            )
    ) {

        Text(
            text = text,
            color = AppText,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun AutoCarousel() {

    val base = animeList.take(6)
    val repeated = List(20) { base }.flatten()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {

        listState.scrollToItem(
            base.size * 5
        )

        while (true) {

            delay(2200)

            val next =
                listState.firstVisibleItemIndex + 1

            listState.animateScrollToItem(
                next
            )

            if (next > base.size * 15) {

                listState.scrollToItem(
                    base.size * 5
                )
            }
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(repeated.size) { index ->

            val anime = repeated[index]

            Card(
                modifier = Modifier.width(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppSurface
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column {

                    PosterSurface(
                        anime = anime,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(155.dp)
                    )

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = anime.title,
                            color = AppText,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${anime.episode}  •  ★ ${anime.rating}",
                            color = AppSubText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SmallPoster(
    anime: Anime
) {
    Column(
        modifier = Modifier.width(150.dp)
    ) {
        PosterSurface(
            anime = anime,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = anime.title,
            color = AppText,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${anime.episode} • ★ ${anime.rating}",
            color = AppSubText,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ScheduleScreen(
    padding: PaddingValues
) {

    val scheduleAnime = animeList.take(6)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(AppBackground),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 25.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.horizontalScroll(
                    rememberScrollState()
                )
            ) {

                listOf(
                    "Senin",
                    "Selasa",
                    "Rabu",
                    "Kamis",
                    "Jumat"
                ).forEachIndexed { index, day ->

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                if (index == 0) {
                                    AppBlue
                                } else {
                                    AppSurface
                                }
                            )
                            .padding(
                                horizontal = 18.dp,
                                vertical = 11.dp
                            )
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

        items(scheduleAnime.size) { index ->

            val anime = scheduleAnime[index]

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(66.dp)
                ) {

                    Text(
                        text = when(index) {
                            0 -> "--:--"
                            1 -> "18:57"
                            2 -> "19:00"
                            3 -> "20:30"
                            4 -> "21:30"
                            else -> "22:00"
                        },
                        color = AppYellow,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(AppYellow)
                    )

                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(110.dp)
                            .background(Color(0xFF57504A))
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = AppSurface2
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        PosterSurface(
                            anime = anime,
                            modifier = Modifier
                                .size(80.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {

                            Text(
                                text = anime.title,
                                color = AppText,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = anime.episode,
                                color = AppSubText
                            )

                            Text(
                                text = "◉ ${anime.views}  •  ★ ${anime.rating}",
                                color = AppSubText,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryScreen(
    padding: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(AppBackground),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 25.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(AppBlue)
                    .padding(
                        horizontal = 18.dp,
                        vertical = 10.dp
                    )
            ) {

                Text(
                    text = "Hari ini",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(animeList.take(4)) { anime ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppSurface
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    PosterSurface(
                        anime = anime,
                        modifier = Modifier.size(90.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = anime.title,
                            color = AppText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${anime.episode} • Hari ini",
                            color = AppSubText
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xFF353A45))
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(
                                        if (anime.title == "One Piece") 1f else 0.35f
                                    )
                                    .height(5.dp)
                                    .background(AppBlue)
                            )
                        }
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

    var menu by rememberSaveable {
        mutableStateOf(false)
    }

    var sort by rememberSaveable {
        mutableStateOf("Terbaru")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(AppBackground),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 25.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Total (${animeList.size})",
                    color = AppText,
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )

                Box {

                    TextButton(
                        onClick = { menu = true }
                    ) {
                        Text(
                            text = "$sort  ▾",
                            color = AppText
                        )
                    }

                    DropdownMenu(
                        expanded = menu,
                        onDismissRequest = { menu = false }
                    ) {

                        listOf(
                            "Terbaru",
                            "A-Z",
                            "Rating"
                        ).forEach { value ->

                            DropdownMenuItem(
                                text = {
                                    Text(value)
                                },
                                onClick = {
                                    sort = value
                                    menu = false
                                }
                            )
                        }
                    }
                }
            }
        }

        items(animeList.size) { index ->

            val anime = animeList[index]

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppSurface
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    PosterSurface(
                        anime = anime,
                        modifier = Modifier.size(78.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = anime.title,
                            color = AppText,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Update terbaru tersedia",
                            color = AppBlue,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = "›",
                        color = AppSubText,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SocialLineScreen(
    padding: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(AppBackground),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 25.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppSurface
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            
                            .background(AppBlue),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "L",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Lucky",
                            color = AppText,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Belum ada aktivitas pertemanan.",
                            color = AppSubText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        items(animeList.take(5)) { anime ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppSurface
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(anime.start),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = anime.short.first().toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text(
                                text = "Hibikuroll User",
                                color = AppText,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "baru saja memperbarui aktivitas",
                                color = AppSubText,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Mulai mengikuti ${anime.title}",
                        color = AppText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Baru saja",
                        color = AppBlue,
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
        containerColor = AppBackground,

        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Profil",
                        color = AppText,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {

                    TextButton(
                        onClick = onBack
                    ) {

                        Text(
                            text = "‹",
                            color = AppText,
                            fontSize = 34.sp
                        )
                    }
                },
                actions = {

                    Text(
                        text = "⚙",
                        color = AppText,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(AppBackground),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(82.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFC39A86),
                                        Color(0xFF765E63)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "L",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "Lucky",
                            color = AppText,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Lvl. 1",
                            color = AppSubText
                        )
                    }
                }
            }

            item {
                ProfileEntry("Watch History")
            }

            item {
                ProfileEntry("My Subscriptions")
            }

            item {
                ProfileEntry("Favorites")
            }

            item {
                ProfileEntry("Downloads")
            }

            item {
                ProfileEntry("Notifications")
            }

            item {
                ProfileEntry("Player Settings")
            }

            item {
                ProfileEntry("Appearance")
            }

            item {
                ProfileEntry("Language")
            }

            item {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "About Hibikuroll",
                    color = AppSubText,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ProfileEntry(
    title: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppSurface
        ),
        shape = RoundedCornerShape(15.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                color = AppText,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                color = AppSubText,
                fontSize = 26.sp
            )
        }
    }
}

