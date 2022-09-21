package com.example.voicelockscreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.voicelockscreen.MainActivity
import com.example.voicelockscreen.R
import com.example.voicelockscreen.sharepreference.PreferenceHelper
import com.example.voicelockscreen.sharepreference.PreferenceHelper.answer
import com.example.voicelockscreen.sharepreference.PreferenceHelper.positionAnswer
import com.example.voicelockscreen.utils.Util
import com.example.voicelockscreen.utils.Util.Companion.pushToScreen
import kotlinx.android.synthetic.main.fragment_security_question.*


class SecurityQuestionFragment : Fragment(), CustomSpinner.OnSpinnerEventsListener {

    private var answerLists = arrayListOf<String>()
    private lateinit var adapterSpinner: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initAction() {
        val prefs =
            context?.let {
                PreferenceHelper.customPreference(
                    it,
                    Util.ANSWER_DATA
                )
            }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                prefs?.positionAnswer = p2
                Toast.makeText(requireContext(), answerLists[p2], Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        btnSubmit.setOnClickListener {
            val answer = tvAnswer.text.toString()
            if (answer.isEmpty())
                Toast.makeText(requireContext(), "Please enter answer...", Toast.LENGTH_LONG).show()
            else {
                prefs?.answer = answer
                tvAnswer.setText("")
                activity?.supportFragmentManager?.popBackStack()
                AlternativeLockFragment().pushToScreen(activity as MainActivity)
            }
        }

    }

    private fun initView() {
        adapterSpinner = QuestionAdapter(requireContext())
        answerLists = resources.getStringArray(R.array.questions).toCollection(ArrayList())
        adapterSpinner.listQuestion = answerLists
        spinner.adapter = adapterSpinner
        spinner.setSpinnerEventsListener(this)
    }

    override fun onPopupWindowOpened(spinner: Spinner?) {
        //TODO("Not yet implemented")
    }

    override fun onPopupWindowClosed(spinner: Spinner?) {
        //TODO("Not yet implemented")
    }


}