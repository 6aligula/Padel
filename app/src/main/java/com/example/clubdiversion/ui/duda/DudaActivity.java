package com.example.clubdiversion.ui.duda;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubdiversion.R;

import java.util.List;

public class DudaActivity extends AppCompatActivity implements DudaContract.View {

    private DudaContract.Presenter presenter;
    private Spinner spiDuda;
    private TextView txtSocioDuda;
    private EditText editTextoDuda, editTextoRespDuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duda);

        spiDuda = findViewById(R.id.spiDuda);
        txtSocioDuda = findViewById(R.id.txtSocioDuda);
        editTextoDuda = findViewById(R.id.editTextoDuda);
        editTextoRespDuda = findViewById(R.id.editTextoRespDuda);

        presenter = new DudaPresenter(this, this);

        presenter.loadDudaList();
        presenter.checkUserType();
    }

    public void btnCreaDuda(View view) {
        String duda = editTextoDuda.getText().toString().trim();
        String respuesta = "Por responder";
        presenter.createDuda(duda, respuesta);
    }

    public void btnRespDuda(View view) {
        String duda = editTextoDuda.getText().toString().trim();
        String respuesta = editTextoRespDuda.getText().toString().trim();
        presenter.updateDuda(duda, respuesta);
    }

    public void bntIniciar(View view) {
        presenter.loadDudaList();
        presenter.checkUserType();
        presenter.clearFields();
    }


    @Override
    public void showDudaList(List<String> dudas, List<String> respuestas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_doc, dudas);
        spiDuda.setAdapter(adapter);
    }

    @Override
    public void showDudaCreated(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearFields() {
        editTextoDuda.setText("");
        editTextoRespDuda.setText("");
    }

    @Override
    public void setUserType(String userType) {
        txtSocioDuda.setText(userType);
    }

    @Override
    public void enableResponseField(boolean enabled) {
        editTextoRespDuda.setEnabled(enabled);
    }
}
