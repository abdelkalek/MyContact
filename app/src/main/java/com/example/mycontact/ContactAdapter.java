package com.example.mycontact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ContactAdapter extends Adapter<ContactAdapter.MHolder> {
Context mConext;
List<Contact> mList;

    public ContactAdapter(Context mConext, List<Contact> mList) {
        this.mConext = mConext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ContactAdapter.MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrow, parent, false);
        return new MHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.MHolder holder, int position) {
        Contact contact = mList.get(position);
        holder.tvNom.setText(contact.getName());
        holder.tvTel.setText(contact.getPhoneNumber());

        byte [] outImage = contact.getImage();
       if(outImage!=null) {
            ///////////////Convertion de Byte[] to Bitmap
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            ///////////////Fin de Convertion de Byte[] to Bitmap
            holder.imgPhoto.setImageBitmap(theImage);
       }
        //holder.imgPhoto.setImageBitmap(contact.getImage());
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+contact.getPhoneNumber()));
                    v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MHolder extends RecyclerView.ViewHolder {
        TextView tvNom , tvTel;
        ImageView imgPhoto , imgCall, imgMore;

        public MHolder(@NonNull View itemView) {

            super(itemView);
            tvNom = itemView.findViewById(R.id.textViewNom);
            tvTel = itemView.findViewById(R.id.textView2Telephone);
            imgPhoto = itemView.findViewById(R.id.imageRowUser);
            imgCall = itemView.findViewById(R.id.imageView2Call);
            imgMore = itemView.findViewById(R.id.imageView3Menu);
        }
    }
}
