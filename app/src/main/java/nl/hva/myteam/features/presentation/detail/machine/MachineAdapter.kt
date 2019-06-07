package nl.hva.myteam.features.presentation.detail.machine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_move_item.view.*
import nl.hva.myteam.R
import nl.hva.myteam.features.presentation.models.MoveRow

class MachineAdapter(private val moveList: List<MoveRow>) : RecyclerView.Adapter<MachineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_move_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = moveList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val move = moveList[position]
        holder.view.apply {
            textViewName.text = move.moveInfo.name.capitalize()
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}