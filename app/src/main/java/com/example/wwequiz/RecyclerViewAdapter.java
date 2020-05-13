package com.example.wwequiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<User> users = new ArrayList<>();
    private Context context;
    private static RecyclerViewClickListener itemListener;


    public RecyclerViewAdapter(ArrayList<User> users, Context context, RecyclerViewClickListener itemListener) {
        this.itemListener = itemListener;
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.userName.setText(String.valueOf(users.get(position).getName()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, users.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userName;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            userName = itemView.findViewById(R.id.name_user);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, users.get(getAdapterPosition()).getName());
        }
    }
}

