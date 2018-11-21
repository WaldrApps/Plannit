package waldrapps.plannit.activities;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.UUID;

import waldrapps.plannit.Contact;
import waldrapps.plannit.ItemTouchHelperCallback;
import waldrapps.plannit.R;
import waldrapps.plannit.adapters.ContactAdapter;
import waldrapps.plannit.fragments.DeleteDialogFragment;
import waldrapps.plannit.viewmodels.ContactViewModel;

public class HomeScreenActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, DeleteDialogFragment.DeleteDialogListener {

    private ContactViewModel contactViewModel;
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    private ItemTouchHelperCallback itemTouchHelperCallback;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //RecyclerView
        setupRecyclerView();
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts) {
                //Update the cached copy of contacts in the adapter
                adapter.setContacts(contacts);
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
    }

    //Create help message
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
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_input:
                Intent intent = new Intent(this, NewContactActivity.class);
                intent.putExtra("uuid", UUID.randomUUID().toString());
                startActivity(intent);
                break;
            case R.id.nav_createGroup:
//                if(contacts.size() > 1)
//                    //        Intent intent = new Intent(this, group.class);
//
////        intent.putExtra("PREFS", prefs);
//        Bundle b = new Bundle();
////        b.putSerializable("CONTACTS", contacts);
////        intent.putExtras(b);
////        startActivity(intent);
//                else
//                    Toast.makeText(this, "You do not have enough contacts to create a group", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share:
//                if(contacts.size() > 0)
//                    //        Intent intent = new Intent(this, ShareSelectScreen.class);
////        intent.putExtra("PREFS", prefs);
//        Bundle b = new Bundle();
////        b.putSerializable("CONTACTS", contacts);
////        intent.putExtras(b);
////        startActivity(intent);
//                else
//                    Toast.makeText(this, "You have no contacts to share", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_read:
//                read();
                break;
            case R.id.nav_about:
                //        Intent intent = new Intent(this, AboutScreen.class);
//        startActivity(intent);
                break;
            case R.id.action_settings:
                settings();
                break;
            default:
                drawerLayout.closeDrawer(GravityCompat.START);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.contact_list);
        adapter = new ContactAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        itemTouchHelperCallback = new ItemTouchHelperCallback(getApplicationContext(),
            (int pos) -> {
                Intent intent = new Intent(this, NewContactActivity.class);
                intent.putExtra("uuid", UUID.randomUUID().toString());
                startActivity(intent);
            },
            (int pos) -> {
                DialogFragment dialog = DeleteDialogFragment.newInstance(pos);
                dialog.show(getSupportFragmentManager(), "DeleteDialogFragment");
            }
        );
        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                itemTouchHelperCallback.onDraw(c);
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DeleteDialogFragment dialog) {
        //User touched the dialog's positive button
        contactViewModel.deleteContact(adapter.getItem(dialog.getPos()));
    }

    @Override
    public void onDialogNegativeClick(DeleteDialogFragment dialog) {
        //User touched the dialog's negative button
    }

    private void settings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
