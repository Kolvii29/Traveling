package com.kelvin.traveling.features.login.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kelvin.traveling.R;
import com.kelvin.traveling.database.DBHelper;
import com.kelvin.traveling.databinding.FragmentBLoginBinding;
import com.kelvin.traveling.features.login.activity.LogInActivity;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private TextInputLayout mainInputEmail;
    private TextInputLayout mainInputPass;
    private TextInputEditText email;
    private TextInputEditText password;
    DBHelper DB;
    FragmentBLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBLoginBinding.inflate(inflater, container, false);

        DB = new DBHelper(requireContext());

        TextView snackRegisterNow = binding.tvForgetPassword;
        snackRegisterNow.setOnClickListener(v -> Snackbar.make(v, "Próximamente...", Snackbar.LENGTH_LONG).show());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listeners();
    }

    private void listeners() {
        Button btn_login = binding.btnLogin;
        TextView goToRegister = binding.tvRegisterNow;
        mainInputEmail = binding.mainInputEmail;
        mainInputPass = binding.mainInputPassword;
        email = binding.secondInputEmail;
        password = binding.secondInputPassword;

        btn_login.setOnClickListener(v -> loginUser());

        goToRegister.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_BLoginFragment_to_BRegisterFragment));
    }

    private void getArgsRegister() {
        if (getArguments() != null) {
            String username = getArguments().getString("username");
            String userEmail = getArguments().getString("email");
            String userPass = getArguments().getString("password");
        }
    }


    public void loginUser() {
        String userEmail = Objects.requireNonNull(email.getText()).toString();
        String userPass = Objects.requireNonNull(password.getText()).toString();

        if (TextUtils.isEmpty(userEmail)) {
            mainInputEmail.setError("Fill in the field");
        } else if (TextUtils.isEmpty(userPass)) {
            mainInputPass.setError("Fill in the field");
        } else {
            mainInputEmail.setError(null);
            mainInputPass.setError(null);

            boolean checkUserEmailPassword = DB.checkUserEmailPassword(userEmail, userPass);
            if (checkUserEmailPassword) {

                getArgsRegister();

                LoginFragmentDirections.ActionBLoginFragmentToHomeActivity action =
                        LoginFragmentDirections.actionBLoginFragmentToHomeActivity(
                                userEmail, userPass
                        );
                NavHostFragment.findNavController(this).navigate(action);
                Log.d("LoginFragment", "Successful Login");

            } else {
                showAlertDialog();
            }
        }
    }

    private void showAlertDialog() {
        View alertCustomDialog = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());

        alertDialog.setView(alertCustomDialog);
        Button ok_btn = alertCustomDialog.findViewById(R.id.btn_Okay);

        final AlertDialog dialog = alertDialog.create();

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ok_btn.setOnClickListener(v -> dialog.cancel());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof LogInActivity) {
            ((LogInActivity) getActivity()).setToolbarVisibility(false);
        }
    }
}