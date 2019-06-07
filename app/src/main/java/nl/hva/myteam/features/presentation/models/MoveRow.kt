package nl.hva.myteam.features.presentation.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import nl.hva.myteam.features.data.models.MoveInfo
import nl.hva.myteam.features.data.models.Pokemon

@Entity(foreignKeys = [ForeignKey(
    entity = Pokemon::class,
    parentColumns = ["id"],
    childColumns = ["pokemonId"],
    onDelete = CASCADE
)])
data class MoveRow(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val level: String,
    val moveInfo: MoveInfo,
    val pokemonId: Int
)