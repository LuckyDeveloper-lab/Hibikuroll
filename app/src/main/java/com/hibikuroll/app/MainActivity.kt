package com.hibikuroll.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateDpAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay

private val Bg = Color(0xFF0F1014)
private val Surface = Color(0xFF17191E)
private val Surface2 = Color(0xFF1C1E25)
private val SurfaceBlue = Color(0xFF202A3A)
private val Purple = Color(0xFF67579D)
private val Blue = Color(0xFF2498F3)
private val Yellow = Color(0xFFFFD447)
private val TextMain = Color(0xFFF3F3F5)
private val TextSub = Color(0xFFA0A7B5)
private val Green = Color(0xFF4A7456)

private enum class Tab(val title: String) {
    HOME("Home"),
    SCHEDULE("Jadwal"),
    SUBSCRIBED("Subscribed"),
    HISTORY("Riwayat"),
    SOCIAL("SocialLine")
}

private enum class ExtraScreen {
    PROFILE,
    ANIGAMES,
    PREMIUM,
    RANKING,
    GIVEAWAY,
    DISCUSSION,
    DETAIL
}

private val hibikurollColors = darkColorScheme(
    primary = Blue,
    secondary = Purple,
    background = Bg,
    surface = Surface,
    onBackground = TextMain,
    onSurface = TextMain
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(colorScheme = hibikurollColors) {
                HibikurollApp()
            }
        }
    }
}

