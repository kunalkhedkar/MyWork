package com.example.admin.clientvisit.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.clientvisit.database.AppDatabase;
import com.example.admin.clientvisit.database.BusinessEntity;
import com.example.admin.clientvisit.database.ContactEntity;
import com.example.admin.clientvisit.database.OwnerEntity;
import com.example.admin.clientvisit.model.ClientData;

import java.util.ArrayList;
import java.util.List;

public class ClientViewModel extends AndroidViewModel {

//    private LiveData<List<ClientData>> clientDataList = new MutableLiveData<>();

    private MutableLiveData<List<ClientData>> clientDataList;
    private AppDatabase appDatabase;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        clientDataList = new MutableLiveData<>();
        appDatabase = AppDatabase.getInstance(application);
    }

    public LiveData<List<ClientData>> buildClientdataList() {

        new buildClientDataTask().execute();

        return clientDataList;
    }

//    private String buildOwnerStringList(List<Integer> oIDs) {
//        List<OwnerEntity> ownerEntities =new ArrayList<>();
//
//        for (int i = 0; i < oIDs.size(); i++) {
//
//            ownerEntities.add(appDatabase.ownerDao().getOwnerNameByOid(oIDs.get(i)));
//        }
//        return new String(buffer);
//    }


    class buildClientDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            List<ClientData> clientDataListTemp = new ArrayList<>();

            int bid;
            String businessName;
            List<BusinessEntity> businessEntities = appDatabase.businessDao().getAllBusinessData();
            if (businessEntities != null) {
                for (int i = 0; i < businessEntities.size(); i++) {
                    bid = businessEntities.get(i).getBid();
                    businessName = businessEntities.get(i).getBusinessName();
                    ContactEntity contactEntities = appDatabase.contactDao().getContactByBusinessId(bid);
                    List<OwnerEntity> owners = appDatabase.businessOwnerDao().getListOfOwnerObjByBusinessId(bid);

                    ClientData clientData = new ClientData(
                            bid,
                            owners,
                            businessName,
                            contactEntities.getWebsite(),
                            contactEntities.getMobile(),
                            contactEntities.getPhone(),
                            contactEntities.getEmail(),
                            contactEntities.getLatitude(),
                            contactEntities.getLongitude(),
                            contactEntities.getAddressArea(),
                            contactEntities.getAddressBrief(),
                            contactEntities.getPincode(),
                            contactEntities.getImageByteArray()
                    );

                    clientDataListTemp.add(clientData);
                }

                clientDataList.postValue(clientDataListTemp);


            }
            return null;
        }
    }

    //TODO : move async to viewmodel here
}
