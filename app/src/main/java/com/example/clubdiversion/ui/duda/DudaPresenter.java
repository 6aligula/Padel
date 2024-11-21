package com.example.clubdiversion.ui.duda;

import android.content.Context;

import com.example.clubdiversion.data.repository.DudaRepository;

import java.util.List;

public class DudaPresenter implements DudaContract.Presenter {

    private final DudaContract.View view;
    private final DudaRepository repository;

    public DudaPresenter(DudaContract.View view, Context context) {
        this.view = view;
        this.repository = new DudaRepository(context);
    }

    @Override
    public void loadDudaList() {
        List<String> dudas = repository.getDudaList();
        List<String> respuestas = repository.getResponseList();
        view.showDudaList(dudas, respuestas);
    }

    @Override
    public void createDuda(String duda, String respuesta) {
        if (repository.isDudaRegistered(duda)) {
            view.showError("Duda ya registrada");
        } else {
            repository.insertDuda(duda, respuesta);
            view.showDudaCreated("Duda creada con éxito");
            view.clearFields();
            loadDudaList();
        }
    }

    @Override
    public void updateDuda(String duda, String respuesta) {
        if (duda.isEmpty() || respuesta.isEmpty()) {
            view.showError("Los campos no pueden estar vacíos");
            return;
        }
        repository.updateDuda(duda, respuesta);
        view.showDudaCreated("Respuesta actualizada con éxito");
        view.clearFields();
        loadDudaList();
    }

    @Override
    public void checkUserType() {
        String userType = repository.isCurrentUserAdmin() ? "Administrador" : "Socio";
        view.setUserType(userType);
        view.enableResponseField("Administrador".equals(userType));
    }
    @Override
    public void clearFields() {
        view.clearFields();
        view.enableResponseField(false);
    }

}
