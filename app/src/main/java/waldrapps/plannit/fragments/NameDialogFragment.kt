package waldrapps.plannit.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import kotlinx.android.synthetic.main.activity_new_contact.*
import kotlinx.android.synthetic.main.fragment_dialog_name.view.*
import waldrapps.plannit.R
import waldrapps.plannit.utils.Constants

class NameDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(activity)
        val view = View.inflate(activity, R.layout.fragment_dialog_name, null)
        if(arguments?.getString(Constants.ARG_AUTOFILL) != null) {
            view.editText.setText(arguments?.getString(Constants.ARG_AUTOFILL).toString())
        }
        builder.setMessage(R.string.enter_name)
                .setView(view)
                .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                    activity!!.toolbar.title = view.editText.text.toString()
                }
                .setNegativeButton(R.string.cancel) { dialog : DialogInterface, _: Int -> dialog.cancel() }
        //Create the AlertDialog object and return it
        return builder.create()
    }

    companion object {
        @JvmStatic fun newInstance(autoFill : String?): NameDialogFragment {
            val fragment = NameDialogFragment()
            val arguments = Bundle()
            arguments.putString(Constants.ARG_AUTOFILL, autoFill)
            fragment.arguments = arguments
            return fragment
        }
    }
}