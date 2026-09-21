package com.hibikuroll.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.net.URL

data class AnimeRemote(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val score: Double,
    val episodes: Int?,
    val members: Int,
    val airing: Boolean,
    val broadcastDay: String?,
    val broadcastTime: String?
)

object AnimeApi {
    private const val BASE = "https://api.jikan.moe/v4"

    suspend fun topAiring(limit: Int = 18): List<AnimeRemote> =
        getList("/top/anime?filter=airing&limit=$limit")

    suspend fun search(query: String, limit: Int = 18): List<AnimeRemote> =
        getList(
            "/anime?q=${URLEncoder.encode(query, "UTF-8")}" +
                "&limit=$limit&sfw=true"
        )

    suspend fun schedule(day: String, limit: Int = 20): List<AnimeRemote> =
        getList("/schedules?filter=$day&limit=$limit")

    private suspend fun getList(path: String): List<AnimeRemote> =
        withContext(Dispatchers.IO) {
            val connection =
                (URL(BASE + path).openConnection() as HttpURLConnection).apply {
                    requestMethod = "GET"
                    connectTimeout = 10000
                    readTimeout = 15000
                    setRequestProperty("Accept", "application/json")
                }

            try {
                if (connection.responseCode !in 200..299) {
                    return@withContext emptyList()
                }

                val body = connection.inputStream
                    .bufferedReader()
                    .use { it.readText() }

                parseList(JSONObject(body).optJSONArray("data"))
            } catch (_: Exception) {
                emptyList()
            } finally {
                connection.disconnect()
            }
        }

    private fun parseList(array: JSONArray?): List<AnimeRemote> {
        if (array == null) return emptyList()

        val result = ArrayList<AnimeRemote>(array.length())

        for (i in 0 until array.length()) {
            val item = array.optJSONObject(i) ?: continue
            val images = item.optJSONObject("images")
            val jpg = images?.optJSONObject("jpg")

            val image =
                jpg?.optString("large_image_url")
                    .orEmpty()
                    .ifBlank {
                        jpg?.optString("image_url").orEmpty()
                    }

            val broadcast = item.optJSONObject("broadcast")

            result += AnimeRemote(
                id = item.optInt("mal_id"),
                title = item.optString("title").ifBlank {
                    "Untitled Anime"
                },
                imageUrl = image,
                score = item.optDouble("score", 0.0),
                episodes = if (item.has("episodes") && !item.isNull("episodes")) {
                    item.optInt("episodes")
                } else {
                    null
                },
                members = item.optInt("members", 0),
                airing = item.optBoolean("airing", false),
                broadcastDay = broadcast?.optString("day"),
                broadcastTime = broadcast?.optString("time")
            )
        }

        return result
    }
}
