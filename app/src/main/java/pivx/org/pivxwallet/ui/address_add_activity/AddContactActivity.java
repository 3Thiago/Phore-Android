package pivx.org.pivxwallet.ui.address_add_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.bitcoinj.core.Address;

import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.contacts.Contact;
import pivx.org.pivxwallet.ui.base.BaseActivity;

/**
 * Created by Neoperol on 6/8/17.
 */

public class AddContactActivity extends BaseActivity {

    public static final String ADDRESS_TO_ADD = "address";

    private View root;
    private EditText edit_name;
    private EditText edit_address;

    private String address;
    private String name;

    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
        root = getLayoutInflater().inflate(R.layout.fragment_new_address, container);
        setTitle("New Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name = (EditText) root.findViewById(R.id.edit_name);
        edit_address = (EditText) root.findViewById(R.id.edit_address);
        edit_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                if(pivxModule.chechAddress(temp)){
                    address = temp;
                    edit_address.setTextColor(Color.GREEN);
                }else {
                    edit_address.setTextColor(Color.RED);
                }
            }
        });
        Intent intent = getIntent();
        if (intent!=null){
            if (intent.hasExtra(ADDRESS_TO_ADD)){
                edit_address.setText(intent.getStringExtra(ADDRESS_TO_ADD));
            }
        }
    }

    protected void onNavigationBackPressed() {
        // save contact
        name = edit_name.getText().toString();
        if (name.length()>0 && address.length()>0) {
            Contact contact = new Contact(name);
            contact.addAddress(address);
            pivxModule.saveContact(contact);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_LONG).show();
        }
    }
}
