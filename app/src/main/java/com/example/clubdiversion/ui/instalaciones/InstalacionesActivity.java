package com.example.clubdiversion.ui.instalaciones;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.clubdiversion.R;
import com.example.clubdiversion.ui.documento.DocumentoActivity;
import com.example.clubdiversion.ui.duda.DudaActivity;
import com.example.clubdiversion.ui.register.RegisterActivity;
import com.example.clubdiversion.ui.reservaciones.ReservacionesActivity;

public class InstalacionesActivity extends AppCompatActivity implements InstalacionesContract.View {

    private InstalacionesPresenter presenter;
    private SparseArray<Integer> viewIdToIndexMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalaciones);

        presenter = new InstalacionesPresenter(this, this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializar el mapeo
        viewIdToIndexMap = new SparseArray<>();
        viewIdToIndexMap.put(R.id.layout1, 1);
        viewIdToIndexMap.put(R.id.layout2, 2);
        viewIdToIndexMap.put(R.id.layout3, 3);
        viewIdToIndexMap.put(R.id.layout4, 4);
        viewIdToIndexMap.put(R.id.layout5, 5);
        viewIdToIndexMap.put(R.id.layout6, 6);
        viewIdToIndexMap.put(R.id.layout7, 7);
        viewIdToIndexMap.put(R.id.layout8, 8);
        viewIdToIndexMap.put(R.id.layout9, 9);
    }

    @Override
    public void navigateToReservaciones(int num, String espacio, String capacidad, String techado, String area) {
        int logicalIndex = presenter.getImageIndex(num);

        if (logicalIndex != -1) {
            int[] imageResources = {
                    R.drawable.descarga1, R.drawable.descarga2, R.drawable.descarga3,
                    R.drawable.descarga4, R.drawable.descarga5, R.drawable.descarga6,
                    R.drawable.descarga7, R.drawable.descarga8, R.drawable.descarga9
            };

            Intent intent = new Intent(this, ReservacionesActivity.class);
            intent.putExtra("installation_id", num); // Pasa el ID de la instalación en lugar de "Imagen"
            intent.putExtra("Imagen", imageResources[logicalIndex]); // paso la imagen
            intent.putExtra("Espacio", espacio);
            intent.putExtra("capacidad", capacidad);
            intent.putExtra("techado", techado);
            intent.putExtra("area", area);
            Log.d("InstalacionesActivity", "Imagen enviada: " + imageResources[logicalIndex]);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Índice de imagen inválido", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuinsta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Mapear `R.id` a un identificador genérico
        int menuOption = 0;
        if (item.getItemId() == R.id.Item1) {
            menuOption = 1;
        } else if (item.getItemId() == R.id.Item2) {
            menuOption = 2;
        } else if (item.getItemId() == R.id.Item4) {
            menuOption = 4;
        }

        presenter.handleMenuOption(menuOption);
        return true;
    }

    public void irReserve(View view) {
        // Mapeo en la Activity, evitando usar R en el Presenter
        int viewId = view.getId();
        int index = getMappedIndex(viewId);

        if (index != -1) { // -1 indica que el ID no está mapeado
            presenter.navigateToReserve(index);
        } else {
            Toast.makeText(this, "Error al seleccionar la instalación", Toast.LENGTH_SHORT).show();
        }
    }

    // Mapeo de IDs de vistas a índices
    private int getMappedIndex(int viewId) {
        return viewIdToIndexMap.get(viewId, -1); // Retorna -1 si no se encuentra el ID
    }

    @Override
    public void navigateToDuda() {
        Intent intent = new Intent(this, DudaActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToDocumento() {
        Intent intent = new Intent(this, DocumentoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAdminError() {
        Toast.makeText(this, "Usted no es Administrador", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("InstalacionesActivity", "onDestroy llamado: Recursos liberados.");
    }

}
