package com.example.clubdiversion.ui.instalaciones;

import android.content.Context;
import android.util.Log;

import com.example.clubdiversion.data.repository.InstalacionesRepository;

public class InstalacionesPresenter implements InstalacionesContract.Presenter {

    private final InstalacionesContract.View view;
    private final InstalacionesRepository repository;

    public InstalacionesPresenter(InstalacionesContract.View view, Context context) {
        this.view = view;
        this.repository = new InstalacionesRepository(context);
    }


    @Override
    public void handleMenuOption(int menuOption) {
        switch (menuOption) {
            case 1: // Opción 1
                view.navigateToDuda();
                break;
            case 2: // Opción 2
                view.navigateToDocumento();
                break;
            case 4: // Opción 4 (cerrar actividad)
                view.closeActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void navigateToReserve(int index) {
        // Validar que el índice esté en el rango válido
        String[] capacities = repository.getCapacities();
        String[] techado = repository.getTechadoStates();
        String[] areas = repository.getAreas();

        if (index < 1 || index >= capacities.length) {
            view.showAdminError(); // O muestra un error más descriptivo
            return;
        }

        String espacio = "Espacio " + index;

        view.navigateToReservaciones(index, espacio, capacities[index], techado[index], areas[index]);
    }

    public int getImageIndex(int index) {
        Log.d("InstalacionesPresenter", "Índice recibido para imagen: " + index);
        if (index >= 1 && index <= 9) {
            return index - 1; // Devuelve el índice lógico
        } else {
            Log.e("InstalacionesPresenter", "Índice fuera de rango: " + index);
            return -1; // Índice inválido
        }
    }

}