@Composable
private fun HibikurollApp() {
    var splashVisible by rememberSaveable { mutableStateOf(true) }
    var selectedTab by rememberSaveable { mutableStateOf(Tab.HOME) }
    var extra by rememberSaveable { mutableStateOf<ExtraScreen?>(null) }
    var detailTitle by rememberSaveable { mutableStateOf("") }

    if (splashVisible) {
        HibikurollLaunchSplash {
            splashVisible = false
        }
        return
    }

    if (extra != null) {
        when (extra) {
            ExtraScreen.PROFILE -> ProfileScreen { extra = null }
            ExtraScreen.ANIGAMES -> AniGamesScreen { extra = null }
            ExtraScreen.PREMIUM -> PremiumScreen { extra = null }
            ExtraScreen.RANKING -> RankingScreen { extra = null }
            ExtraScreen.GIVEAWAY -> GiveawayScreen { extra = null }
            ExtraScreen.DISCUSSION -> DiscussionScreen { extra = null }
            ExtraScreen.DETAIL -> DetailScreen(
                title = detailTitle,
                onBack = { extra = null }
            )
            null -> Unit
        }
        return
    }

    Box(Modifier.fillMaxSize().background(Bg)) {
        AnimatedContent(
            targetState = selectedTab,
            label = "mainTabs"
        ) { tab ->
            when (tab) {
                Tab.HOME -> HomeScreen(
                    onProfile = { extra = ExtraScreen.PROFILE },
                    onAniGames = { extra = ExtraScreen.ANIGAMES },
                    onPremium = { extra = ExtraScreen.PREMIUM },
                    onRanking = { extra = ExtraScreen.RANKING },
                    onGiveaway = { extra = ExtraScreen.GIVEAWAY },
                    onDiscussion = { extra = ExtraScreen.DISCUSSION },
                    onDetail = {
                        detailTitle = it
                        extra = ExtraScreen.DETAIL
                    }
                )
                Tab.SCHEDULE -> ScheduleScreen(
                    onDetail = {
                        detailTitle = it
                        extra = ExtraScreen.DETAIL
                    }
                )
                Tab.SUBSCRIBED -> SubscribedScreen(
                    onDetail = {
                        detailTitle = it
                        extra = ExtraScreen.DETAIL
                    }
                )
                Tab.HISTORY -> HistoryScreen(
                    onDetail = {
                        detailTitle = it
                        extra = ExtraScreen.DETAIL
                    }
                )
                Tab.SOCIAL -> SocialLineScreen()
            }
        }

        BottomDock(
            selected = selectedTab,
            onSelected = { selectedTab = it },
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
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Tab.values().forEach { tab ->
            val active = selected == tab
            val bg by animateColorAsState(
                targetValue = if (active) Purple else Color.Transparent,
                animationSpec = tween(220),
                label = "navBg"
            )
            val width by animateDpAsState(
                targetValue = if (active) 106.dp else 62.dp,
                animationSpec = tween(220),
                label = "navWidth"
            )

            Column(
                modifier = Modifier
                    .width(width)
                    .clip(RoundedCornerShape(31.dp))
                    .background(bg)
                    .clickable(
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) { onSelected(tab) }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = when (tab) {
                        Tab.HOME -> Icons.Outlined.Home
                        Tab.SCHEDULE -> Icons.Outlined.CalendarMonth
                        Tab.SUBSCRIBED -> Icons.Outlined.SmartDisplay
                        Tab.HISTORY -> Icons.Outlined.History
                        Tab.SOCIAL -> Icons.Outlined.Timeline
                    },
                    contentDescription = tab.title,
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )

                Text(
                    text = tab.title,
                    color = if (active) Color.White else TextSub,
                    fontSize = 10.sp,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun SearchPill(
    value: String,
    onChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(31.dp))
            .border(
                BorderStroke(1.5.dp, Color(0xFF7A7E88)),
                RoundedCornerShape(31.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = TextSub,
                modifier = Modifier.size(23.dp)
            )
            Spacer(Modifier.width(12.dp))

            BasicTextField(
                value = value,
                onValueChange = onChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = TextMain,
                    fontSize = 17.sp
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { inner ->
                    if (value.isBlank()) {
                        Text(
                            text = "Mencari anime",
                            color = TextSub,
                            fontSize = 17.sp
                        )
                    }
                    inner()
                }
            )
        }
    }
}

@Composable
private fun HomeScreen(
    onProfile: () -> Unit,
    onAniGames: () -> Unit,
    onPremium: () -> Unit,
    onRanking: () -> Unit,
    onGiveaway: () -> Unit,
    onDiscussion: () -> Unit,
    onDetail: (String) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var data by remember { mutableStateOf<List<AnimeRemote>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        loading = true
        data = AnimeApi.topAiring()
        loading = false
    }

    LaunchedEffect(query) {
        if (query.length < 2) return@LaunchedEffect
        delay(450)
        val result = AnimeApi.search(query)
        if (result.isNotEmpty()) data = result
    }

    val items = data.ifEmpty { fallbackAnime() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            HomeHeader(
                onProfile = onProfile,
                onAniGames = onAniGames
            )
        }

        item {
            SearchPill(query) { query = it }
        }

        item {
            PremiumCard(onClick = onPremium)
        }

        item {
            FeatureCarousel(
                onRanking = onRanking,
                onGiveaway = onGiveaway
            )
        }

        item {
            DiscussionBar(onClick = onDiscussion)
        }

        item {
            SectionHeader(
                title = "Terakhir Ditonton",
                action = "Selengkapnya"
            )
        }

        item {
            ContinueWatching(
                source = items.take(4),
                onDetail = onDetail
            )
        }

        item {
            SectionHeader(
                title = "New Anime Update",
                action = "Lihat Jadwal"
            )
        }

        item {
            if (loading && data.isEmpty()) {
                LoadingStrip()
            } else {
                AnimeGrid(
                    source = items.take(12),
                    onDetail = onDetail
                )
            }
        }

        item {
            SectionHeader("Genre Series")
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
            WeeklyCarousel(
                source = items.take(6),
                onDetail = onDetail
            )
        }

        item {
            SectionHeader("Completed Anime")
        }

        item {
            CompletedCarousel(
                source = items.take(7),
                onDetail = onDetail
            )
        }
    }
}

@Composable
private fun HomeHeader(
    onProfile: () -> Unit,
    onAniGames: () -> Unit
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
                                listOf(
                                    Color(0xFFC59D8B),
                                    Color(0xFF705963)
                                )
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

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Lucky",
                            color = TextMain,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(7.dp))
                        Text(
                            "#1485221",
                            color = TextSub,
                            fontSize = 12.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Cloud,
                            contentDescription = null,
                            tint = TextSub,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            "Lvl. 1",
                            color = TextSub,
                            fontSize = 13.sp
                        )
                    }
                }

                Icon(
                    Icons.Outlined.NotificationsNone,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(Modifier.width(17.dp))

                Icon(
                    Icons.Outlined.Search,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF5D413C),
                                Color(0xFF3B3542)
                            )
                        )
                    )
                    .padding(
                        horizontal = 17.dp,
                        vertical = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "✦ 0",
                    color = TextMain,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "Crystal",
                    color = TextSub,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF60365F))
                        .clickable { onAniGames() }
                        .padding(
                            horizontal = 17.dp,
                            vertical = 10.dp
                        )
                ) {
                    Text(
                        "✦ AniGames",
                        color = Yellow,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun PremiumCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF503B3A)
        )
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(21.dp))
                    .background(Yellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "♟",
                    color = Color.Black,
                    fontSize = 28.sp
                )
            }

            Spacer(Modifier.width(13.dp))

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                Text(
                    "BELI PREMIUM DI SINI",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun FeatureCarousel(
    onRanking: () -> Unit,
    onGiveaway: () -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            FeatureCard(
                title = "PERINGKAT ANIME",
                subtitle = "Weekly anime ranking",
                accent = Blue,
                onClick = onRanking
            )
        }

        item {
            FeatureCard(
                title = "TOP GIVEAWAY",
                subtitle = "Top 3 spender",
                accent = Yellow,
                onClick = onGiveaway
            )
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    subtitle: String,
    accent: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(310.dp)
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface2
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                color = accent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                subtitle,
                color = TextSub,
                fontSize = 14.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.20f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (accent == Yellow)
                            Icons.Outlined.Star
                        else
                            Icons.Outlined.Timeline,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(Modifier.width(9.dp))

                Text(
                    "Buka halaman",
                    color = TextMain,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    Icons.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = accent
                )
            }
        }
    }
}

