package com.androidapps.swathi.communityhelpapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_selectable_list_item;

public class MainActivity extends AppCompatActivity {

    static int activity_counter_babysitting = 0;
    static int activity_counter_activities = 0;
    static int activity_counter_elderly = 0;
    static int activity_counter_grocery = 0;
    static int activity_counter_emergency = 0;
    static int activity_counter_tutoring = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        UserRequest userRequest = new UserRequest();
        userRequest.setRequestorName(Login.USER_NAME);
        userRequest.setRequestStatus("U");
        StringBuilder message = new StringBuilder();
        if(id == R.id.babysitting){
            message.append("Hi. Can one of you please babysit my kid tonight? Thanks in advance");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Hi. Can one of you please babysit my kid tonight? Thanks in advance");
            userRequest.setActivity("Baby Sitting");
            userRequest.setMessage(message.toString());
            activity_counter_babysitting++;
            userRequest.setNumberOfRequests(activity_counter_babysitting);
            message.setLength(0);
        }
        else if(id== R.id.activities){
            message.append("Hi. Can one of you please take my daughter out for a swim?? Thanks in advance");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi. Can one of you please take my daughter out for a swim?? Thanks in advance");
            activity_counter_activities++;
            userRequest.setMessage(message.toString());
            message.setLength(0);
            userRequest.setActivity("Sports and Activities");
            userRequest.setNumberOfRequests(activity_counter_activities);
        }else if(id == R.id.elderly){
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi. Is anyone free now?? I am watching a movie in sometime and would love some company!!Thanks in advance");
            message.append("Hi. Is anyone free now?? I am watching a movie in sometime and would love some company!!Thanks in advance");
            activity_counter_elderly++;
            userRequest.setMessage(message.toString());
            message.setLength(0);
            userRequest.setActivity("Services related to Elderly");
            userRequest.setNumberOfRequests(activity_counter_elderly);
        }else if(id == R.id.grocery){
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi. I need a carton of milk and bread. Can someone please pick it up for me?? Thanks in advance");
            message.append("Hi. I need a carton of milk and bread. Can someone please pick it up for me?? Thanks in advance");
            activity_counter_grocery++;
            userRequest.setMessage(message.toString());
            message.setLength(0);
            userRequest.setActivity("Grocery");
            userRequest.setNumberOfRequests(activity_counter_grocery);
        }else if(id == R.id.tutoring){
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi. I need some help solving my Math quiz. Is anyone available to tutor?? Thanks in advance");
            message.append("Hi. I need some help solving my Math quiz. Is anyone available to tutor?? Thanks in advance");
            activity_counter_tutoring++;
            userRequest.setMessage(message.toString());
            message.setLength(0);
            userRequest.setActivity("Tutoring");
            userRequest.setNumberOfRequests(activity_counter_tutoring);
        }else if(id == R.id.emergency){
            activity_counter_emergency++;
            userRequest.setActivity("Emergency Help");
            userRequest.setMessage("Emergency. Dial 911");
            userRequest.setNumberOfRequests(activity_counter_emergency);
            Intent callIntent = new Intent();
            String num = "911";
            callIntent.setAction(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" +num));
            startActivity(callIntent);
            return super.onOptionsItemSelected(item);
        }

        DataBaseHelper db = new DataBaseHelper(this);
        db.insertRequestDetails(userRequest);

        PackageManager pm = getPackageManager();
        sendIntent.putExtra(Intent.EXTRA_TEXT, message.toString());
        sendIntent.setType("text/plain");

        String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("whatsapp") || packageName.contains("android.email") || packageName.contains("bluetooth") || packageName.contains(defaultSmsPackageName)) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,sendIntent.getStringExtra(Intent.EXTRA_TEXT));
                intent.setType("text/plain");

                Log.d("MainActivity","Intent Package :"+i+" "+packageName);
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
                Log.d("MainActivity","Intent List size "+intentList.size());
            }else{
                continue;
            }
        }
        Intent chooser = Intent.createChooser(intentList.remove(0), "Complete action using: ");
        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);
        Log.d("MainActivity","Size of final array "+extraIntents.length);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(chooser);
        return super.onOptionsItemSelected(item);
    }
    public void viewRequests(View view){

        Intent requestIntent = new Intent(this,RequestActivity.class);
        startActivity(requestIntent);
    }
    }