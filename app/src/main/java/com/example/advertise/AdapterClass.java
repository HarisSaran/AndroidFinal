package com.example.advertise;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    private Context mContext;
    private List<MovieModelClass>  mData;

    public AdapterClass(Context mContext, List<MovieModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(mData.get(position).getId());
        holder.name.setText(mData.get(position).getName());

        // Using glide to display the image since it is fast and efficient!!
        // Need to get the link to the image now.. Found in the moviesdb documentation..
        // https://image.tmdb.org/t/p/w500/xeItgLK9qcafxbd8kYgv7XnMEog.jpg
        // the link where general images are stored is https://image.tmdb.org/t/p/w500  the API returnes the rest of the link
        // we just add what the api returns and we get the image

            Glide.with(mContext).load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImg()).into(holder.image);


            // We can set the on click here
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(mContext,MoveDetailsActivity.class);
                Intent i = new Intent(mContext,AddMovieActivity.class);
                i.putExtra("results","https://image.tmdb.org/t/p/w500"+mData.get(position).getImg());
                i.putExtra("results2", "The Rating for "+ mData.get(position).name+" is "+ mData.get(position).id +"/10.  My Review:" );
                i.putExtra("results3", mData.get(position).name);

                mContext.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            id = itemView.findViewById(R.id.id_txt);
            name = itemView.findViewById(R.id.name_txt);
            image = itemView.findViewById(R.id.imageView);
        }
    }

}
