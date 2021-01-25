package com.example.workbanktest.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.workbanktest.R
import com.example.workbanktest.databinding.FragmentCurrencyGraphBinding
import com.example.workbanktest.util.dipToFloat
import com.example.workbanktest.viewmodel.CurrencyGraphViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CurrencyGraphFragment : Fragment() {

    private val viewModel by viewModels<CurrencyGraphViewModel>()
    private lateinit var binding: FragmentCurrencyGraphBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyGraphBinding.inflate(inflater)
        val graphTextSize = dipToFloat(requireContext(), R.dimen.graph_text_size)
        viewModel.currencyData.observe(viewLifecycleOwner) {
            val sortedMap = it.toSortedMap()
            val lineListSell = ArrayList<Entry>()
            sortedMap.forEach { (key, value) ->
                lineListSell.add(
                    Entry(
                        SimpleDateFormat("dd.MM.yyyy").parse(key).time.toFloat(),
                        value.saleRatePB!!.toFloat()
                    )
                )
            }

            val lineListPurchase = ArrayList<Entry>()
            sortedMap.forEach { (key, value) ->
                lineListPurchase.add(
                    Entry(
                        SimpleDateFormat("dd.MM.yyyy").parse(key).time.toFloat(),
                        value.purchaseRatePB!!.toFloat()
                    )
                )
            }

            val lineDataSell = LineDataSet(lineListSell, "продажа")
            lineDataSell.setCircleColor(Color.RED)
            lineDataSell.setDrawCircleHole(false)
            lineDataSell.color = Color.RED

            val lineDataPurchase = LineDataSet(lineListPurchase, "покупка")
            lineDataPurchase.setCircleColor(Color.GREEN)
            lineDataPurchase.setDrawCircleHole(false)
            lineDataPurchase.color = Color.GREEN

            val chartData = LineData()
            chartData.addDataSet(lineDataSell)
            chartData.addDataSet(lineDataPurchase)

            val dateFormatter = SimpleDateFormat("dd/MM")
            binding.currencyGraph.data = chartData
            binding.currencyGraph.xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return dateFormatter.format(Date(value.toLong()))
                }
            }
            binding.currencyGraph.xAxis.position = XAxis.XAxisPosition.BOTTOM
            binding.currencyGraph.xAxis.textSize = graphTextSize
            binding.currencyGraph.axisRight.textSize = graphTextSize
            binding.currencyGraph.axisLeft.textSize = graphTextSize

            binding.currencyGraph.description = Description().apply {
                text = resources.getString(R.string.label_usd); textSize = graphTextSize
            }
            binding.currencyGraph.legend.textSize = graphTextSize
            binding.labelAttention.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.currencyGraph.invalidate()
            binding.currencyGraph.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.setNavigationIcon(R.mipmap.back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}