@Composable
private fun DiscussionBar(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 15.dp,
                vertical = 14.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.ChatBubbleOutline,
                contentDescription = null,
                tint = Blue
            )

            Spacer(Modifier.width(10.dp))

            Text(
                "Public Diskusi",
                color = TextMain,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.width(9.dp))

            Text(
                "|  青 | Fabian. : TF??",
                color = TextSub,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                Icons.Outlined.ArrowForward,
                null,
                tint = Yellow
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    action: String = ""
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            color = TextMain,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (action.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    action,
                    color = Blue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    Icons.Outlined.ChevronRight,
                    null,
                    tint = Blue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun ContinueWatching(
    source: List<AnimeRemote>,
    onDetail: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(source) { anime ->
            Card(
                modifier = Modifier
                    .width(270.dp)
                    .clickable { onDetail(anime.title) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                )
            ) {
                Column {
                    Poster(
                        anime,
                        Modifier
                            .fillMaxWidth()
                            .height(148.dp)
                    )

                    Column(Modifier.padding(11.dp)) {
                        Text(
                            anime.title,
                            color = TextMain,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            "${anime.episodes ?: "-"} Eps  •  01:19 / 23:52",
                            color = TextSub,
                            fontSize = 12.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        ProgressBar(.67f)
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeGrid(
    source: List<AnimeRemote>,
    onDetail: (String) -> Unit
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val columns = if (maxWidth >= 600.dp) 5 else 3
        val rows = source.chunked(columns)

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { anime ->
                        Box(Modifier.weight(1f)) {
                            AnimeGridCard(
                                anime = anime,
                                onClick = { onDetail(anime.title) }
                            )
                        }
                    }

                    repeat(columns - row.size) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeGridCard(
    anime: AnimeRemote,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Poster(
            anime,
            Modifier
                .fillMaxWidth()
                .aspectRatio(.69f),
            showNew = true,
            showRating = true,
            showEpisodes = true
        )

        Spacer(Modifier.height(6.dp))

        Text(
            anime.title,
            color = TextMain,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Outlined.Visibility,
                null,
                tint = TextSub,
                modifier = Modifier.size(12.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                compactCount(anime.members),
                color = TextSub,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun Poster(
    anime: AnimeRemote,
    modifier: Modifier,
    showNew: Boolean = false,
    showRating: Boolean = true,
    showEpisodes: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF246FD4),
                        Color(0xFF7B438D)
                    )
                )
            )
    ) {
        if (anime.imageUrl.isNotBlank()) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                anime.title
                    .split(" ")
                    .take(2)
                    .joinToString("") { it.take(1) }
                    .uppercase(),
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(85.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = .85f)
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
                            bottomEnd = 16.dp
                        )
                    )
                    .background(Blue)
                    .padding(
                        horizontal = 10.dp,
                        vertical = 7.dp
                    )
            ) {
                Text(
                    "New",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (showRating) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(7.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xC9000000))
                    .padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Star,
                        null,
                        tint = Yellow,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        if (anime.score > 0)
                            String.format("%.1f", anime.score)
                        else "-",
                        color = Yellow,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (showEpisodes) {
            Text(
                "${anime.episodes ?: "-"} Eps",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(9.dp)
            )
        }
    }
}

@Composable
private fun ProgressBar(value: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFF343844))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(value.coerceIn(0f, 1f))
                .height(5.dp)
                .background(Blue)
        )
    }
}

@Composable
private fun GenreCloud() {
    val genres = listOf(
        "Action", "Adult Cast", "Adventure", "Comedy",
        "Magic", "Martial Arts", "Mecha", "Military",
        "Mystery", "Romance", "School", "Sci-Fi",
        "Shoujo", "Shounen", "Sports", "Supernatural",
        "Suspense", "Thriller", "Time Travel", "Movie"
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        genres.chunked(4).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                row.forEach { genre ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .border(
                                BorderStroke(1.dp, Green),
                                RoundedCornerShape(22.dp)
                            )
                            .padding(
                                horizontal = 11.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Text(
                            genre,
                            color = TextMain,
                            fontSize = 10.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyCarousel(
    source: List<AnimeRemote>,
    onDetail: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(source) { anime ->
            Card(
                modifier = Modifier
                    .width(290.dp)
                    .clickable { onDetail(anime.title) },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                )
            ) {
                Box {
                    Poster(
                        anime,
                        Modifier
                            .fillMaxWidth()
                            .height(190.dp),
                        showRating = true
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
                            .padding(
                                horizontal = 14.dp,
                                vertical = 9.dp
                            )
                    ) {
                        Text(
                            "#1",
                            color = Color.Black,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            anime.title,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            "◉ ${compactCount(anime.members)} views",
                            color = Color.White.copy(alpha = .82f),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CompletedCarousel(
    source: List<AnimeRemote>,
    onDetail: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(source) { anime ->
            Box(
                modifier = Modifier
                    .width(155.dp)
                    .clickable { onDetail(anime.title) }
            ) {
                Poster(
                    anime,
                    Modifier
                        .fillMaxWidth()
                        .height(224.dp)
                )
            }
        }
    }
}

@Composable
private fun LoadingStrip() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(.69f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Surface2)
            )
        }
    }
}

@Composable
private fun ScheduleScreen(
    onDetail: (String) -> Unit
) {
    var day by rememberSaveable { mutableStateOf("monday") }
    var schedule by remember { mutableStateOf<List<AnimeRemote>>(emptyList()) }

    val labels = listOf(
        "monday" to "Senin",
        "tuesday" to "Selasa",
        "wednesday" to "Rabu",
        "thursday" to "Kamis",
        "friday" to "Jumat",
        "saturday" to "Sabtu",
        "sunday" to "Minggu"
    )

    LaunchedEffect(day) {
        schedule = AnimeApi.schedule(day)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Jadwal Tayang",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(labels) { (key, name) ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(
                                if (day == key) Blue else Surface
                            )
                            .clickable { day = key }
                            .padding(
                                horizontal = 18.dp,
                                vertical = 11.dp
                            )
                    ) {
                        Text(
                            name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        val list = schedule.ifEmpty {
            fallbackAnime().take(5)
        }

        items(list) { anime ->
            TimelineScheduleItem(
                anime = anime,
                onDetail = { onDetail(anime.title) }
            )
        }
    }
}

@Composable
private fun TimelineScheduleItem(
    anime: AnimeRemote,
    onDetail: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.width(68.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                anime.broadcastTime ?: "--:--",
                color = Yellow,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(Yellow)
            )

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(111.dp)
                    .background(Color(0xFF5A554D))
            )
        }

        Spacer(Modifier.width(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(118.dp)
                .clickable { onDetail() },
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = SurfaceBlue
            )
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Poster(
                    anime,
                    Modifier.size(88.dp),
                    showRating = false
                )

                Spacer(Modifier.width(11.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        anime.title,
                        color = TextMain,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        "${anime.episodes ?: "-"} Eps",
                        color = TextSub,
                        fontSize = 13.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Visibility,
                            null,
                            tint = TextSub,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            compactCount(anime.members),
                            color = TextSub,
                            fontSize = 11.sp
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            Icons.Outlined.Star,
                            null,
                            tint = Yellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            if (anime.score > 0)
                                String.format("%.1f", anime.score)
                            else "-",
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
    onDetail: (String) -> Unit
) {
    var sort by rememberSaveable { mutableStateOf("Terbaru") }
    var menuOpen by rememberSaveable { mutableStateOf(false) }

    val titles = listOf(
        "One Piece", "Black Clover",
        "Naruto: Shippuuden", "Bleach",
        "Mushoku Tensei", "Neon Samurai",
        "Digimon Beatbreak", "Hanaori-san"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                "Subscribed",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total (${titles.size})",
                    color = TextMain,
                    fontSize = 23.sp,
                    modifier = Modifier.weight(1f)
                )

                Box {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { menuOpen = true }
                            .background(Surface)
                            .padding(
                                horizontal = 13.dp,
                                vertical = 9.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(sort, color = TextMain)
                        Icon(
                            Icons.Outlined.KeyboardArrowDown,
                            null,
                            tint = TextSub
                        )
                    }

                    DropdownMenu(
                        expanded = menuOpen,
                        onDismissRequest = { menuOpen = false }
                    ) {
                        listOf("Terbaru", "A-Z", "Rating").forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    sort = it
                                    menuOpen = false
                                }
                            )
                        }
                    }
                }
            }
        }

        items(titles) { title ->
            SubscriptionCard(
                title = title,
                onClick = { onDetail(title) }
            )
        }
    }
}

@Composable
private fun SubscriptionCard(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlaceholderPoster(
                title,
                Modifier.size(92.dp)
            )

            Spacer(Modifier.width(13.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    color = TextMain,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    "Update terbaru tersedia",
                    color = Blue,
                    fontSize = 13.sp
                )
            }

            Icon(
                Icons.Outlined.ChevronRight,
                null,
                tint = TextSub
            )
        }
    }
}

