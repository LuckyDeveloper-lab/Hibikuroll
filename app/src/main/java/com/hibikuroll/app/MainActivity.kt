package com.hibikuroll.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CalendarMonth
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay

private val Bg = Color(0xFF101114)
private val Surface = Color(0xFF17191E)
private val Surface2 = Color(0xFF1D2027)
private val SurfaceBlue = Color(0xFF202938)
private val Blue = Color(0xFF2497F3)
private val Purple = Color(0xFF65569B)
private val Yellow = Color(0xFFFFD447)
private val TextMain = Color(0xFFF4F4F6)
private val TextSub = Color(0xFF9BA3B1)
private val GreenOutline = Color(0xFF487354)

private enum class Tab(
    val title: String
) {
    HOME("Home"),
    SCHEDULE("Jadwal"),
    SUBSCRIBED("Subscribed"),
    HISTORY("Riwayat"),
    SOCIAL("SocialLine")
}

private val colors = darkColorScheme(
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
            MaterialTheme(colorScheme = colors) {
                HibikurollApp()
            }
        }
    }
}

@Composable
private fun HibikurollApp() {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    var selected by rememberSaveable { mutableStateOf(Tab.HOME) }
    var profileOpen by rememberSaveable { mutableStateOf(false) }

    if (showSplash) {
        HibikurollSplash {
            showSplash = false
        }
        return
    }

    if (profileOpen) {
        ProfileScreen(
            onBack = { profileOpen = false }
        )
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        AnimatedContent(
            targetState = selected,
            label = "screenTransition"
        ) { tab ->
            when (tab) {
                Tab.HOME -> HomeScreen(
                    onProfile = { profileOpen = true }
                )
                Tab.SCHEDULE -> ScheduleScreen()
                Tab.SUBSCRIBED -> SubscribedScreen()
                Tab.HISTORY -> HistoryScreen()
                Tab.SOCIAL -> SocialLineScreen()
            }
        }

        BottomDock(
            selected = selected,
            onSelected = { selected = it },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HibikurollSplash(
    onFinished: () -> Unit
) {
    var phase by remember { mutableStateOf(0) }

    val pulse = rememberInfiniteTransition(label = "splashPulse")
    val pulseScale by pulse.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.035f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    LaunchedEffect(Unit) {
        delay(350)
        phase = 1
        delay(1100)
        phase = 2
        delay(1600)
        phase = 3
        delay(2500)
        phase = 4
        delay(700)
        onFinished()
    }

    val logoAlpha = when {
        phase == 0 -> 0f
        phase >= 1 -> 1f
        else -> 0f
    }

    val nameAlpha = if (phase >= 2) 1f else 0f
    val versionAlpha = if (phase >= 3) 1f else 0f
    val fadeOut = if (phase >= 4) 0f else 1f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .alpha(fadeOut),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = androidx.compose.ui.res.painterResource(
                    R.drawable.hibikuroll_logo
                ),
                contentDescription = "Hibikuroll",
                modifier = Modifier
                    .size(245.dp)
                    .alpha(logoAlpha)
                    .graphicsLayer {
                        scaleX = if (phase >= 2) pulseScale else 0.55f
                        scaleY = if (phase >= 2) pulseScale else 0.55f
                        rotationZ = if (phase == 0) -7f else 0f
                    }
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Hibikuroll",
                color = Yellow,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(nameAlpha)
                    .graphicsLayer {
                        translationY = if (nameAlpha > 0f) 0f else 20f
                    }
            )
        }

        Text(
            text = "v1.1.1 ( 10 )",
            color = Yellow,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .alpha(versionAlpha)
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
            .padding(horizontal = 8.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Tab.values().forEach { tab ->
            val active = selected == tab
            val bg by animateColorAsState(
                if (active) Purple else Color.Transparent,
                label = "dockColor"
            )

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
                    .clip(RoundedCornerShape(26.dp))
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
                    imageVector = icon,
                    contentDescription = tab.title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = tab.title,
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
    onProfile: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var remote by remember { mutableStateOf<List<AnimeRemote>>(emptyList()) }

    LaunchedEffect(Unit) {
        loading = true
        remote = AnimeApi.topAiring()
        loading = false
    }

    LaunchedEffect(query) {
        if (query.length < 2) return@LaunchedEffect

        delay(450)

        val result = AnimeApi.search(query)
        if (result.isNotEmpty()) {
            remote = result
        }
    }

    val list = remote

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            HomeHeader(onProfile)
        }

        item {
            SearchField(
                value = query,
                onValueChange = { query = it }
            )
        }

        item {
            PremiumCard()
        }

        item {
            GiveawayCard()
        }

        item {
            DiscussionBar()
        }

        item {
            SectionHeader(
                title = "Terakhir Ditonton",
                action = "Selengkapnya"
            )
        }

        item {
            ContinueWatching(
                source = list.take(2)
            )
        }

        item {
            SectionHeader(
                title = "New Anime Update",
                action = "Lihat Jadwal"
            )
        }

        item {
            if (loading && list.isEmpty()) {
                LoadingGrid()
            } else {
                AnimeGrid(list)
            }
        }

        item {
            SectionHeader(
                title = "Genre Series"
            )
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
                source = list.take(7)
            )
        }

        item {
            SectionHeader(
                title = "Completed Anime"
            )
        }

        item {
            CompletedCarousel(
                source = list.take(6)
            )
        }
    }
}

@Composable
private fun HomeHeader(
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
                                listOf(
                                    Color(0xFFC9A18F),
                                    Color(0xFF7A5B64)
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
                            modifier = Modifier.size(17.dp)
                        )

                        Spacer(Modifier.width(5.dp))

                        Text(
                            text = "Lvl. 1",
                            color = TextSub,
                            fontSize = 13.sp
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = null,
                    tint = TextMain,
                    modifier = Modifier.size(29.dp)
                )

                Spacer(Modifier.width(17.dp))

                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = TextMain,
                    modifier = Modifier.size(29.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF5C4039),
                                Color(0xFF3B3542)
                            )
                        )
                    )
                    .padding(
                        horizontal = 18.dp,
                        vertical = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✦ 0",
                    color = TextMain,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = "Crystal",
                    color = TextSub,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF61365E))
                        .padding(
                            horizontal = 16.dp,
                            vertical = 9.dp
                        )
                ) {
                    Text(
                        text = "✦ AniGames",
                        color = Yellow,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(34.dp),
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
                tint = TextSub
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {}
        )
    )
}

