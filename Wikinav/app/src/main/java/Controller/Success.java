package Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.wikinav.R;


public class Success extends AppCompatDialogFragment {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private SuccessDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.success_dialog, null);

        builder.setView(view)
                .setTitle("Great!")
                .setNegativeButton("Navigation History", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.getHistNavigation();
                    }
                })
                .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.playAgain();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SuccessDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface SuccessDialogListener {
        void playAgain();
        void getHistNavigation();
    }
}