@Composable
private fun PlaceholderPoster(
    title: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF2775E5),
                        Color(0xFF6A4390)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            title
                .split(" ")
                .take(2)
                .joinToString("") { it.take(1) }
                .uppercase(),
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun HistoryScreen(
    onDetail: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                "Riwayat Menonton",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Tahan series untuk pilih & hapus",
                color = TextSub,
                fontSize = 13.sp
            )
        }

        item {
            DayTag("Hari ini")
        }

        items(
            listOf(
                "One Piece" to 1f,
                "Black Clover" to .38f
            )
        ) { item ->
            HistoryCard(
                title = item.first,
                progress = item.second,
                onClick = { onDetail(item.first) }
            )
        }

        item {
            DayTag("Kemarin")
        }

        items(
            listOf(
                "Hanaori-san wa Tensei shitemo Kenka ga Shitai" to .12f,
                "Naruto: Shippuuden" to .43f
            )
        ) { item ->
            HistoryCard(
                title = item.first,
                progress = item.second,
                onClick = { onDetail(item.first) }
            )
        }
    }
}

@Composable
private fun DayTag(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Blue)
            .padding(
                horizontal = 19.dp,
                vertical = 10.dp
            )
    ) {
        Text(
            text,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HistoryCard(
    title: String,
    progress: Float,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlaceholderPoster(
                title,
                Modifier.size(92.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    color = TextMain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    "Episode 1  •  Hari ini",
                    color = TextSub
                )

                Spacer(Modifier.height(9.dp))
                ProgressBar(progress)
            }
        }
    }
}

