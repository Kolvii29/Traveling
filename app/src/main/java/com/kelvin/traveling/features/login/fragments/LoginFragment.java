package com.kelvin.traveling.features.login.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.kelvin.traveling.features.home.activity.HomeActivity;
import com.kelvin.traveling.features.login.activity.LogInActivity;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private TextInputLayout mainInputEmail;
    private TextInputLayout mainInputPass;
    private TextInputEditText email;
    private TextInputEditText password;
    private String userUserName;
    DBHelper DB;
    FragmentBLoginBinding binding;
    PendingIntent pendingIntent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBLoginBinding.inflate(inflater, container, false);

        DB = new DBHelper(requireContext());

        TextView snackRegisterNow = binding.tvForgetPassword;
        snackRegisterNow.setOnClickListener(v -> Snackbar.make(v, "Próximamente...", Snackbar.LENGTH_LONG).show());
        getArgsRegister();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        Log.d("Depurando", "Solicitud de permisos");

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

    public void getArgsRegister() {
        Bundle extras = getArguments();
        if (extras != null) {
            userUserName = extras.getString("username");
            String userEmail = extras.getString("email");
            String userPassword = extras.getString("password");
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
                Log.d("LoginFragment", "Successful Login");
                    navigateToHome(userEmail, userPass);
                showNotification(userUserName);
            } else {
                showAlertDialog();
            }
        }
    }

    private void navigateToHome(String userEmail, String userPass) {
        LoginFragmentDirections.ActionBLoginFragmentToHomeActivity action =
                LoginFragmentDirections.actionBLoginFragmentToHomeActivity(userEmail, userPass);
        NavHostFragment.findNavController(this).navigate(action);
    }

    // Permisos de notificación

    private void showNotification(String userUserName) {
        Log.d("Depurando", "Estoy en showNotification " + Build.VERSION.SDK_INT);
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.i("Info", "Permiso Concedido ");
            //Android SDK 26 o posterior:
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i("Info", "Version correcta");
                String id = "my_channel_01";

                //NotificationManager
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(requireContext());

                //Canal de notificaciones
                NotificationChannel channel = new NotificationChannel(id, "channelName", NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableVibration(true);

                notificationManagerCompat.createNotificationChannel(channel);

                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_notification);

                lanzarIntentNotificacion();

                NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(requireContext(), id)
                        .setSmallIcon(R.drawable.ic_home)
                        .setContentTitle("Bienvenido " + userUserName)
                        .setContentText("Nos alegra verte en este paraíso")
                        .setLargeIcon(imgBitmap)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                notificationManagerCompat.notify(1, noBuilder.build());

            }

        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            Log.d("Depurando", "Solicitud de permisos");
        }
    }

    private void lanzarIntentNotificacion() {
        Intent intent = new Intent(requireContext(), HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(requireContext());
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(intent);
        //pendindIntent = stackBuilder.gerPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

        /* Para evitar error tenemos que mostrar este if, si no se repoducirá este error.
        java.lang.IllegalArgumentException: com.example.notificaciones2: ...
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(
                    requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE
            );
        }
    }

    public void showAlertDialog() {
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