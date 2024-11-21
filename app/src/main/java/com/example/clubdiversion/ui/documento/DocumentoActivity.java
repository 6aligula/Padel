package com.example.clubdiversion.ui.documento;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.clubdiversion.MainActivity2;
import com.example.clubdiversion.R;
import com.example.clubdiversion.data.repository.DocumentoRepository;

import java.util.List;

public class DocumentoActivity extends AppCompatActivity implements DocumentoContract.View {

    private DocumentoPresenter presenter;

    private EditText editDecDocum, editLinkDocum;
    private Spinner spiDesDoc;
    private TextView txtNonDoc;
    private List<String> descriptions;
    private List<String> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editDecDocum = findViewById(R.id.editDecDocum);
        editLinkDocum = findViewById(R.id.editLinkDocum);
        spiDesDoc = findViewById(R.id.spiDesDoc);
        txtNonDoc = findViewById(R.id.txtNonDoc);

        presenter = new DocumentoPresenter(this, new DocumentoRepository(this));

        presenter.checkUserType();
        presenter.loadDocuments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regresar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Salir1) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDocuments(List<String> descriptions, List<String> links) {
        this.descriptions = descriptions;
        this.links = links;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_doc, descriptions);
        spiDesDoc.setAdapter(adapter);

        spiDesDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    navigateToDocumentViewer(descriptions.get(position), links.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    @Override
    public void navigateToDocumentViewer(String description, String link) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("Descripcion", description);
        intent.putExtra("Link", link);
        startActivity(intent);
    }

    @Override
    public void setUserType(String userType) {
        txtNonDoc.setText(userType);
        boolean isAdmin = "Administrador".equals(userType);
        editDecDocum.setEnabled(isAdmin);
        editLinkDocum.setEnabled(isAdmin);
    }

    @Override
    public void showAdminError() {
        Toast.makeText(this, "Operación no permitida", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearFields() {
        editDecDocum.setText("");
        editLinkDocum.setText("");
    }

    public void onAddDocument(View view) {
        presenter.addDocument(editDecDocum.getText().toString(), editLinkDocum.getText().toString());
    }
}
