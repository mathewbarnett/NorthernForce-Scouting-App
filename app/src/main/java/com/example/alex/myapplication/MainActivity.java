package com.example.alex.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

public class MainActivity extends ActionBarActivity {

    boolean textChanged = false;
    boolean hasDoneTests = false;
    private MySQLiteHelper db;
    public static UIDatabaseInterface uiDatabaseInterface;
    boolean isStart = true;
    private Context baseContext;
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
                    unregisterReceiver(mReceiver);
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiDatabaseInterface = new UIDatabaseInterface(this.getBaseContext());



        this.baseContext = this.getBaseContext();

        this.runTests();

        setContentView(R.layout.activity_main);

      //  View test = findViewById(R.id.entry1);
      //  TextView next = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
      //  String hm = "lol";
      //  next.setText(hm.toCharArray(), 0, 3);


     /*   EditText test1 = (EditText) findViewById(R.id.comments);

        test1.setVisibility(View.INVISIBLE);


    //    TextView text = (TextView) findViewById(R.id.header);
    //    text.setTextColor(Color.parseColor("#000000"));


*/
        Button enterData = (Button) (findViewById(R.id.titleScreenEnterData));
        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, ControlledEnterDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });
        //enterData.setTextColor(Color.GREEN);

        Button viewData = (Button) (findViewById(R.id.titleScreenViewData));
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, ViewDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });
     /*   String oh = (new BlueConnect().run(this, uuid, this));
        if(!oh.equals("master")) {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
            bl = BluetoothAdapter.getDefaultAdapter();

            bl.startDiscovery();
        }
        else {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(discoverableIntent);
        }*/

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

    public UIDatabaseInterface getUiDatabaseInterface(){
        return this.uiDatabaseInterface;
    }
}
