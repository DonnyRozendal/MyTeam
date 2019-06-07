package nl.hva.myteam.features.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.models.PokemonResponse
import nl.hva.myteam.features.presentation.models.MoveRow

@Database(entities = [Pokemon::class, PokemonResponse::class, MoveRow::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}