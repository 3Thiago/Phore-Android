package pivx.org.pivxwallet.ui.contacts_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import pivx.org.pivxwallet.contacts.Contact;
import pivx.org.pivxwallet.ui.address_add_activity.AddContactActivity;
import pivx.org.pivxwallet.ui.base.BaseDrawerActivity;
import pivx.org.pivxwallet.R;

/**
 * Created by Neoperol on 5/11/17.
 */


public class ContactsActivity extends BaseDrawerActivity {
    RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Collection<Contact> contacts;
    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
        getLayoutInflater().inflate(R.layout.fragment_address, container);
        setTitle("Contacts Book");
        recyclerView = (RecyclerView) findViewById(R.id.addressList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactsAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // check current activity in the navigation drawer
        setNavigationMenuItemChecked(1);
        // re load
        load();
    }

    private void load() {
        // add loading..
        new Thread(new Runnable() {
            @Override
            public void run() {
                contacts = pivxModule.getContacts();
                if (contacts!=null && !contacts.isEmpty())
                    adapter.changeDataSet(new ArrayList(contacts));
                // Empty view..
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add:
                Intent intent = new Intent(this, AddContactActivity.class);
                //      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actionAdd(View view) {
        Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
        startActivity(intent);
    }
}
