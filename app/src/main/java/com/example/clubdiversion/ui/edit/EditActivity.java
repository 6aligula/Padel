package com.example.clubdiversion.ui.edit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubdiversion.R;
import com.example.clubdiversion.data.entities.UserResponse;
import com.example.clubdiversion.data.repository.UserRepository;

public class EditActivity extends AppCompatActivity implements EditContract.View {
    private EditText editNIP, editName, editDireccion, editTelefono, editPassword;
    private Button btnSaveUser;
    private EditPresenter presenter;

    private int userId;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editNIP = findViewById(R.id.editNIP);
        editName = findViewById(R.id.editName);
        editDireccion = findViewById(R.id.editDireccion);
        editTelefono = findViewById(R.id.editTelefono);
        editPassword = findViewById(R.id.editPassword);
        btnSaveUser = findViewById(R.id.btnSaveUser);

        UserRepository userRepository = new UserRepository(this);
        boolean loggedInIsAdmin = userRepository.isCurrentUserAdmin();
        Log.d("EditActivity", "Usuario logueado isAdmin: " + loggedInIsAdmin);

        // Recibir datos del Intent
        userId = getIntent().getIntExtra("user_id", -1);
        String userNIP = getIntent().getStringExtra("user_nip");
        String userName = getIntent().getStringExtra("user_name");
        String userDireccion = getIntent().getStringExtra("user_direccion");
        String userTelefono = getIntent().getStringExtra("user_telefono");

        editNIP.setText(userNIP);
        editName.setText(userName);
        editDireccion.setText(userDireccion);
        editTelefono.setText(userTelefono);

        if (!loggedInIsAdmin) {
            editNIP.setEnabled(false); // Solo admins logueados pueden editar el NIP
        }

        Log.d("EditActivity", "isAdmin recibido: " + isAdmin);

        // Instanciar el presenter
        presenter = new EditPresenter(this, new UserRepository(this).getToken());

        btnSaveUser.setOnClickListener(view -> {
            String updatedNIP = editNIP.getText().toString().trim();
            String updatedName = editName.getText().toString().trim();
            String updatedDireccion = editDireccion.getText().toString().trim();
            String updatedTelefono = editTelefono.getText().toString().trim();
            String updatedPassword = editPassword.getText().toString().trim();

            UserResponse updatedUser = new UserResponse(userId, updatedNIP, updatedName, updatedDireccion, updatedTelefono, false);
            presenter.updateUser(userId, updatedUser, updatedPassword.isEmpty() ? null : updatedPassword);
        });
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish(); // Regresar al panel de admin o cerrar la actividad
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
