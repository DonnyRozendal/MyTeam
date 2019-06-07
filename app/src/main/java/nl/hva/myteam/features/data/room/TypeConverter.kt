package nl.hva.myteam.features.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nl.hva.myteam.features.data.models.MoveInfo
import nl.hva.myteam.features.data.models.Pokemon

class TypeConverter {

    @TypeConverter
    fun fromPokemon(pokemon: List<Pokemon>): String {
        return Gson().toJson(pokemon)
    }

    @TypeConverter
    fun toPokemon(value: String): List<Pokemon> {
        val listType = object : TypeToken<List<Pokemon>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMoveInfo(moveInfo: MoveInfo): String {
        return Gson().toJson(moveInfo)
    }

    @TypeConverter
    fun toMoveInfo(value: String): MoveInfo {
        val listType = object : TypeToken<MoveInfo>() {}.type
        return Gson().fromJson(value, listType)
    }

}