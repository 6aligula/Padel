package com.example.clubdiversion.ui.admin;

import com.example.clubdiversion.data.entities.UserResponse;

import java.util.List;

public interface AdminUserContract {
    interface View {
        void showUsers(List<UserResponse> users);
        void showError(String error);
        void showSuccess(String message);
    }

    interface Presenter {
        void getAllUsers();
        void updateUser(int userId, UserResponse user, String password);
        void deleteUser(int userId);
    }
}