@Composable
private fun SocialLineScreen() {
    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 120.dp
            ),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            item {
                Text(
                    "SocialLine",
                    color = TextMain,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SocialCard(
                    avatar = "L",
                    name = "Lucky",
                    text = "Belum ada aktivitas pertemanan."
                )
            }

            item {
                SocialCard(
                    avatar = "O",
                    name = "Hibikuroll User",
                    text = "Mulai mengikuti One Piece"
                )
            }

            item {
                SocialCard(
                    avatar = "B",
                    name = "Hibikuroll User",
                    text = "Mulai mengikuti Black Clover"
                )
            }

            item {
                SocialCard(
                    avatar = "N",
                    name = "Hibikuroll User",
                    text = "Mulai mengikuti Naruto: Shippuuden"
                )
            }

            item {
                SocialCard(
                    avatar = "B",
                    name = "Hibikuroll User",
                    text = "Mulai mengikuti Bleach"
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 17.dp,
                    bottom = 97.dp
                )
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF2464C9))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.People,
                null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun SocialCard(
    avatar: String,
    name: String,
    text: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(23.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    avatar,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    name,
                    color = TextMain,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "baru saja memperbarui aktivitas",
                    color = TextSub,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(9.dp))

                Text(
                    text,
                    color = TextMain,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "Baru saja",
                    color = Blue,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ProfileScreen(
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 26.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        null,
                        tint = TextMain
                    )
                }

                Text(
                    "Profil",
                    color = TextMain,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    Icons.Outlined.Settings,
                    null,
                    tint = TextMain
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFFC69E8B),
                                    Color(0xFF72575E)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "L",
                        color = Color.White,
                        fontSize = 37.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(13.dp))

                Column {
                    Text(
                        "Lucky",
                        color = TextMain,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Lvl. 1  •  #1485221",
                        color = TextSub
                    )
                }
            }
        }

        item {
            ProfileStatRow()
        }

        item { ProfileItem("Riwayat", Icons.Outlined.History) }
        item { ProfileItem("Subscribed", Icons.Outlined.BookmarkBorder) }
        item { ProfileItem("Favorit", Icons.Outlined.FavoriteBorder) }
        item { ProfileItem("Download", Icons.Outlined.Download) }
        item { ProfileItem("Notifikasi", Icons.Outlined.NotificationsNone) }
        item { ProfileItem("Tampilan", Icons.Outlined.Palette) }
        item { ProfileItem("Bahasa", Icons.Outlined.Language) }
        item { ProfileItem("Hapus riwayat", Icons.Outlined.DeleteOutline) }
    }
}

