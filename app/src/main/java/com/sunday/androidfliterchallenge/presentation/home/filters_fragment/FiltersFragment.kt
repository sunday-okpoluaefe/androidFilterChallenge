package com.sunday.androidfliterchallenge.presentation.home.filters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.presentation.core.BaseFragment
import com.sunday.androidfliterchallenge.presentation.core.ViewModelFactory
import com.sunday.androidfliterchallenge.presentation.home.filters_fragment.adapter.FilterAdapter
import com.sunday.androidfliterchallenge.presentation.home.filters_fragment.adapter.OnItemClickListener
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FiltersFragment : BaseFragment() {

    lateinit var filterRecyclerView: RecyclerView

    lateinit var filters : ArrayList<Filter>

    @Inject
    lateinit var viewModelFactory : ViewModelFactory<FilterFragmentViewModel>

    private val filterFragmentViewModel : FilterFragmentViewModel by lazy {
        requireActivity().run {
            ViewModelProvider(this, viewModelFactory)
                .get(FilterFragmentViewModel::class.java)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.filters_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

        filters = arguments?.getParcelableArrayList("filters")!!

        filterRecyclerView = view.findViewById(R.id.list)
        filterRecyclerView.setHasFixedSize(true);
        filterRecyclerView.layoutManager = LinearLayoutManager(activity);
        filterRecyclerView.adapter = FilterAdapter(filters, object : OnItemClickListener {
            override fun onItemClicked(trip: Filter) {

            }
        })

        //(filterRecyclerView.adapter as FilterAdapter).notifyDataSetChanged()
        activity?.title = resources.getString(R.string.filter_fragment_title)


        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    requireActivity().finish()
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback);

    }
}