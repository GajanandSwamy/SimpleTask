package com.example.gajanand.simpletask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyViewHolder> {

    private List<UserListResponse> moviesList;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);

        return new MyViewHolder(itemView);
    }

    public AdapterUser(List<UserListResponse> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserListResponse movie = moviesList.get(position);
        holder.id.setText(movie.getId());
        holder.name.setText(movie.getName());
        holder.email.setText(movie.getEmail());
        holder.com_code.setText(movie.getCom_code());
        holder.status.setText(movie.getStatus());
        holder.password.setText(movie.getPassword());
        holder.forgot.setText(movie.getForgot());

    }

    @Override
    public int getItemCount() {
         return moviesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, email,password,com_code,status,forgot;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            password = (TextView) view.findViewById(R.id.password);
            com_code = (TextView) view.findViewById(R.id.com_code);
            status = (TextView) view.findViewById(R.id.status);
            forgot = (TextView) view.findViewById(R.id.forgot);

        }
    }
}
