package com.example.admin.clientvisit;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.clientvisit.database.AppDatabase;
import com.example.admin.clientvisit.database.BusinessEntity;
import com.example.admin.clientvisit.database.BusinessOwnerEntity;
import com.example.admin.clientvisit.database.ContactEntity;
import com.example.admin.clientvisit.database.DbUtil;
import com.example.admin.clientvisit.database.OwnerEntity;
import com.example.admin.clientvisit.model.ClientData;
import com.example.admin.clientvisit.util.Z;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.admin.clientvisit.util.AppCompatActivityWithPermission.MY_CAMERA_PERMISSITION_REQUEST;
import static com.example.admin.clientvisit.util.Z.isNotNullNotBlank;

public class ClientFragment extends Fragment {

    boolean UPDATE_MODE = false;
    boolean clientNameEditFlag = false, businessNameEditFlag = false;
    boolean contactDataEditFlag = false;
    boolean imageEdit = false;
    String oldClientName = "";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    View view;
    FloatingActionButton fab;
    Context context;
    ClientData clientData;
    ImageView addImage, image;
    Bitmap bitmap;
    String latitude, longitude;

    AppDatabase db;
    List<OwnerEntity> selectedOwnerList;
    List<OwnerEntity> TempOwnerListSelected;

    public static final int ADD_FEEDBACK_ACTIVITY_REQUEST_CODE = 111;
    public static final int PLACE_PICKER_REQUEST_CODE = 222;
    public static final int OPEN_CAMERA_INTENT_REQUEST_CODE = 333;


    TextInputEditText client_name, business_name, website_edit, mobile_edit, phone_edit, email_edit, choose_location, address_area, address_brief, address_pincode;

