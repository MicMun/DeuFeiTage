package de.micmun.android.deufeitage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Class for fragment of state of germany.
 *
 * @author MicMun
 * @version 1.0, 18.02.18
 */
class StateFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.state_row, container, false)

    companion object {
        fun newInstance(name: String): StateFragment {
            val stfm = StateFragment()

            val bundle = Bundle()
            bundle.putString("NAME", name)
            stfm.arguments = bundle

            return stfm
        }
    }
}