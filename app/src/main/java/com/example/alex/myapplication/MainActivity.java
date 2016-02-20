package com.example.alex.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import java.util.UUID;
import java.security.Security;
import android.provider.Settings.Secure;


import java.io.IOException;
import java.io.InputStream;



//CONNECTION IS WORKING, NOW NEED TO GET DATA FLOWING, CHECKING CONNECT METHODS, INPUT/OUTPUT STREAMS, ETC.
public class MainActivity extends ActionBarActivity {

    boolean textChanged = false;
    boolean hasDoneTests = false;
    private MySQLiteHelper db;
    MainActivity jo = this;
    BluetoothAdapter bl;

    UUID uuid = UUID.fromString("e720951a-a29e-4772-b32e-7c60264d5c9b");
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found

                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


           //     Log.v("Mac Address", device.getName());
                if(device.getAddress().equalsIgnoreCase("18:3b:d2:e1:88:59")) {
                    Log.v("Mac Address", device.getName() + "\n" + device.getAddress());
                    Aggro ag = new Aggro(uuid, jo, device);
                    Thread t = new Thread(ag);
                    t.start();
                    bl.cancelDiscovery();
                }
            }
        }
    };



    public static UIDatabaseInterface uiDatabaseInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String oh = (new BlueConnect().run(this, uuid, this));
        if(!oh.equals("master")) {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
            bl = BluetoothAdapter.getDefaultAdapter();
            bl.startDiscovery();
        }




        this.runTests();

        setContentView(R.layout.activity_main);

        UIDatabaseInterface uidi = new UIDatabaseInterface(this.getBaseContext());

        ListView listView = (ListView) (findViewById(R.id.list));

        DataEntryAdapter adapter = new DataEntryAdapter(this.getBaseContext(), uiDatabaseInterface.getDataEntryRows());
        listView.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("MainActivity", "Submit was pressed");
                UIDatabaseInterface.submitDataEntry(v);
            }
        });

      uidi.printDatabase(UIDatabaseInterface.teamTable);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_enterData){

        }

        if (id == R.id.action_viewData){
            Context context = this.getBaseContext();
            Intent i = new Intent(context, ViewDataActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void runTests(){
        Tests test = new Tests();
        test.testDocumentParserGetLengthReturnsCorrect();
        test.testDocumentParserGetValueReturnsCorrect();
        test.testSQLite(this.getBaseContext());
        test.testConfigParser(this.getBaseContext());
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }

    public UIDatabaseInterface getUiDatabaseInterface(){
        return this.uiDatabaseInterface;
    }

    public void setDiscoverable() {
        startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0));

    }
   // }
}
