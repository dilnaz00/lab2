import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.indian.ApiClient
import com.example.indian.databinding.FragmentDataBinding
import com.example.indian.Data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.SearchView

class MyDataFragment : Fragment() {

    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CatListAdapter
    private var dataList: List<Data> = listOf()
    private var filteredList: List<Data> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter
        adapter = CatListAdapter()

        binding.myList.layoutManager = LinearLayoutManager(requireContext())
        binding.myList.adapter = adapter

        // Make the API call
        ApiClient.instance.fetchOfferList().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful && response.body() != null) {
                    dataList = response.body() ?: listOf()
                    filterList("") // Initially display full list
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                // Handle network call failure
            }
        })

        // Implement Search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText.orEmpty())
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterList(query: String) {
        filteredList = if (query.isBlank()) {
            dataList // Display full list if query is empty or blank
        } else {
            dataList.filter { it.name.contains(query, ignoreCase = true) }
        }
        adapter.submitList(filteredList)
    }

    companion object {
        fun newInstance() = MyDataFragment()
    }
}
