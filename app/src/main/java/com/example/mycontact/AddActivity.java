package com.example.mycontact;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST  = 100;
    private static final int PICK_Camera_REQUEST = 114;
    Bitmap imageBitmap;
    ImageView imagePhoto;
    EditText edNom , edTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imagePhoto = findViewById(R.id.imageViewPhoto);
        edNom = findViewById(R.id.txtName);
        edTel = findViewById(R.id.txtPhone);
        getSupportActionBar().hide();
    }

    public void btncameraClick(View view) {
        Intent takeintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takeintent, PICK_Camera_REQUEST);
    }

    public void btngalleryClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
    }

    public void btnEnregisterClick(View view) {
      Contact contact = new Contact();
        contact.setName(edNom.getText().toString());
        contact.setPhoneNumber(edTel.getText().toString());
        if(imagePhoto!=null){
        /*Convertion Byte to Arry */
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();
        /* Fin Convertion Byte to Arry */
        contact.setImage(imageInByte);}

        DataBaseHandler db = new DataBaseHandler(this);
      //  db.addContact(new Contact(edNom.getText().toString(),edTel.getText().toString(),imageInByte));
     db.addContact(contact);
        this.finish();

    }


    public void btnAnnulerClick(View view) {
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null ) {
            //Bundle extras = data.getExtras();
            // imageBitmap = (Bitmap) extras.get("data");
            // img.setImageBitmap(imageBitmap);
            Uri filePath = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagePhoto.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
          //  imagePhoto.setImageURI(filePath);
        }
        if (requestCode == PICK_Camera_REQUEST && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imagePhoto.setImageBitmap(imageBitmap);
        }
    }
}