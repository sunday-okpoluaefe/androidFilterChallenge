package com.sunday.androidfliterchallenge.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.CarOwner
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.presentation.core.BaseFragment
import com.sunday.androidfliterchallenge.presentation.core.ViewModelFactory
import com.sunday.androidfliterchallenge.rx.Scheduler
import com.sunday.androidfliterchallenge.utils.Status
import javax.inject.Inject


class SplashFragment : BaseFragment() {

    lateinit var progress_indicator : View
    lateinit var error_layout : View
    lateinit var bt_try_again : View

    @Inject
    lateinit var scheduler : Scheduler

    lateinit var filters : ArrayList<Filter>

    @Inject
    lateinit var viewModelFactory : ViewModelFactory<SplashFragmentViewModel>

    private val splashFragmentViewModel : SplashFragmentViewModel by lazy {
        requireActivity().run {
            ViewModelProvider(this, viewModelFactory)
                .get(SplashFragmentViewModel::class.java)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_layout, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = ""

        progress_indicator = view.findViewById(R.id.progress_indicator)
        error_layout = view.findViewById(R.id.error_layout)
        bt_try_again = view.findViewById(R.id.bt_try_again)

        bt_try_again.setOnClickListener {
            splashFragmentViewModel.getFilters()
        }

        //
        splashFragmentViewModel.carOwnersObserver().observe(viewLifecycleOwner, Observer {result ->
            when (result.status){
                Status.LOADING ->   {

                }
                Status.ERROR -> {

                }
                Status.SUCCESS -> {

                    // processData(result.data!!)
                    findNavController().navigate(R.id.action_splashFragment_to_FirstFragment,
                        bundleOf("filters" to filters, "owners" to result.data))

                }
            }
        })

        splashFragmentViewModel.filtersObserver().observe(viewLifecycleOwner, Observer { result ->
            when (result.status){
                Status.LOADING ->   {
                    error_layout.visibility = View.GONE
                    progress_indicator.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    error_layout.visibility = View.VISIBLE
                    progress_indicator.visibility = View.GONE
                }
                Status.SUCCESS -> {

                   // processData(result.data!!)
                    filters = result?.data!!
                    splashFragmentViewModel.getCarOwners(requireContext())

                }
            }
        })

        splashFragmentViewModel.getFilters()
    }



}