    public ClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_client, container, false);
        setHasOptionsMenu(true);
        context = getActivity();

        TempOwnerListSelected = new ArrayList<>();


        fab = view.findViewById(R.id.fab);
        client_name = view.findViewById(R.id.client_name);
        business_name = view.findViewById(R.id.business_name);
        website_edit = view.findViewById(R.id.website_edit);
        mobile_edit = view.findViewById(R.id.mobile_edit);
        phone_edit = view.findViewById(R.id.phone_edit);
        email_edit = view.findViewById(R.id.email_edit);
        choose_location = view.findViewById(R.id.choose_location);
        address_area = view.findViewById(R.id.address_area);
        address_brief = view.findViewById(R.id.address_brief);
        address_pincode = view.findViewById(R.id.address_pincode);

        addImage = view.findViewById(R.id.addImage);
        image = view.findViewById(R.id.image);


        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        db = AppDatabase.getInstance(getActivity());
        selectedOwnerList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            clientData = (ClientData) bundle.getSerializable(ClientListFragment.CLIENT_DATA_KEY);
            if (clientData != null) {
                populateData(clientData);
                ((NavigationActivity) getActivity()).getSupportActionBar().setTitle(clientData.getBusinessName());
                UPDATE_MODE = true;
                selectedOwnerList.clear();
                selectedOwnerList.addAll(clientData.getOwnerList());
            } else {
                fab.setVisibility(View.GONE);
                ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Add Client");
                UPDATE_MODE = false;
            }
        } else {
            fab.setVisibility(View.GONE);
            ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Add Client");
            UPDATE_MODE = false;
        }


        //textChange Listeners
        client_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clientNameEditFlag = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        business_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                businessNameEditFlag = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //common client data watcher
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactDataEditFlag = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        website_edit.addTextChangedListener(textWatcher);
        mobile_edit.addTextChangedListener(textWatcher);
        phone_edit.addTextChangedListener(textWatcher);
        email_edit.addTextChangedListener(textWatcher);
        address_area.addTextChangedListener(textWatcher);
        choose_location.addTextChangedListener(textWatcher);
        address_brief.addTextChangedListener(textWatcher);
        address_pincode.addTextChangedListener(textWatcher);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showVisitListFragment();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageEdit = true;
                showDialogImage();
            }
        });


        choose_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        client_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectOwnerIntent = new Intent(getActivity(), SelectOwnerDialog.class);
                if (UPDATE_MODE) {
                    selectOwnerIntent.putExtra("owners", (Serializable) clientData.getOwnerList());
                } else {          // while adding new entry before save

                    if (TempOwnerListSelected.size() > 0) {
                        selectOwnerIntent.putExtra("owners", (Serializable) TempOwnerListSelected);
                    }
                }

                startActivityForResult(selectOwnerIntent, 666);
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            onSaveClicked();

        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveClicked() {

        String clientName, businessName, website, mobile, phone, email, addressArea, location, addressBrief, addressPincode;

        clientName = client_name.getText().toString();
        businessName = business_name.getText().toString();
        website = website_edit.getText().toString();
        mobile = mobile_edit.getText().toString();
        phone = phone_edit.getText().toString();
        email = email_edit.getText().toString();
        addressArea = address_area.getText().toString();
        location = choose_location.getText().toString();
        addressBrief = address_brief.getText().toString();
        addressPincode = address_pincode.getText().toString();

        boolean errorFlag = false;
        if (!isNotNullNotBlank(clientName)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter Owner name", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(businessName)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter Business name", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(website)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter website", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(mobile)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter mobile number", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(email)  || email!=null && !Z.isEmailValid(email)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter valid email address", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(addressArea)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter Address Area", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(location)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly select location", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(addressBrief)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter valid Address", Toast.LENGTH_SHORT).show();
        } else if (!isNotNullNotBlank(addressPincode)) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter valid Pincode", Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            errorFlag = true;
            Toast.makeText(context, "Kindly provide image", Toast.LENGTH_SHORT).show();
        } else if (selectedOwnerList.size() == 0 && UPDATE_MODE) {
            errorFlag = true;
            Toast.makeText(context, "Kindly enter Owner name", Toast.LENGTH_SHORT).show();
        }

        if (errorFlag == false) {

            if (UPDATE_MODE == false) {             // new record

                new SaveClientTask(
                        businessName,
                        mobile,
                        phone,
                        email,
                        addressArea,
                        latitude,
                        longitude,
                        addressBrief,
                        addressPincode,
                        website,
                        bitmap
                ).execute();

            } else {                      // update


                boolean ownerUpdate = false, businessUpdate = false, contactUpdate = false, imageUpdate = false;

                if (clientNameEditFlag == true && !client_name.getText().toString().equals(oldClientName)) {
                    ownerUpdate = true;
                    Log.d("TAG", "update : -------  update owner");
                }
                if (businessNameEditFlag == true && !clientData.getBusinessName().equals(business_name.getText().toString())) {
                    businessUpdate = true;
                    Log.d("TAG", "update : -------  update business table");
                }

                if (contactDataEditFlag == true) {
                    contactUpdate = true;
                    Log.d("TAG", "update : ------- update contact data");
                }
                if (imageEdit == true) {
                    imageUpdate = true;
                    Log.d("TAG", "update : ------- update image");
                }


                if (ownerUpdate || businessUpdate || contactUpdate || imageUpdate) {

                    new UpdateDataTask(
                            ownerUpdate, businessUpdate, contactUpdate, imageUpdate,
                            clientData.getBusinessId(), selectedOwnerList,
                            business_name.getText().toString(),
                            mobile, phone, email,
                            addressArea, latitude, longitude,
                            addressBrief, addressPincode, website,
                            bitmap
                    ).execute();
                } else {
                    Log.d("TAG", "update : Nothing to update");
                }

            }// update mode
        }
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
    }

    public void populateData(ClientData clientData) {
        oldClientName = DbUtil.buildOwnerNameStringFromList(clientData);
        client_name.setText(oldClientName);
        business_name.setText(clientData.getBusinessName());
        website_edit.setText(clientData.getWebsite());
        mobile_edit.setText(clientData.getMobile());
        phone_edit.setText(clientData.getPhone());
        email_edit.setText(clientData.getEmail());
        address_area.setText(clientData.getAddressArea());
        latitude = clientData.getLatitude();
        longitude = clientData.getLongitude();
        choose_location.setText(latitude + ", " + longitude);
        address_brief.setText(clientData.getAddressBrief());
        address_pincode.setText(clientData.getAddressPincode());
        byte[] imageByte = clientData.getImage();
        if (imageByte != null) {
            bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            image.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "No image found", Toast.LENGTH_SHORT).show();
        }

        //reset edit flags
        clientNameEditFlag = false;
        businessNameEditFlag = false;
        contactDataEditFlag = false;
    }

    private void showVisitListFragment() {
        try {
            VisitDetailListFragment visitDetailListFragment = new VisitDetailListFragment();
            fragmentTransaction.replace(R.id.container, visitDetailListFragment, "visitDetailListFragment");
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(clientData.getBusinessId()));
            bundle.putString("name", clientData.getBusinessName());
            visitDetailListFragment.setArguments(bundle);
            fragmentTransaction.addToBackStack("visitDetailListFragment");
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d("TAG", "showVisitListFragment: exp " + e.getMessage());
        }
    }

    private void showClientListFragment() {
        try {
            ClientListFragment clientListFragment = new ClientListFragment();
            fragmentTransaction.replace(R.id.container, clientListFragment, "clientListFragment");
            fragmentTransaction.addToBackStack("visitDetailListFragment");
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d("TAG", "showVisitListFragment: exp " + e.getMessage());
        }
    }


    // Image

    void showDialogImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Action").setItems(new String[]{"Take Photo", "Choose Photo"}, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        openCameraIntent();
                    } else {
                        ((NavigationActivity) getActivity()).requestRunTimePermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSITION_REQUEST);
                    }


                } else if (which == 1) {
                    getImageFromGallery();
                }
            }
        });
        builder.show();
    }

    public void openCameraIntent() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, OPEN_CAMERA_INTENT_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                image.setImageBitmap(bitmap);
                imageEdit = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (requestCode == PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Log.d("TAG", "getLatLng: getLatLng " + place.getLatLng());
                Log.d("TAG", "onActivityResult: getAddress " + place.getAddress());
                Log.d("TAG", "onActivityResult: getLocale " + place.getLocale());
                Log.d("TAG", "onActivityResult: getName " + place.getName());

                if (place != null) {
                    latitude = String.valueOf(place.getLatLng().latitude);
                    longitude = String.valueOf(place.getLatLng().longitude);

                    String lats = latitude.substring(0, Math.min(latitude.length(), 8));  // show 8 char lat/lng
                    String lngs = longitude.substring(0, Math.min(longitude.length(), 8));
                    choose_location.setText(lats + ", " + lngs);
                    address_brief.setText(String.valueOf(place.getAddress()));
                }


            }
        }

        if (requestCode == OPEN_CAMERA_INTENT_REQUEST_CODE) {
            bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }

        if (requestCode == 666 && resultCode == 666) {
            selectedOwnerList = (List<OwnerEntity>) data.getSerializableExtra("owners");

            Log.d("TAG", "onActivityResult: " + selectedOwnerList);
            Log.d("TAG", "onActivityResult: " + selectedOwnerList.size());

            String owners = "";
            for (int i = 0; i < selectedOwnerList.size(); i++) {
                if (i == 0)
                    owners = selectedOwnerList.get(i).getOwnerName();
                else
                    owners = owners + ", " + selectedOwnerList.get(i).getOwnerName();
            }

            client_name.setText(owners);
            if (UPDATE_MODE) {
                clientData.setOwnerList(selectedOwnerList);
            } else
                TempOwnerListSelected = selectedOwnerList;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_PERMISSITION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCameraIntent();
        }
    }


    //              AsyncTasks


    class DeleteBusinessLessOwner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            db.ownerDao().deleteBusinessLessOwners();
            return null;
        }
    }

    class SaveClientTask extends AsyncTask<Void, Void, Boolean> {


        String businessName, clientName, mobile, phone, email, addressArea, latitude, longitude, addressBrief, addressPincode, website;
        Bitmap bitmap;

        public SaveClientTask(String businessName, String mobile, String phone, String email, String addressArea, String latitude, String longitude, String addressBrief, String addressPincode, String website, Bitmap bitmap) {
            this.businessName = businessName;
            this.mobile = mobile;
            this.phone = phone;
            this.email = email;
            this.addressArea = addressArea;
            this.latitude = latitude;
            this.longitude = longitude;
            this.addressBrief = addressBrief;
            this.addressPincode = addressPincode;
            this.website = website;
            this.bitmap = bitmap;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();

                db.beginTransaction();
                long bid = db.businessDao().insertBusinessData(new BusinessEntity(0, businessName));
//                long oid = db.ownerDao().insertOwnerData(new OwnerEntity(0, clientName));
                for (int i = 0; i < selectedOwnerList.size(); i++) {

                    db.businessOwnerDao().insertBusinessOwnerData(new BusinessOwnerEntity((int) bid, selectedOwnerList.get(i).getOid()));
                }
                db.contactDao().insertContactData(new ContactEntity(0, (int) bid, mobile, phone, email, addressArea, latitude, longitude, addressBrief, addressPincode, website, byteArray));
                db.setTransactionSuccessful();
                return true;

            } catch (Exception e) {
                Log.d("TAG", "Exp inserting data :" + e.getMessage());

            } finally {
                db.endTransaction();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(context, "successfully save!", Toast.LENGTH_SHORT).show();
                new DeleteBusinessLessOwner().execute();
                showClientListFragment();
            } else
                Toast.makeText(context, "Fail to save data!", Toast.LENGTH_SHORT).show();

        }
    }


    class UpdateDataTask extends AsyncTask<Void, Void, Boolean> {

        boolean ownerUpdate, businessUpdate, contactUpdate, imageUpdate;

        int bid;
        List<OwnerEntity> ownerEntities;

        String NewBusinessName;

        Bitmap bitmap;

        String mobile, phone, email, addressArea, latitude, longitude, addressBrief, addressPincode, website;


        public UpdateDataTask(boolean ownerUpdate, boolean businessUpdate, boolean contactUpdate, boolean imageUpdate, int bid, List<OwnerEntity> ownerEntities, String newBusinessName, String mobile, String phone, String email, String addressArea, String latitude, String longitude, String addressBrief, String addressPincode, String website, Bitmap bitmap) {
            this.ownerUpdate = ownerUpdate;
            this.businessUpdate = businessUpdate;
            this.contactUpdate = contactUpdate;
            this.imageUpdate = imageUpdate;
            this.bid = bid;
            this.ownerEntities = ownerEntities;
            this.NewBusinessName = newBusinessName;
            this.bitmap = bitmap;
            this.mobile = mobile;
            this.phone = phone;
            this.email = email;
            this.addressArea = addressArea;
            this.latitude = latitude;
            this.longitude = longitude;
            this.addressBrief = addressBrief;
            this.addressPincode = addressPincode;
            this.website = website;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                db.beginTransaction();


                if (ownerUpdate) {                                                   // Owner
                    db.businessOwnerDao().deleteAllByBusinessId(bid);

                    for (int i = 0; i < ownerEntities.size(); i++) {
                        db.businessOwnerDao().insertBusinessOwnerData(new BusinessOwnerEntity(bid, ownerEntities.get(i).getOid()));
                    }
                }
                if (businessUpdate) {                                                // Business
                    db.businessDao().updateBusinessName(NewBusinessName, bid);
                }
                if (imageUpdate) {                                                    // Image

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bitmap.recycle();
                    db.contactDao().updateImage(bid, byteArray);
                }
                if (contactUpdate) {                                                  // contact

                    db.contactDao().updateContact(bid,
                            mobile, phone, email,
                            addressArea,
                            latitude, longitude,
                            addressBrief, addressPincode, website);

                }


                db.setTransactionSuccessful();
                return true;

            } catch (Exception e) {
                Log.d("TAG", "UPDATE EXCEPTION ----- : " + e.getMessage());
            } finally {
                db.endTransaction();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(context, "Update data successfully", Toast.LENGTH_SHORT).show();
                new DeleteBusinessLessOwner().execute();
            } else
                Toast.makeText(context, "Fail to update Data", Toast.LENGTH_SHORT).show();
        }
    }


