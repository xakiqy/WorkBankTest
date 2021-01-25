package com.example.workbanktest.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.workbanktest.R
import com.example.workbanktest.adapter.CurrencyNBAdapter
import com.example.workbanktest.adapter.CurrencyPBAdapter
import com.example.workbanktest.databinding.FragmentCurrencyBinding
import com.example.workbanktest.viewmodel.CurrencyViewModel
import java.text.SimpleDateFormat
import java.util.*


class CurrencyFragment : Fragment() {

    private val viewModel by viewModels<CurrencyViewModel>()
    private lateinit var binding: FragmentCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.secondCurrencyTable.adapter =
            CurrencyNBAdapter(CurrencyNBAdapter.OnClickListener {})

        binding.firstCurrencyTable.adapter = CurrencyPBAdapter(CurrencyPBAdapter.OnClickListener {
            val position = viewModel.getSecondTablePosition(it.currency)
            binding.secondCurrencyTable.smoothScrollToPosition(position)
            binding.secondCurrencyTable.postOnAnimationDelayed({
                binding.secondCurrencyTable.findViewHolderForAdapterPosition(position)!!
                    .itemView.setBackgroundColor(requireContext().getColor(R.color.colorSelectedCurrency))
            }, 300)
        })

        timeTextTextViewInit(binding)

        binding.firstCalendarButton.setOnClickListener {
            getDatePickerDialog(requireActivity(), binding.firstDateLabel, true).show()
        }

        binding.secondCalendarButton.setOnClickListener {
            getDatePickerDialog(requireActivity(), binding.secondDateLabel, false).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbarTitle.text = binding.toolbar.title
        binding.toolbar.title = ""
        binding.toolbar.inflateMenu(R.menu.appbar)
        binding.toolbar.setOnMenuItemClickListener {
            findNavController().navigate(CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyGraphFragment())
            true
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun timeTextTextViewInit(binding: FragmentCurrencyBinding) {
        binding.firstDateLabel.paintFlags =
            binding.firstDateLabel.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.secondDateLabel.paintFlags =
            binding.secondDateLabel.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun getDatePickerDialog(
        activity: Activity,
        view: TextView,
        firstTable: Boolean
    ): DatePickerDialog {
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy"
                val sdf = SimpleDateFormat(myFormat)

                val udata = sdf.format(cal.time)
                val content = SpannableString(udata)
                content.setSpan(UnderlineSpan(), 0, udata.length, 0)
                view.text = content

                if (firstTable) {
                    viewModel.getCurrencyPB(udata)
                } else {
                    viewModel.getCurrencyNB(udata)
                }
            }

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            activity, AlertDialog.THEME_HOLO_DARK, dateSetListener, year, month, day
        )
    }
}
