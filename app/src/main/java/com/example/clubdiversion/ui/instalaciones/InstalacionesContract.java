package com.example.clubdiversion.ui.instalaciones;

public interface InstalacionesContract {

    interface View {
        void navigateToDuda();
        void navigateToDocumento();
        void navigateToRegister();
        void navigateToReservaciones(int num, String espacio, String capacidad, String techado, String area);
        void showAdminError();
        void closeActivity();
    }

    interface Presenter {
        void handleMenuOption(int itemId);
        void navigateToReserve(int viewId);
    }
}
