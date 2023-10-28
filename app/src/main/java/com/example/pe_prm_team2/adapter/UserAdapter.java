package com.example.pe_prm_team2.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe_prm_team2.R;
import com.example.pe_prm_team2.UserDetailActivity;
import com.example.pe_prm_team2.api.ApiService;
import com.example.pe_prm_team2.model.User;
import com.example.pe_prm_team2.model.UserDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> listUsers;
    private User user = new User();
    private Context context;
    public UserAdapter() {
    }

    public UserAdapter(List<User> listUsers, Context context) {
        this.context = context;
        this.listUsers = listUsers;
    }

    public  void setUserSearchResult(List<User> searchResult) {
        this.listUsers = searchResult;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = listUsers.get(position);
        if (user==null)
            return;
        Picasso.get().load(user.getAvatar()).into(holder.imageView);
        holder.tv_user.setText(user.getFirst_name()+" " +user.getLast_name());
        holder.tv_email.setText(user.getEmail());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserApiDetail(user.getId());

            }
        });
    }

    public void getUserApiDetail(int id){
        ApiService.apiservice.getUserApiDetail(id).enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                UserDetail userDetail= response.body();

                user= userDetail.getData();
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra("user", user);
                System.out.println(userDetail);
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                System.out.println("");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listUsers!=null)
            return listUsers.size();
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tv_user, tv_email;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_avatar);
            tv_user=itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
        }
    }
}
