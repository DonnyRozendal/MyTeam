package nl.hva.myteam.features.presentation.myteam

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_pokedex_item.view.*
import nl.hva.myteam.R
import nl.hva.myteam.features.data.models.Pokemon

class MyTeamAdapter(
    private val clickItem: (Pokemon) -> Unit,
    private val clickDelete: (Pokemon) -> Unit
) :
    ListAdapter<Pokemon, MyTeamAdapter.ViewHolder>(MyTeamDiffCallback()) {

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
        holder.bind(getItem(position), clickItem, clickDelete)
    }

    override fun submitList(list: List<Pokemon>?) {
        val comparator = compareBy<Pokemon> { it.teamSpot }
        super.submitList(list?.sortedWith(comparator))
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pokemon: Pokemon, clickItem: (Pokemon) -> Unit, clickDelete: (Pokemon) -> Unit) =
            with(view) {
                iconDelete.visibility = View.VISIBLE
                val entry = Pokemon.getEntry(pokemon)
                val ips = resources.openRawResource(
                    resources.getIdentifier(
                        "default_$entry",
                        "raw",
                        context.packageName
                    )
                )
                val bitmap = BitmapFactory.decodeStream(ips)
                imageViewSprite.setImageBitmap(bitmap)
                ips.close()

                textViewName.text = pokemon.nickname?.capitalize()
                textViewEntry.text = context.getString(R.string.entry, entry)

                setOnClickListener {

                }
                textViewName.setOnClickListener { clickItem(pokemon) }
                iconDelete.setOnClickListener { clickDelete(pokemon) }
            }

    }

    class MyTeamDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

}