package com.example.clubdiversion.ui.main;

import android.content.Context;
import com.example.clubdiversion.data.entities.SocioDB;
import com.example.clubdiversion.data.repository.UserRepository;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private final UserRepository userRepository;
    public MainPresenter(MainContract.View view, Context context) {
        this.view = view;
        this.userRepository = new UserRepository(context);
    }

    @Override
    public void checkUser() {
        SocioDB currentUser = userRepository.getCurrentUser();

        if (currentUser == null || currentUser.getId() == null) {
            view.navigateToLogin();
        } else {
            String welcomeMessage = currentUser.getSocioAdmin() ?
                    "Bienvenido Admin: " + currentUser.getNombre() :
                    "Bienvenido Usuario: " + currentUser.getNombre();
            view.showWelcomeMessage(welcomeMessage);
        }
    }

    @Override
    public void logout() {
        boolean isLoggedOut = userRepository.logoutUser();

        if (isLoggedOut) {
            view.navigateToLogin();
        } else {
            view.showWelcomeMessage("No se pudo cerrar sesión");
        }
    }
}
