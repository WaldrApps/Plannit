package waldrapps.plannit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import waldrapps.plannit.R;

public class DeleteDialogFragment extends DialogFragment {

    public interface DeleteDialogListener {
        void onDialogPositiveClick(DeleteDialogFragment dialog);
        void onDialogNegativeClick(DeleteDialogFragment dialog);
    }

    private DeleteDialogListener deleteDialogListener;

    public int pos;

    public static DeleteDialogFragment newInstance(int pos) {
        DeleteDialogFragment fragment = new DeleteDialogFragment();

        Bundle args = new Bundle();
        args.putInt("pos", pos);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            Bundle args = getArguments();
            pos = args.getInt("pos");
        }
         //Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_question)
                .setPositiveButton(R.string.delete, (dialog, id) -> deleteDialogListener.onDialogPositiveClick(DeleteDialogFragment.this))
                .setNegativeButton(R.string.cancel, (dialog, id) -> deleteDialogListener.onDialogNegativeClick(DeleteDialogFragment.this));
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Verify that the host activity implements the callback interface
        try {
            deleteDialogListener = (DeleteDialogListener) context;
        } catch (ClassCastException e) {
            //The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString() + " must implement DeleteDialogListener");
        }
    }

    public int getPos() {
        return pos;
    }
}
