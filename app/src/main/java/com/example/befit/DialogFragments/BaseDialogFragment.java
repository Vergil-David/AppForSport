package com.example.befit.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogFragment extends DialogFragment {
    protected abstract int getLayuoytID();

    protected abstract int getDialogStyle();

    protected abstract void setupViews(View dialogView);

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View dialogView = getLayoutInflater().inflate(getLayuoytID(),null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), getDialogStyle());
        builder.setView(dialogView);

        setupViews(dialogView);
        return builder.create();
    }
}
