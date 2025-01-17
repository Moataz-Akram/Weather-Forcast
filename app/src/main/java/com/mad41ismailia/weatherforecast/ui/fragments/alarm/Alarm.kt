package com.mad41ismailia.weatherforecast.ui.fragments.alarm

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mad41ismailia.weatherforecast.R
import com.mad41ismailia.weatherforecast.databinding.AlarmFragmentBinding
import com.mad41ismailia.weatherforecast.ui.activites.alarm.AlarmActivity

class Alarm : Fragment(R.layout.alarm_fragment) {
    private lateinit var binding: AlarmFragmentBinding
    private lateinit var viewModel: AlarmFragmentViewModel
    lateinit var adapter : AlarmDataAdapter

    companion object {
        fun newInstance() = Alarm()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = AlarmFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlarmFragmentViewModel::class.java)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.alarmRecycler.layoutManager = layoutManager
        binding.alarmRecycler.setHasFixedSize(true)


        viewModel.getAlarms().observe(viewLifecycleOwner,{
            adapter = AlarmDataAdapter(viewModel,it,requireContext())
            binding.alarmRecycler.adapter = adapter
        })

        if (viewModel.getCurrentLocation()==null){
            binding.FABAddAlarm.setOnClickListener {
                Toast.makeText(requireContext(),"Please set your current location first",Toast.LENGTH_LONG).show()
            }
        }else {
            binding.FABAddAlarm.setOnClickListener {
                val intent = Intent(requireContext(), AlarmActivity::class.java)
                requireActivity().startActivity(intent)
            }
        }

    }

}