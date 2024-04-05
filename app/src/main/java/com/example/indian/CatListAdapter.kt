import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.indian.Data
import com.example.indian.databinding.ItemCatBinding

class CatListAdapter : ListAdapter<Data, CatListAdapter.ViewHolder>(CatDiffUtil()) {

    private var dataList: List<Data> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<Data>?) {
        super.submitList(list)
        dataList = list ?: listOf()
    }

    fun filter(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            dataList
        } else {
            dataList.filter { it.name.contains(query, ignoreCase = true) }
        }
        submitList(filteredList)
    }

    inner class ViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            with(binding) {
                cattitle.text = item.name
                direct.text = item.origin
                intelligence.text = "Intelligence: ${item.intelligence}"
                generalHealth.text = "General Health: ${item.generalHealth}"
                grooming.text = "Grooming: ${item.grooming}"

                // Load image using Glide
                Glide.with(catImage.context)
                    .load(item.imageLink)
                    .into(catImage)
            }
        }
    }

    class CatDiffUtil : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}
