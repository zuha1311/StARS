package com.example.producta.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.producta.R;
import com.example.producta.View.SearchActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.Reference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;



public class UploadProductsFragment extends Fragment {

    private View uploadProductsView;
    private EditText name,description, price;
    private RadioButton sportRadio, periodRadio;
    private RadioGroup radioGroup, radioGroup2;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference productsRef;
    private String sportSelected, periodSelected;
    private Button uploadProduct;
    private String uid;
    private ImageView uploadPicture;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String saveCurrentDate, saveCurrentTime;
    private String productRandomKey, downloadImageUrl, currentUser;
    private StorageReference productImageRef;


    public UploadProductsFragment()
    {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        uploadProductsView = inflater.inflate(R.layout.fragment_uploadnew,container,false);

        name = uploadProductsView.findViewById(R.id.productNameEditText);
        description = uploadProductsView.findViewById(R.id.descriptionEditText);
        price = uploadProductsView.findViewById(R.id.rateEditText);
        uploadPicture = uploadProductsView.findViewById(R.id.uploadPicture);

        uploadProduct = uploadProductsView.findViewById(R.id.submitProductButton);

        radioGroup = uploadProductsView.findViewById(R.id.radioGroup);
        radioGroup2  = uploadProductsView.findViewById(R.id.radioGroup2);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();

        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
                uploadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForm();
            }
        });



        return uploadProductsView;
    }

    private void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode == Activity.RESULT_OK && data!=null)
        {
                ImageUri = data.getData();
                uploadPicture.setImageURI(ImageUri);
        }
    }

    private void checkForm() {


        if(name.getText().toString().isEmpty() || description.getText().toString().isEmpty() ||
        description.getText().toString().isEmpty() || radioGroup.getCheckedRadioButtonId() == -1 || radioGroup2.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getContext(), "Please fill in all the details", Toast.LENGTH_LONG).show();
        }

        else{
            final String productNameforDB = name.getText().toString();
            final String descriptionforDB = description.getText().toString();
            final String rateforDB = price.getText().toString();

            int selectId = radioGroup.getCheckedRadioButtonId();
            int selectId2 = radioGroup2.getCheckedRadioButtonId();

            if(selectId != -1 && selectId2 !=-1)
            {
                sportRadio = uploadProductsView.findViewById(selectId);
                sportSelected = sportRadio.getText().toString();

                periodRadio = uploadProductsView.findViewById(selectId2);
                periodSelected = periodRadio.getText().toString();

                Toast.makeText(getContext(), "Sport:"+sportSelected, Toast.LENGTH_SHORT).show();
                saveProductInfo(productNameforDB,descriptionforDB,rateforDB,sportSelected, periodSelected);
            }


        }
    }

    private void saveProductInfo(String productNameforDB, String descriptionforDB, String rateforDB, String sportSelected, String periodSelected) {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat(("MMM dd, yyyy"));
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat(("HH:mm:ss a"));
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getContext(), "Error: "+message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Image Uploaded Successfully.. ", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(getContext(), "Image Url successfully retrieved", Toast.LENGTH_SHORT).show();
                            saveInfoToDatabase(productNameforDB,descriptionforDB,rateforDB,sportSelected, periodSelected);
                        }
                    }
                });
            }
        });








    }

    private void saveInfoToDatabase(String productNameforDB, String descriptionforDB, String rateforDB, String sportSelected, String periodSelected) {

        productsRef = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> productDataMap = new HashMap<>();

        productDataMap.put("image", downloadImageUrl);
        productDataMap.put("name",productNameforDB);
        productDataMap.put("description",descriptionforDB);
        productDataMap.put("rate",rateforDB);
        productDataMap.put("pid", productRandomKey);
        productDataMap.put("sport",sportSelected);
        productDataMap.put("period", periodSelected);
        productDataMap.put("uid",currentUser);
        productDataMap.put("productStatus","Not Rented");

        productsRef.child("Products of user").child(currentUser).child(productRandomKey).updateChildren(productDataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(getContext(), "Product Added to User Account Successfully", Toast.LENGTH_LONG).show();
                            name.setText("");
                            description.setText("");
                            price.setText("");
                            radioGroup.clearCheck();
                            radioGroup2.clearCheck();

                            productsRef.child("Products").child(productRandomKey).updateChildren(productDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getContext(), "Product added to inventory", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), SearchActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(getContext(), "Failure in Uploading product ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
