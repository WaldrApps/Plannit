package waldrapps.plannit.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.UUID;

import kotlin.Unit;
import waldrapps.plannit.Contact;
import waldrapps.plannit.ItemTouchHelperCallback;
import waldrapps.plannit.R;
import waldrapps.plannit.adapters.ContactAdapter;
import waldrapps.plannit.fragments.DeleteDialogFragment;
import waldrapps.plannit.fragments.GroupFragment;
import waldrapps.plannit.utils.Constants;
import waldrapps.plannit.viewmodels.ContactViewModel;
import waldrapps.plannit.viewmodels.EventViewModel;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, DeleteDialogFragment.DeleteDialogListener {

    private ContactViewModel contactViewModel;
    private ContactAdapter adapter;
    private ItemTouchHelperCallback itemTouchHelperCallback;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_input:
                Intent intentInput = new Intent(this, NewContactActivity.class);
                intentInput.putExtra(getString(R.string.uuid), UUID.randomUUID().toString());
                startActivity(intentInput);
                break;
            case R.id.nav_createGroup:
                if(adapter.getItemCount() > 1) {
                    Intent intentGroup = new Intent(this, GroupActivity.class);
                    intentGroup.putExtra(getString(R.string.uuid), UUID.randomUUID().toString());
                    startActivity(intentGroup);
                }
                else {
                    Toast.makeText(
                            this,
                            "You do not have enough contacts to create a group",
                            Toast.LENGTH_LONG
                    ).show();
                }
                break;
            case R.id.nav_about:
//        startActivity(new Intent(this, AboutScreen.class));
                ContactViewModel contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
                contactViewModel.deleteAllContacts();
                EventViewModel eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
                eventViewModel.deleteAllEvents();
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            default:
                drawerLayout.closeDrawer(GravityCompat.START);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.contact_list);
        adapter = new ContactAdapter(this, this::contactClicked, (contact, b) -> Unit.INSTANCE);
        //Populate data in the recycler
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, contacts -> {
            //Set the cached copy of contacts in the adapter
            if (contacts != null) {
                adapter.updateContacts(contacts);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Delete and edit sliders
        itemTouchHelperCallback = new ItemTouchHelperCallback(getApplicationContext(),
            (int pos) -> {
                Intent intent = new Intent(this, NewContactActivity.class);
                intent.putExtra(getString(R.string.uuid), UUID.randomUUID().toString());
                startActivity(intent);
            },
            (int pos) -> {
                DialogFragment dialog = DeleteDialogFragment.newInstance(pos);
                dialog.show(getSupportFragmentManager(), "DeleteDialogFragment");
            }
        );
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
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

    private Unit contactClicked(Contact contact) {
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra(Constants.ARG_CONTACT_ID, contact.getId());
        startActivity(intent);
        return null;
    }
}