@Composable
private fun ProfileStatRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileStat(Modifier.weight(1f), "1", "hari")
        ProfileStat(Modifier.weight(1f), "0", "komentar")
        ProfileStat(Modifier.weight(1f), "2", "riwayat")
        ProfileStat(Modifier.weight(1f), "24", "menit")
    }
}

@Composable
private fun ProfileStat(
    modifier: Modifier,
    value: String,
    label: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(17.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                value,
                color = TextMain,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                label,
                color = TextSub,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun ProfileItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(17.dp)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                null,
                tint = TextSub
            )

            Spacer(Modifier.width(12.dp))

            Text(
                title,
                color = TextMain,
                modifier = Modifier.weight(1f)
            )

            Icon(
                Icons.Outlined.ChevronRight,
                null,
                tint = TextSub
            )
        }
    }
}

@Composable
private fun AniGamesScreen(
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        item {
            DetailTopBar("AniGames", onBack)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceBlue)
            ) {
                Column(Modifier.padding(15.dp)) {
                    Text(
                        "AniGames",
                        color = TextMain,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Ruang fitur sosial dan kosmetik Hibikuroll",
                        color = TextSub
                    )
                }
            }
        }

        item {
            GameGrid()
        }
    }
}

@Composable
private fun GameGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GameTile(
                modifier = Modifier.weight(1f),
                icon = "🛡",
                title = "Clans",
                subtitle = "Gabung squad",
                color = Blue
            )
            GameTile(
                modifier = Modifier.weight(1f),
                icon = "🐾",
                title = "AniPet",
                subtitle = "Koleksi pet",
                color = Yellow
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GameTile(
                modifier = Modifier.weight(1f),
                icon = "✦",
                title = "Gacha Wallpaper",
                subtitle = "Spin banner",
                color = Color(0xFFC42E78)
            )
            GameTile(
                modifier = Modifier.weight(1f),
                icon = "◉",
                title = "Avatar Border",
                subtitle = "Shop decoration",
                color = Purple
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GameTile(
                modifier = Modifier.weight(1f),
                icon = "✦",
                title = "Profile Effects",
                subtitle = "Efek profil",
                color = Color(0xFFC52C24)
            )
            GameTile(
                modifier = Modifier.weight(1f),
                icon = "i",
                title = "Tutorial",
                subtitle = "Panduan",
                color = SurfaceBlue
            )
        }
    }
}

