package com.jignesh.shopex.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jignesh.shopex.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageChooser extends AppCompatActivity {

    ImageView iv;
    Button btnImgChooser, btnImgUpload;

    int GALLERY_REQ_CODE = 2004;

    Boolean imgChoosen=false;

    StorageReference sr;

    Uri imgUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chooser);

        iv = findViewById(R.id.img_chooser_iv);
        btnImgChooser = findViewById(R.id.img_chooser_btn);
        btnImgUpload = findViewById(R.id.img_upload_btn);

        btnImgChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inChooseImg = new Intent(Intent.ACTION_PICK);
                inChooseImg.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(inChooseImg, GALLERY_REQ_CODE);
            }
        });

        btnImgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgChoosen){
                    if(imgUri!=null){
                        try {
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
                            Date d = new Date();
                            String defaultFileName = sdf.format(d);
                            sr = FirebaseStorage.getInstance().getReference("images/"+defaultFileName);
                            sr.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(ImageChooser.this, "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ImageChooser.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
                                    Log.d("dalle", e+"");
                                }
                            });
                        } catch (Exception e) {
                            Log.d("dalle", e+"");
                        }
                    }
                }else {
                    Toast.makeText(ImageChooser.this, "Image is to be selected first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                imgUri = data.getData();
                iv.setImageURI(data.getData());
                imgChoosen = true;
            }
        }
    }
}