@Composable
private fun PremiumCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF503A39)
        )
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(67.dp)
                    .clip(RoundedCornerShape(20.dp))
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

            Spacer(Modifier.width(13.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                Text(
                    text = "BELI PREMIUM DI SINI",
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
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF063149)
        )
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = "♛ TOP GIVEAWAY USERS ♛",
                color = Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GiveawayAvatar("#1", "Tama")
                GiveawayAvatar("#2", "flyRoxy")
                GiveawayAvatar("#3", "CL")
            }

            GiveawayLine(1, "『冥』 Tama 愛 Acha", "172481d")
            GiveawayLine(2, "flyRoxy.`", "164333d")
            GiveawayLine(3, "Cl...", "93150d")
        }
    }
}

@Composable
private fun GiveawayAvatar(
    rank: String,
    name: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF47A8E6),
                            Color(0xFF5E4AAB)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.take(2).uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Black
            )
        }

        Text(
            text = rank,
            color = Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun GiveawayLine(
    rank: Int,
    name: String,
    points: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF3B5C6E))
            .padding(
                horizontal = 12.dp,
                vertical = 7.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#$rank",
            color = Yellow,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(36.dp)
        )

        Text(
            text = name,
            color = TextMain,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = points,
            color = Yellow,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DiscussionBar() {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            Text(
                text = "▣ Public Diskusi",
                color = TextMain,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.width(10.dp))

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
private fun ContinueWatching(
    source: List<AnimeRemote>
) {
    val list = source.ifEmpty {
        listOf(
            AnimeRemote(
                id = 21,
                title = "One Piece",
                imageUrl = "",
                score = 8.7,
                episodes = 1179,
                members = 2400000,
                airing = true,
                broadcastDay = null,
                broadcastTime = null
            ),
            AnimeRemote(
                id = 34572,
                title = "Black Clover",
                imageUrl = "",
                score = 8.1,
                episodes = 170,
                members = 56800,
                airing = true,
                broadcastDay = null,
                broadcastTime = null
            )
        )
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list) { anime ->
            Card(
                modifier = Modifier.width(265.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                )
            ) {
                Column {
                    Poster(
                        anime = anime,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )

                    Column(Modifier.padding(11.dp)) {
                        Text(
                            text = anime.title,
                            color = TextMain,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${anime.episodes ?: "-"} Eps • 01:19 / 23:52",
                            color = TextSub,
                            fontSize = 12.sp
                        )

                        Spacer(Modifier.height(7.dp))

                        ProgressBar(0.67f)
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeGrid(
    source: List<AnimeRemote>
) {
    BoxWithConstraints {
        val columns = if (maxWidth < 650.dp) 3 else 5
        val rows = source.chunked(columns)

        Column(
            verticalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    row.forEach { anime ->
                        Box(Modifier.weight(1f)) {
                            AnimeGridCard(anime)
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
    anime: AnimeRemote
) {
    Column {
        Poster(
            anime = anime,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.68f),
            showNew = true,
            showRating = true,
            showEpisodes = true
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = anime.title,
            color = TextMain,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
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
                modifier = Modifier.size(13.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = compactCount(anime.members),
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
            .clip(RoundedCornerShape(17.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF226FE3),
                        Color(0xFF6B408D)
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
                text = anime.title
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
                .fillMaxWidth()
                .height(90.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.82f)
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
                    text = "New",
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
                    .background(Color(0xD9000000))
                    .padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(13.dp)
                    )

                    Spacer(Modifier.width(3.dp))

                    Text(
                        text = if (anime.score > 0) {
                            String.format("%.1f", anime.score)
                        } else {
                            "-"
                        },
                        color = Yellow,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (showEpisodes) {
            Text(
                text = "${anime.episodes ?: "-"} Eps",
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
private fun ProgressBar(
    value: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFF353943))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(value)
                .height(5.dp)
                .background(Blue)
        )
    }
}

@Composable
private fun GenreCloud() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            "Action", "Adult Cast", "Adventure", "Comedy",
            "Magic", "Martial Arts", "Mecha", "Military",
            "Mystery", "Romance", "School", "Sci-Fi",
            "Shoujo", "Shounen", "Sports", "Supernatural",
            "Suspense", "Thriller", "Time Travel", "Movie"
        ).chunked(4).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { genre ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .border(
                                BorderStroke(1.dp, GreenOutline),
                                RoundedCornerShape(22.dp)
                            )
                            .padding(
                                horizontal = 12.dp,
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
private fun WeeklyCarousel(
    source: List<AnimeRemote>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        items(source) { anime ->
            Card(
                modifier = Modifier.width(286.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                )
            ) {
                Box {
                    Poster(
                        anime = anime,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp),
                        showNew = false,
                        showRating = true,
                        showEpisodes = false
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
                            text = "#1",
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
                            text = anime.title,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "◉ ${compactCount(anime.members)} views",
                            color = Color.White.copy(alpha = 0.8f),
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
    source: List<AnimeRemote>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(source) { anime ->
            Box(
                modifier = Modifier.width(155.dp)
            ) {
                Poster(
                    anime = anime,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    showNew = false,
                    showRating = true,
                    showEpisodes = false
                )
            }
        }
    }
}

@Composable
private fun LoadingGrid() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(0.68f)
                    .clip(RoundedCornerShape(17.dp))
                    .background(Surface2)
            )
        }
    }
}

@Composable
private fun ScheduleScreen() {
    var selectedDay by rememberSaveable { mutableStateOf("monday") }
    var data by remember { mutableStateOf<List<AnimeRemote>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    val labels = listOf(
        "monday" to "Senin",
        "tuesday" to "Selasa",
        "wednesday" to "Rabu",
        "thursday" to "Kamis",
        "friday" to "Jumat"
    )

    LaunchedEffect(selectedDay) {
        loading = true
        data = AnimeApi.schedule(selectedDay)
        loading = false
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 20.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Jadwal Tayang",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(labels) { (key, label) ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(
                                if (selectedDay == key) Blue
                                else Surface
                            )
                            .clickable { selectedDay = key }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 12.dp
                            )
                    ) {
                        Text(
                            text = label,
                            color = TextMain,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (loading) {
            item {
                Text(
                    text = "Memuat jadwal...",
                    color = TextSub,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        items(
            if (data.isEmpty()) {
                listOf(
                    AnimeRemote(
                        id = 0,
                        title = "Belum ada data jadwal",
                        imageUrl = "",
                        score = 0.0,
                        episodes = null,
                        members = 0,
                        airing = false,
                        broadcastDay = null,
                        broadcastTime = null
                    )
                )
            } else {
                data.take(8)
            }
        ) { anime ->
            ScheduleItem(anime)
        }
    }
}

@Composable
private fun ScheduleItem(
    anime: AnimeRemote
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
                text = anime.broadcastTime ?: "--:--",
                color = Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(13.dp)
                    .clip(CircleShape)
                    .background(Yellow)
            )

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(120.dp)
                    .background(Color(0xFF58544C))
            )
        }

        Spacer(Modifier.width(7.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(122.dp),
            shape = RoundedCornerShape(21.dp),
            colors = CardDefaults.cardColors(
                containerColor = SurfaceBlue
            )
        ) {
            Row(
                modifier = Modifier.padding(11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Poster(
                    anime = anime,
                    modifier = Modifier.size(87.dp),
                    showNew = false,
                    showRating = false
                )

                Spacer(Modifier.width(11.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = anime.title,
                        color = TextMain,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "${anime.episodes ?: "-"} Eps",
                        color = TextSub,
                        fontSize = 13.sp
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
                            text = compactCount(anime.members),
                            color = TextSub,
                            fontSize = 11.sp
                        )

                        Spacer(Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            tint = Yellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = if (anime.score > 0) {
                                String.format("%.1f", anime.score)
                            } else {
                                "-"
                            },
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
private fun SubscribedScreen() {
    var sort by rememberSaveable { mutableStateOf("Terbaru") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 20.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        item {
            Text(
                text = "Subscribed Anime",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total (8)",
                    color = TextMain,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f)
                )

                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text(
                            text = sort,
                            color = TextMain
                        )
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null,
                            tint = TextSub
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Terbaru", "A-Z", "Rating").forEach { label ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    sort = label
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        items(
            listOf(
                "One Piece", "Black Clover",
                "Naruto: Shippuuden", "Bleach",
                "Mushoku Tensei", "Neon Samurai",
                "Digimon Beatbreak", "Hanaori-san"
            )
        ) { title ->
            SubscriptionCard(title)
        }
    }
}

@Composable
private fun SubscriptionCard(
    title: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                title = title,
                modifier = Modifier.size(92.dp)
            )

            Spacer(Modifier.width(13.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
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
                        Color(0xFF1F71D7),
                        Color(0xFF774A92)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title
                .split(" ")
                .take(2)
                .joinToString("") { it.take(1) }
                .uppercase(),
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 21.sp
        )
    }
}

@Composable
private fun HistoryScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 20.dp,
            bottom = 120.dp
        ),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        item {
            Text(
                text = "Riwayat Menonton",
                color = TextMain,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tahan series untuk pilih & hapus",
                color = TextSub,
                fontSize = 13.sp
            )
        }

        item { DayLabel("Hari ini") }

        items(
            listOf(
                "One Piece" to 1f,
                "Black Clover" to .38f
            )
        ) { (title, progress) ->
            HistoryCard(title, progress)
        }

        item { DayLabel("Kemarin") }

        items(
            listOf(
                "Naruto: Shippuuden" to .42f,
                "Bleach" to .29f
            )
        ) { (title, progress) ->
            HistoryCard(title, progress)
        }
    }
}

@Composable
private fun DayLabel(
    text: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Blue)
            .padding(
                horizontal = 20.dp,
                vertical = 11.dp
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HistoryCard(
    title: String,
    progress: Float
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    text = title,
                    color = TextMain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "1179 Eps • Hari ini",
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = 120.dp
            )
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
                                        Color(0xFFC9A18F),
                                        Color(0xFF7A5B64)
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
                        Text(
                            text = "Lucky",
                            color = TextMain,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )

                        Text(
                            text = "Belum ada aktivitas pertemanan.",
                            color = TextSub,
                            fontSize = 13.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        tint = TextMain,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            item {
                Spacer(Modifier.height(250.dp))

                Text(
                    text = "Belum ada aktivitas pertemanan.",
                    color = TextSub,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Mulai follow teman untuk lihat update mereka",
                    color = TextSub,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 92.dp
                )
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF2462C7))
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 20.dp,
            bottom = 30.dp
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
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextMain
                    )
                }

                Text(
                    text = "Profil",
                    color = TextMain,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    tint = TextMain
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
                                    Color(0xFFC9A18F),
                                    Color(0xFF7A5B64)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "L",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Lucky",
                        color = TextMain,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Lvl. 1  •  #1485221",
                        color = TextSub
                    )
                }
            }
        }

        item {
            ProfileItem("Watch History", Icons.Outlined.History)
        }

        item {
            ProfileItem("My Subscriptions", Icons.Outlined.BookmarkBorder)
        }

        item {
            ProfileItem("Favorites", Icons.Outlined.FavoriteBorder)
        }

        item {
            ProfileItem("Downloads", Icons.Outlined.Download)
        }

        item {
            ProfileItem("Notifications", Icons.Outlined.NotificationsNone)
        }

        item {
            ProfileItem("Player Settings", Icons.Outlined.SmartDisplay)
        }

        item {
            ProfileItem("Appearance", Icons.Outlined.Palette)
        }

        item {
            ProfileItem("Language", Icons.Outlined.Language)
        }

        item {
            ProfileItem("Clear History", Icons.Outlined.DeleteOutline)
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
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextSub
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
                tint = TextSub
            )
        }
    }
}

private fun compactCount(value: Int): String {
    return when {
        value >= 1_000_000 -> {
            String.format("%.1fM", value / 1_000_000.0)
        }
        value >= 1_000 -> {
            String.format("%.1fK", value / 1_000.0)
        }
        else -> value.toString()
    }
}
