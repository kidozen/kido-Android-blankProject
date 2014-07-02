package com.kidozen.kidozenblankproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpStatus;

import java.util.concurrent.CountDownLatch;

import kidozen.client.KZApplication;
import kidozen.client.ServiceEvent;
import kidozen.client.ServiceEventListener;



public class HelloKidoActivity extends Activity {

    final String TENANT = "https://kidodemo.kidocloud.com";
    final String APPLICATION = "tasks";

    final String KidoZenProvider = "Kidozen";
    final String KidoZenUser = "demo@kidozen.com";
    final String KidoZenPassword = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_kido);

        final CountDownLatch lcd = new CountDownLatch(1);

        try {
            KZApplication app = new KZApplication(TENANT,APPLICATION,false, new ServiceEventListener() {
                @Override
                public void onFinish(ServiceEvent e) {
                   if (e.StatusCode != HttpStatus.SC_OK){
                       Log.d("HelloKido Activity","**** ERROR MESSAGE: Unable to reach the kidozen server. Make sure your KidoZenAppCenterUrl and KidoZenAppName are correct");
                   }else{
                       Log.d("HelloKido Activity","KidoZen Server instance Initialized.");
                   }
                    lcd.countDown();
                }
            });

            lcd.await();
            app.Authenticate(KidoZenProvider,KidoZenUser,KidoZenPassword,new ServiceEventListener() {
                @Override
                public void onFinish(ServiceEvent e) {
                    if (e.StatusCode != HttpStatus.SC_OK){
                        Log.d("HelloKido Activity","**** ERROR MESSAGE: Unable to reach the kidozen server. Make sure your KidoZenAppCenterUrl and KidoZenAppName are correct");
                    }else{
                        Log.d("HelloKido Activity","KidoZen autentication sucessful.");
                    }

                }
            });
        }
        catch (Exception e){

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello_kido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
