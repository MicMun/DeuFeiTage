package de.micmun.android.deufeitage

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.micmun.android.deufeitage.adapter.HolidayAdapter
import de.micmun.android.deufeitage.adapter.YearRecyclerAdapter
import de.micmun.android.deufeitage.adapter.YearViewHolder
import de.micmun.android.deufeitage.model.StateItem
import de.micmun.android.deufeitage.model.YearItem
import de.micmun.android.deufeitage.utils.HolidayCalculator
import de.micmun.android.deufeitage.utils.HolidayConfigReader
import de.micmun.android.deufeitage.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.util.*


/**
 * MainActivity of the app.
 *
 * @author MicMun
 * @version 1.0, 07.08.18
 */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
        YearViewHolder.OnYearItemSelectedListener {
    private var yearAdapter: YearRecyclerAdapter? = null
    private var holidayCalculator: HolidayCalculator? = null
    private var holidayConfigReader: HolidayConfigReader? = null
    private var preferenceManager: PreferenceManager? = null

    private var selectedYear: Int = 0
    private var selectedState: StateItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Read config and init calculator
        if (holidayConfigReader == null) {
            holidayConfigReader = HolidayConfigReader(this)
            holidayCalculator = HolidayCalculator(holidayConfigReader!!.holidays)
        }
        initPreferences()

        // holiday view
        holidayView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // year selector
        yearSelector.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    /**
     * Inits the preferences.
     */
    private fun initPreferences() {
        val sharedPreference = getSharedPreferences(PreferenceManager.pref_name, Context.MODE_PRIVATE)
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        val version = pInfo.versionName

        preferenceManager = PreferenceManager(sharedPreference, version)
    }

    override fun onPause() {
        stateSpinner.onItemSelectedListener = null
        stateSpinner.adapter = null
        Log.d("MainActivity", "onPause")
        super.onPause()
    }

    override fun onResume() {
        selectedYear = preferenceManager!!.getYear()
        val stateId = preferenceManager!!.getState()
        selectedState = holidayConfigReader!!.states.find { it.key == stateId }
        initActivity()
        Log.d("MainActivity", "onResume")
        super.onResume()
    }

    private fun initActivity() {
        // state selection
        val stateAdapter = ArrayAdapter<StateItem>(this, R.layout.state_text_view, holidayConfigReader!!.states)
        stateSpinner.adapter = stateAdapter
        stateSpinner.onItemSelectedListener = this
        if (selectedState == null) {
            selectedState = stateSpinner.selectedItem as StateItem?
        } else {
            val pos = holidayConfigReader!!.states.indexOf(selectedState!!)
            stateSpinner.setSelection(pos)
        }

        // year selection
        initYearSelector()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        selectedState = parent.getItemAtPosition(pos) as StateItem
        preferenceManager!!.setState(selectedState!!.key)
        initHolidays()
    }


    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    /**
     * Init the holiday list.
     */
    private fun initHolidays() {
        var adapter = holidayView.adapter

        if (adapter == null) {
            adapter = HolidayAdapter(holidayCalculator!!.getHolidays(selectedYear, selectedState!!))
            holidayView.adapter = adapter
        } else {
            (adapter as HolidayAdapter).holidays = holidayCalculator!!.getHolidays(selectedYear, selectedState!!)
        }
        adapter.notifyDataSetChanged()
    }

    /**
     * Init year selector to show years
     */
    private fun initYearSelector() {
        val years = mutableListOf<YearItem>()

        if (selectedYear == -1) {
            selectedYear = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.now().year
            } else {
                Calendar.getInstance().get(Calendar.YEAR)
            }
        }
        val currentYear = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().year
        } else {
            Calendar.getInstance().get(Calendar.YEAR)
        }

        var current: YearItem? = null

        for (i in currentYear - 4..currentYear + 4) {
            if (i == selectedYear) {
                current = YearItem(i)
                years.add(current)
            } else
                years.add(YearItem(i))
        }

        val selectedPos = years.indexOf(current)

        yearAdapter = YearRecyclerAdapter(years.toTypedArray(), this)
        yearSelector.adapter = yearAdapter

        yearSelector.postDelayed({
            yearSelector.findViewHolderForAdapterPosition(selectedPos)?.itemView?.performClick()
        },
                30)
    }

    override fun onYearItemSelected(item: YearItem) {
        selectedYear = item.year
        preferenceManager!!.setYear(selectedYear)

        initHolidays()
    }
}
