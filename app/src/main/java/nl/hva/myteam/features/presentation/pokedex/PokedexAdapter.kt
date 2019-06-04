package nl.hva.myteam.features.presentation.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.hva.myteam.R
import nl.hva.myteam.features.data.models.Pokemon

class PokedexAdapter(private val clickListener: (Pokemon) -> Unit) :
    ListAdapter<Pokemon, PokedexAdapter.ViewHolder>(
        MovieDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_pokedex_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pokemon: Pokemon, clickListener: (Pokemon) -> Unit) = with(view) {
            val images = context.assets.list("sprites/pokemon")
            val ips = context.assets.open("sprites/pokemon/" + images?.get(adapterPosition))

            setOnClickListener { clickListener(pokemon) }
        }

    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

}