package com.example.clubdiversion.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubdiversion.R;
import com.example.clubdiversion.data.entities.UserResponse;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface OnUserDeleteListener {
        void onDelete(int userId);
    }

    private final List<UserResponse> userList;
    private final OnUserDeleteListener onDeleteListener;

    public interface OnUserClickListener {
        void onClick(UserResponse user);
    }

    private final OnUserClickListener onClickListener;

    public UserAdapter(List<UserResponse> userList, OnUserDeleteListener onDeleteListener, OnUserClickListener onClickListener) {
        this.userList = userList;
        this.onDeleteListener = onDeleteListener;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserResponse user = userList.get(position);

        holder.txtName.setText(user.getName());
        holder.txtUsername.setText(user.getUsername());
        holder.txtTelefono.setText(user.getTelefono());
        holder.txtDireccion.setText(user.getDireccion());

        holder.btnDelete.setOnClickListener(view -> {
            if (onDeleteListener != null) {
                onDeleteListener.onDelete(user.getId());
            }
        });

        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtUsername, txtTelefono, txtDireccion;
        Button btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
