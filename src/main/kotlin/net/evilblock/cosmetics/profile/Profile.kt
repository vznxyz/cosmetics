package net.evilblock.cosmetics.profile

import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cosmetics.category.track.TrackHandler
import net.evilblock.cosmetics.category.track.player.PlayerTrack
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

class Profile(val uuid: UUID) {

    @JsonAdapter(StatesMapSerializer::class)
    private val cosmetics: HashMap<Cosmetic, Boolean> = hashMapOf()

    @JsonAdapter(PlayerTrackSerializer::class)
    var track: PlayerTrack? = null

    @Transient
    var requiresSave: Boolean = false

    fun getEnabledCosmetics(): List<Cosmetic> {
        return cosmetics.filter { it.value }.keys.toList()
    }

    fun isCosmeticEnabled(cosmetic: Cosmetic): Boolean {
        return cosmetics.getOrDefault(cosmetic, false)
    }

    fun toggleCosmetic(cosmetic: Cosmetic) {
        if (cosmetics.containsKey(cosmetic)) {
            cosmetics[cosmetic] = !cosmetics[cosmetic]!!
        } else {
            cosmetics[cosmetic] = true
        }

        requiresSave = true
    }

    object StatesMapSerializer : JsonSerializer<HashMap<Cosmetic, Boolean>>, JsonDeserializer<HashMap<Cosmetic, Boolean>> {
        override fun serialize(map: HashMap<Cosmetic, Boolean>, type: Type, context: JsonSerializationContext): JsonElement {
            return JsonObject().also {
                for ((key, value) in map) {
                    it.addProperty(key.getID(), value)
                }
            }
        }

        override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): HashMap<Cosmetic, Boolean> {
            return hashMapOf<Cosmetic, Boolean>().also {
                val obj = json.asJsonObject
                for ((key, value) in obj.entrySet()) {
                    val cosmetic = CosmeticsPlugin.instance.getCosmeticById(key)
                    if (cosmetic != null) {
                        it[cosmetic] = value.asBoolean
                    }
                }
            }
        }
    }

    object PlayerTrackSerializer : JsonSerializer<PlayerTrack>, JsonDeserializer<PlayerTrack> {
        override fun serialize(track: PlayerTrack?, type: Type, context: JsonSerializationContext): JsonElement {
            return if (track == null) {
                JsonNull.INSTANCE
            } else {
                JsonPrimitive(track.track.getRawID())
            }
        }

        override fun deserialize(json: JsonElement?, type: Type, context: JsonDeserializationContext): PlayerTrack? {
            return if (json == null) {
                null
            } else {
                val track = TrackHandler.getTrackById(json.asString)
                if (track != null) {
                    PlayerTrack(track = track)
                } else {
                    null
                }
            }
        }
    }

}