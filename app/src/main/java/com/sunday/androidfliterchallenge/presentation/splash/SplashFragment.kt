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
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject


class SplashFragment : BaseFragment() {

    lateinit var progress_indicator : View
    lateinit var error_layout : View
    lateinit var bt_try_again : View

    @Inject
    lateinit var scheduler : Scheduler

    lateinit var carOwnersList : List<CarOwner>

    @Inject
    lateinit var viewModelFactory : ViewModelFactory<SplashFragmentViewModel>

    private val splashFragmentViewModel : SplashFragmentViewModel by lazy {
        requireActivity().run {
            ViewModelProvider(this, viewModelFactory)
                .get(SplashFragmentViewModel::class.java)
        }
    }

    private fun readCSV(): Observable<ArrayList<CarOwner>> {
        val inputStream = resources.openRawResource(R.raw.car_ownsers_data)

        BufferedReader(
            InputStreamReader(inputStream, Charsets.UTF_8)
        )

        //this is all the file in the csv as a string, you can just write this to external storage
        val allText = inputStream.bufferedReader().use(BufferedReader::readText)

        val split = allText.split("\n")

        //the first row is the heading, so I split that off
        val heading = split.first()

        val carOwners = ArrayList<CarOwner>()
        for(data in split.subList(1, split.size)){
            val row = data.split(',')
            try{
                carOwners.add(CarOwner().apply {
                    firstName = row[1]
                    lastName  = row[2]
                    email  = row[3]
                    country  = row[4]
                    carModel  = row[5]
                    carModelYear  = row[6]
                    carColor  = row[7]
                    gender  = row[8]
                    jobTitle  = row[9]
                    bio   = row[10]
                })
            }catch (e: Exception){

            }
        }

        return Observable.just(carOwners)
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

                    processData(result.data!!)

                }
            }
        })

        splashFragmentViewModel.getFilters()
    }

    @SuppressLint("CheckResult")
    fun processData(filters: ArrayList<Filter>){
        readCSV().subscribeOn(scheduler.background())
            .observeOn(scheduler.ui())
            .subscribe(
                {
                    //Log.d(TAG, "Reading done: ${it.size}")
                    carOwnersList = it


                    findNavController().navigate(R.id.action_splashFragment_to_FirstFragment,
                        bundleOf("filters" to filters, "owners" to carOwnersList))

                },
                {
                    //Log.e(TAG, "Error occurred: ${it.localizedMessage}", it)
                }
            )
    }

}