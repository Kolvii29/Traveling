package com.kelvin.traveling.features.login.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kelvin.traveling.R;
import com.kelvin.traveling.database.DBHelper;
import com.kelvin.traveling.databinding.FragmentBRegisterBinding;
import com.kelvin.traveling.features.login.activity.LogInActivity;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private ImageView photo_user;
    private TextInputLayout mainInputName;
    private TextInputLayout mainInputEmail;
    private TextInputLayout mainInputPass;
    private TextInputLayout mainInputAges;
    private Button btn_letsStart;
    private AutoCompleteTextView completeInputAges;
    private String termsPrivacy;
    private boolean isAgeSelected = false;
    TextInputEditText username, email, password;
    DBHelper DB;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBRegisterBinding binding = FragmentBRegisterBinding.inflate(inflater, container, false);
        ImageView clickToCamera = binding.picProfile;
        TextView btn_seeConditions = binding.tvVerCondiciones;
        MaterialToolbar toolbarRegister = binding.topBarApp;
        DB = new DBHelper(requireContext());
        photo_user = binding.picProfile;
        mainInputName = binding.mainInputName;
        mainInputEmail = binding.mainInputEmail;
        mainInputPass = binding.mainInputPassword;
        mainInputAges = binding.mainInputAge;
        btn_letsStart = binding.btnLetsStart;
        completeInputAges = binding.secondInputAge;
        username = binding.secondInputName;
        email = binding.secondInputEmail;
        password = binding.secondInputPassword;
        termsPrivacy = "https://developers.google.com/ml-kit/terms";

        Objects.requireNonNull(mainInputName.getEditText()).addTextChangedListener(textWatcherInputs);
        Objects.requireNonNull(mainInputEmail.getEditText()).addTextChangedListener(textWatcherInputs);
        Objects.requireNonNull(mainInputPass.getEditText()).addTextChangedListener(textWatcherInputs);
        Objects.requireNonNull(mainInputAges.getEditText()).addTextChangedListener(textWatcherInputs);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbarRegister);

        toolbarRegister.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_BRegisterFragment_to_BLoginFragment));

        btn_seeConditions.setOnClickListener(v -> {
            Uri termsAndPrivacy = Uri.parse(termsPrivacy);
            Intent clickTvSeeTerms = new Intent(Intent.ACTION_VIEW, termsAndPrivacy);
            startActivity(clickTvSeeTerms);
        });

        clickToCamera.setOnClickListener(v -> {
            Intent cameraPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraPic, 1);
        });

        btn_letsStart.setOnClickListener(v -> {
            if (!isAgeSelected) {
                mainInputAges.setError("Fill in the field");
                return;
            }

            registerUser();
        });

        btn_InputAges();

        return binding.getRoot();
    }

    public void registerUser() {
        String userUsername = Objects.requireNonNull(username.getText()).toString().trim();
        String userEmail = Objects.requireNonNull(email.getText()).toString().trim();
        String pass = Objects.requireNonNull(password.getText()).toString().trim();

        if (TextUtils.isEmpty(userUsername)) {
            mainInputName.setErrorEnabled(true);
            mainInputName.setError("Fill in the field");
        } else if (TextUtils.isEmpty(userEmail)) {
            mainInputEmail.setErrorEnabled(true);
            mainInputEmail.setError("Fill in the field");
        } else if (TextUtils.isEmpty(pass)) {
            mainInputPass.setErrorEnabled(true);
            mainInputPass.setError("Fill in the field");
        } else {
            clearErrors();

            if (!DB.checkUserEmail(userEmail)) {
                Toast.makeText(requireActivity(), "Presiona otra vez para confirmar", Toast.LENGTH_SHORT).show();
                if (!DB.checkUserName(userUsername)) {
                    boolean insert = DB.insertData(userUsername, userEmail, pass);

                    if (insert) {
                        btn_letsStart.setEnabled(true);
                        btn_letsStart.setOnClickListener(v -> btn_letsStart.setOnClickListener(view -> {
                            Toast.makeText(requireContext(), "Successful Register", Toast.LENGTH_SHORT).show();
                            Log.d("RegisterFragment", "Successful Register");

                            Bundle args = new Bundle();
                            args.putString("username", userUsername);
                            args.putString("email", userEmail);
                            args.putString("password", pass);

                            NavHostFragment.findNavController(this)
                                    .navigate(R.id.action_BRegisterFragment_to_BLoginFragment, args);
                        }));

                    } else alertDialog();

                } else {
                    Toast.makeText(requireContext(), "UserName already used", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Email already used", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearErrors() {
        mainInputName.setError(null);
        mainInputEmail.setError(null);
        mainInputPass.setError(null);
    }

    private void alertDialog() {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) Objects.requireNonNull(extras).get("data");
            photo_user.setImageBitmap(photo);
        }

    }

    private final TextWatcher textWatcherInputs = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean isInputNameValid = isInputEmpty(mainInputName);
            boolean containInvalidChars = InvalidCharacters(mainInputName);

            if (!isInputNameValid) {
                mainInputName.setError("Fill in the field");
            } else if (containInvalidChars) {
                mainInputName.setError("Oops, I don't think that's right, check it.");
            } else {
                mainInputName.setErrorEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean isInputEmpty(TextInputLayout textInputLayout) {
        String textInputs = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
        return !TextUtils.isEmpty(textInputs);
    }

    private boolean InvalidCharacters(TextInputLayout textInputLayout) {
        String textInputs = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
        return textInputs.contains("@") || textInputs.contains("!");
    }

    private void btn_InputAges() {
        String[] optionAges = {"0-5", "6-11", "12-17", "18-99"};

        completeInputAges.setAdapter(new ArrayAdapter<>(requireActivity(), R.layout.dropdown_menu, optionAges));
        completeInputAges.setOnItemClickListener((parent, view, position, id) -> {
            String selectedAge = optionAges[position];

            if (selectedAge.equals("0-5") || selectedAge.equals("6-11") || selectedAge.equals("12-17")) {
                mainInputAges.setError("Esta aplicación no es para tí.");
                isAgeSelected = false;
            } else if (selectedAge.equals("18-99")) {
                mainInputAges.setError(null);
                isAgeSelected = true;
            } else {
                mainInputName.setError("Fill in the field");
                isAgeSelected = false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof LogInActivity homeActivity) {
            homeActivity.setToolbarVisibility(true);
            homeActivity.setToolbarBackButton();
        }
    }
}