//    class UpdateClientDataTack extends AsyncTask<Void, Void, Void> {
//
//        int businessID;
//        String businessName, mobile, phone, email, addressArea, latitude, longitude, addressBrief, addressPincode, website;
//
//        public UpdateClientDataTack(int businessID, String businessName, String mobile, String phone, String email, String addressArea, String latitude, String longitude, String addressBrief, String addressPincode, String website) {
//            this.businessID = businessID;
//            this.businessName = businessName;
//            this.mobile = mobile;
//            this.phone = phone;
//            this.email = email;
//            this.addressArea = addressArea;
//            this.latitude = latitude;
//            this.longitude = longitude;
//            this.addressBrief = addressBrief;
//            this.addressPincode = addressPincode;
//            this.website = website;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//
//                db.contactDao().updateContact(businessID,
//                        mobile, phone, email,
//                        addressArea,
//                        latitude, longitude,
//                        addressBrief, addressPincode, website);
//
//                Log.d("TAG", "doInBackground: SuccessFully update contact data");
//            } catch (Exception e) {
//                Log.d("TAG", "doInBackground: Fail to update contact data");
//            }
//
//            return null;
//        }
//    }
//
//
//    class updateOwnersTask extends AsyncTask<Void, Void, Boolean> {
//        int bid;
//        List<OwnerEntity> ownerEntities;
//        boolean doneflag;
//
//        public updateOwnersTask(int bid, List<OwnerEntity> ownerEntities) {
//            this.bid = bid;
//            this.ownerEntities = ownerEntities;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            try {
//
//                db.beginTransaction();
//                db.businessOwnerDao().deleteAllByBusinessId(bid);
//
//                for (int i = 0; i < ownerEntities.size(); i++) {
//                    db.businessOwnerDao().insertBusinessOwnerData(new BusinessOwnerEntity(bid, ownerEntities.get(i).getOid()));
//                }
//                db.setTransactionSuccessful();
//                doneflag = true;
//            } catch (Exception e) {
//                Log.d("TAG", "update owner EXP: " + e.getMessage());
//                doneflag = false;
//            } finally {
//                db.endTransaction();
//            }
//
//            return doneflag;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            if (aBoolean)
//                Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(context, "Fail update data", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    class updateBusinessDataTask extends AsyncTask<Void, Void, Void> {
//
//        String NewBusinessName;
//        int bid;
//
//        public updateBusinessDataTask(String newBusinessName, int bid) {
//            NewBusinessName = newBusinessName;
//            this.bid = bid;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//                db.businessDao().updateBusinessName(NewBusinessName, bid);
//                Log.d("TAG", "update successfully business name ");
//            } catch (Exception e) {
//                Log.d("TAG", "Fail to update business name ");
//            }
//            return null;
//        }
//    }
//
//    class updateImageTask extends AsyncTask<Void, Void, Void> {
//
//        int bid;
//        Bitmap bitmap;
//
//        public updateImageTask(int bid, Bitmap bitmap) {
//            this.bid = bid;
//            this.bitmap = bitmap;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                bitmap.recycle();
//
//                db.contactDao().updateImage(bid, byteArray);
//                Log.d("TAG", "Image updated successfully");
//
//            } catch (Exception e) {
//                Log.d("TAG", "fail to update image");
//            }
//
//
//            return null;
//        }
//    }
//


}