@Composable
private fun GameTile(
    modifier: Modifier = Modifier,
    icon: String,
    title: String,
    subtitle: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = .88f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(icon, fontSize = 30.sp)
            Column {
                Text(
                    title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    subtitle,
                    color = Color.White.copy(alpha = .78f),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun PremiumScreen(
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        item {
            DetailTopBar("Anibi Premium", onBack)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(23.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(
                    Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .size(58.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Yellow, Blue)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "A",
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Anibi Premium",
                        color = Yellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Upgrade premium dalam satu halaman.",
                        color = TextSub,
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(14.dp))

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(17.dp)
                    ) {
                        Text("BELI PREMIUM")
                    }
                }
            }
        }

        item {
            PremiumStep("LANGKAH 1", "Masuk dengan akun Hibikuroll.")
        }

        item {
            PremiumStep("LANGKAH 2", "Pilih durasi premium yang diinginkan.")
        }

        item {
            PremiumStep("LANGKAH 3", "Konfirmasi pembayaran.")
        }

        item {
            BenefitCard()
        }
    }
}

@Composable
private fun PremiumStep(
    number: String,
    text: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(Modifier.padding(15.dp)) {
            Text(
                number,
                color = Blue,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text,
                color = TextSub,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun BenefitCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Surface2)
    ) {
        Column(Modifier.padding(15.dp)) {
            Text(
                "✦ Benefit Premium",
                color = Yellow,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(5.dp))
            listOf(
                "Bonus EXP 50%",
                "Ubah nama dan tampilan profil lebih bebas",
                "Background profil dan foto profil lebih fleksibel"
            ).forEach {
                Text(
                    "• $it",
                    color = TextSub,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun RankingScreen(
    onBack: () -> Unit
) {
    val list = fallbackAnime()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            DetailTopBar("Peringkat Anime", onBack)
        }

        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RankTab("All Time", true)
                RankTab("Mingguan", false)
                RankTab("Bulanan", false)
            }
        }

        items(list.take(6)) { anime ->
            RankingCard(
                anime = anime,
                rank = list.indexOf(anime) + 1
            )
        }
    }
}

@Composable
private fun RankTab(
    title: String,
    active: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (active) Blue else Surface)
            .padding(
                horizontal = 14.dp,
                vertical = 10.dp
            )
    ) {
        Text(
            title,
            color = TextMain,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RankingCard(
    anime: AnimeRemote,
    rank: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column {
            Box {
                Poster(
                    anime,
                    Modifier
                        .fillMaxWidth()
                        .height(if (rank == 1) 230.dp else 155.dp),
                    showRating = true
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(
                            if (rank == 1) Yellow
                            else Color(0xCC22252C)
                        )
                        .padding(
                            horizontal = 11.dp,
                            vertical = 7.dp
                        )
                ) {
                    Text(
                        "#$rank",
                        color = if (rank == 1)
                            Color.Black
                        else
                            Color.White,
                        fontWeight = FontWeight.Black
                    )
                }

                Text(
                    anime.title,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                )
            }

            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${anime.episodes ?: "-"} Eps",
                    color = TextSub
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    "•  ${compactCount(anime.members)} views",
                    color = TextSub
                )
            }
        }
    }
}

@Composable
private fun GiveawayScreen(
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            DetailTopBar("Top Giveaway", onBack)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF063149)
                )
            ) {
                Column(Modifier.padding(13.dp)) {
                    Text(
                        "♛ Top 3 Spender",
                        color = Yellow,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        GiveawayAvatar("flyRoxy.", "#2")
                        GiveawayAvatar("Tama 愛", "#1")
                        GiveawayAvatar("Claude", "#3")
                    }
                }
            }
        }

        item {
            Text(
                "Semua Donatur",
                color = TextMain,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(
            listOf(
                "蒸 WTinter", "Tante Witch", "Xianlie",
                "Tidur 100th", "%BfBjj", "off=mati", "Mideka"
            )
        ) { name ->
            DonorRow(name)
        }
    }
}

