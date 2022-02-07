package com.sumonkmr.sharingmomments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class AddVideos extends AppCompatActivity {

    VideoView videoView;
    Button browse,upload;
    Uri videoUri;
    MediaController mediaController;
    EditText vtitle;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_videos);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("videos");

        videoView = findViewById(R.id.videoView);
        browse = findViewById(R.id.browse);
        upload = findViewById(R.id.upload);
        vtitle = findViewById(R.id.vtitle);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();

        browse.setOnClickListener(view -> {
            Dexter.withContext(getApplicationContext())
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent();
                            intent.setType("video/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent,101);

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        });

        upload.setOnClickListener(view -> {
            proccesVideoUploading();
        });



    }

    public String GetExtention(){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(videoUri));
    }

    private void proccesVideoUploading() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Media Uploader");
        progressDialog.show();

        StorageReference uploader = storageReference.child("videos/"+System.currentTimeMillis()+"."+GetExtention());
        uploader.putFile(videoUri)
                .addOnSuccessListener(taskSnapshot -> {
                    uploader.getDownloadUrl().addOnSuccessListener(uri -> {
                        ModelClass modelClass = new ModelClass(vtitle.getText().toString(),uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(modelClass)
                                .addOnSuccessListener(unused -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Successfully Uploaded!!", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Failed to Upload!!", Toast.LENGTH_SHORT).show();
                                });
                    });
                })


                .addOnProgressListener(snapshot -> {
                    float persentage = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploading: " + (int)persentage + " %");
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK){
            assert data != null;
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
        }
    }
}