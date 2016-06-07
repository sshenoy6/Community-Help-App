package com.androidapps.swathi.communityhelpapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swathi on 5/31/2016.
 */
public class RequestStatusFragment extends Fragment {

    public RequestStatusFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_requeststatus, container, false);

        ListView listView = (ListView)view.findViewById(R.id.requestStatusText);
        DataBaseHelper db = new DataBaseHelper(getContext());
        List<UserRequest> allRequests = db.getAllRequests();
        List<String> requests = new ArrayList<String>();
        for(int i=0;i<allRequests.size();i++){
            String req = allRequests.get(i).getActivity() + " "+allRequests.get(i).getNumberOfRequests()+" "+allRequests.get(i).getRequestorName()+" "+allRequests.get(i).getRequestStatus();
            requests.add(req);
        }
        final ArrayAdapter<String> aa = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,requests);
        listView.setAdapter(aa);
        return view;
    }
}
