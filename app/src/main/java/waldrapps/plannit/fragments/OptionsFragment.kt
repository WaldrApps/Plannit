package waldrapps.plannit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_new_contact_options.view.*
import waldrapps.plannit.R
import waldrapps.plannit.activities.GroupActivity
import waldrapps.plannit.utils.Constants.ARG_COLOR

/**
 * https://github.com/QuadFlask/colorpicker
 */
class OptionsFragment : BaseFragment() {
    val bundle = Bundle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_contact_options, container, false)
        view.color_picker_view.addOnColorChangedListener {
            bundle.putString(ARG_COLOR, "#" + Integer.toHexString(it))
        }

        view.fab.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val fragment = if(activity is GroupActivity) {
                GroupFragment.newInstance()
            } else {
                NewContactFragment.newInstance()
            }
            //Pass uuid to fragment
            bundle.putString(getString(R.string.uuid), arguments!!.getString(getString(R.string.uuid)))
            fragment.arguments = bundle
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()}
        return view
    }

    companion object {
        fun newInstance(): OptionsFragment {
            return OptionsFragment()
        }
    }
}
