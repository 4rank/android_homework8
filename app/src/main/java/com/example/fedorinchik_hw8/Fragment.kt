package com.example.fedorinchik_hw8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.fedorinchik_hw8.workers.Battery
import com.example.fedorinchik_hw8.workers.InternalStorage
import java.util.concurrent.TimeUnit

class Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment, container, false)
        view.findViewById<Button>(R.id.but_battery)?.setOnClickListener {
            val storageAvailable =
                PeriodicWorkRequestBuilder<InternalStorage>(1, TimeUnit.HOURS).build()
            val batteryResult = PeriodicWorkRequestBuilder<Battery>(1, TimeUnit.HOURS).build()
            val requests = listOf(batteryResult, storageAvailable)
            WorkManager
                .getInstance(requireContext())
                .enqueue(requests)
        }
        return view
    }
}