@Composable
private fun GiveawayAvatar(
    name: String,
    rank: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(Blue, Purple)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                name.take(2),
                color = Color.White,
                fontWeight = FontWeight.Black
            )
        }

        Text(
            rank,
            color = Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun DonorRow(name: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SurfaceBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    name.take(1),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(10.dp))

            Text(
                name,
                color = TextMain,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                "${(80015..172481).random()} hari",
                color = Yellow,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DiscussionScreen(
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            DetailTopBar("Public Diskusi", onBack)
        }

        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RankTab("Global", false)
                RankTab("Rekrut Clan", false)
                RankTab("Clan", true)
                RankTab("Teman", false)
            }
        }

        items(
            listOf(
                "gby nyokot em kul kids",
                "kapan jdd v7 ghen?",
                "mending lanjut episode ini",
                "One Piece update baru"
            )
        ) { text ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(19.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                )
            ) {
                Column(Modifier.padding(13.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                                .background(Blue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text.first().uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        Column {
                            Text(
                                "Hibikuroll User",
                                color = TextMain,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "baru saja",
                                color = TextSub,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(9.dp))

                    Text(
                        text,
                        color = TextMain,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailScreen(
    title: String,
    onBack: () -> Unit
) {
    var episodeQuery by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        item {
            DetailTopBar(
                title = title.ifBlank { "Anime" },
                onBack = onBack
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PlaceholderPoster(
                    title,
                    Modifier.size(116.dp)
                )

                Column(Modifier.weight(1f)) {
                    Text(
                        title.ifBlank { "Anime" },
                        color = TextMain,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Daftar Episode",
                        color = TextSub,
                        fontSize = 13.sp
                    )

                    Text(
                        "Pilih episode untuk mulai menonton",
                        color = TextSub,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            SearchPill(
                episodeQuery
            ) { episodeQuery = it }
        }

        items(
            (12 downTo 1).filter {
                episodeQuery.isBlank() ||
                    "Episode $it".contains(
                        episodeQuery,
                        ignoreCase = true
                    )
            }
        ) { episode ->
            EpisodeRow(episode)
        }
    }
}

@Composable
private fun EpisodeRow(
    episode: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceBlue
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "Episode $episode",
                    color = TextMain,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "21 September 2026  •  ${compactCount((2370..7900).random())}",
                    color = TextSub,
                    fontSize = 11.sp
                )
            }

            Box(
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Blue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.PlayArrow,
                    null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun DetailTopBar(
    title: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                Icons.Outlined.ArrowBack,
                null,
                tint = TextMain
            )
        }

        Text(
            title,
            color = TextMain,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

private fun fallbackAnime(): List<AnimeRemote> {
    return listOf(
        AnimeRemote(
            id = 21,
            title = "One Piece",
            imageUrl = "",
            score = 8.7,
            episodes = 1179,
            members = 2400000,
            airing = true,
            broadcastDay = "Sunday",
            broadcastTime = "10:00"
        ),
        AnimeRemote(
            id = 34572,
            title = "Black Clover",
            imageUrl = "",
            score = 8.1,
            episodes = 170,
            members = 56800,
            airing = true,
            broadcastDay = "Sunday",
            broadcastTime = "18:57"
        ),
        AnimeRemote(
            id = 20,
            title = "Naruto: Shippuuden",
            imageUrl = "",
            score = 8.3,
            episodes = 500,
            members = 63800,
            airing = false,
            broadcastDay = null,
            broadcastTime = "19:00"
        ),
        AnimeRemote(
            id = 269,
            title = "Bleach",
            imageUrl = "",
            score = 7.9,
            episodes = 365,
            members = 46000,
            airing = false,
            broadcastDay = null,
            broadcastTime = "20:30"
        ),
        AnimeRemote(
            id = 5114,
            title = "Fullmetal Alchemist: Brotherhood",
            imageUrl = "",
            score = 9.1,
            episodes = 64,
            members = 900000,
            airing = false,
            broadcastDay = null,
            broadcastTime = "21:30"
        ),
        AnimeRemote(
            id = 146066,
            title = "Mushoku Tensei",
            imageUrl = "",
            score = 8.8,
            episodes = 23,
            members = 187600,
            airing = true,
            broadcastDay = null,
            broadcastTime = "22:00"
        )
    )
}

private fun compactCount(value: Int): String {
    return when {
        value >= 1_000_000 ->
            String.format("%.1fM", value / 1_000_000.0)
        value >= 1_000 ->
            String.format("%.1fK", value / 1_000.0)
        else -> value.toString()
    }
}
