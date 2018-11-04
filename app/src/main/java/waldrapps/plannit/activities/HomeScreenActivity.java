package waldrapps.plannit.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import waldrapps.plannit.Contact;
import waldrapps.plannit.R;
import waldrapps.plannit.activities.NewContactActivity;
import waldrapps.plannit.adapters.ContactAdapter;
import waldrapps.plannit.viewmodels.ContactViewModel;

public class HomeScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ContactViewModel contactViewModel;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //RecyclerView
        setupRecyclerView();
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts) {
                //Update the cached copy of contacts in the adapter
                //TODO: Fix bug that makes you have to refresh twice for first contact
                adapter.setContacts(contacts);
            }
        });

        //Create navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //create help message
    private void alert() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("This is an app for finding free time with friends. You'll want to " +
                "start by inputting your own schedule, using the \"Input Planner\" option. All help" +
                " messages will only display once automatically, but can be brought up using the top" +
                " corner.");
        dlgAlert.setTitle("Welcome to Plannit!");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //write to never show again
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
//            Intent intent = new Intent(this, DeleteScreen.class);
            Bundle b = new Bundle();
//            b.putSerializable("CONTACTS", contacts);
//            b.putSerializable("PREFS", prefs);
//            intent.putExtras(b);
//            startActivity(intent);
            return true;
        }
//        else if(id == R.id.edit)
//        {
//            Intent intent = new Intent(this, EditScreen.class);
//            Bundle b = new Bundle();
//            b.putSerializable("CONTACTS", contacts);
//            intent.putExtras(b);
//            intent.putExtra("PREFS", prefs);
//            startActivity(intent);
//            return true;
//        }
//        else if(id==R.id.help)
//        {
//            alert();
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        switch(item.getItemId()) {
            case R.id.nav_input:
                Intent intent = new Intent(this, NewContactActivity.class);
                intent.putExtra("uuid", UUID.randomUUID().toString());
                startActivity(intent);
                break;
            case R.id.nav_createGroup:
//                if(contacts.size() > 1)
//                    createGroup();
//                else
//                    Toast.makeText(this, "You do not have enough contacts to create a group", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share:
                shareSchedule();
//                if(contacts.size() > 0)
//                    shareSelect();
//                else
//                    Toast.makeText(this, "You have no contacts to share", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_read:
                read();
                break;
            case R.id.nav_about:
                about();
                break;
            default:
                drawer.closeDrawer(GravityCompat.START);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //read contact data
    private void read()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if(permissionCheck != getPackageManager().PERMISSION_GRANTED)
        {
            // Here, this is the current activity
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                // Should we show an explanation?
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
        else
        {
//            Intent intent = new Intent(this, ReadScreen.class);
//            intent.putExtra("PREFS", prefs);
//            startActivity(intent);
        }
    }

    //request camera permission
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
//                    Intent intent = new Intent(this, ReadScreen.class);
//                    intent.putExtra("PREFS", prefs);
//                    startActivity(intent);
                }
                else
                {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //drawer run methods
    private void shareSelect()
    {
//        Intent intent = new Intent(this, ShareSelectScreen.class);
//        intent.putExtra("PREFS", prefs);
        Bundle b = new Bundle();
//        b.putSerializable("CONTACTS", contacts);
//        intent.putExtras(b);
//        startActivity(intent);
    }
    private void shareSchedule() {
        Intent intent = new Intent(this, ShareActivity.class);
        startActivity(intent);
    }

    private void createGroup()
    {
//        Intent intent = new Intent(this, group.class);

//        intent.putExtra("PREFS", prefs);
        Bundle b = new Bundle();
//        b.putSerializable("CONTACTS", contacts);
//        intent.putExtras(b);
//        startActivity(intent);
    }
    private void about()
    {
//        Intent intent = new Intent(this, AboutScreen.class);
//        startActivity(intent);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.contact_list);
        adapter = new ContactAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
