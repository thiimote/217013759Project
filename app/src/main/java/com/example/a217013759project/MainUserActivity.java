package com.example.a217013759project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainUserActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private ListView mylistView;
    int[] IMAGES = {R.drawable.arm,R.drawable.copycat,R.drawable.download,R.drawable.fish,
            R.drawable.foodpiza,R.drawable.gluten,R.drawable.jargon,R.drawable.timeout};
    String[] NAMES = {"Arm" , "Copy cat" ,"best", "fish" ,"piza" ,"gluten" ,"jargon" ,"time"};
    String[] DESCRIPTION = {"available" ,"only wednesday" ,"free" ,"not available",
    "fast food","free to get it" , "low prices" , "weekend only"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mylistView = (ListView)findViewById(R.id.listview);
        customAdapter customAdapter = new customAdapter();
        mylistView.setAdapter(customAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null)
        {
            Intent intent = new Intent(MainUserActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_option)
        {
            mAuth.signOut();
            Intent intent = new Intent(MainUserActivity.this,MainActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.main_settings_option)
        {
            Toast.makeText(this, "No Settings Available", Toast.LENGTH_SHORT).show();
        }
      return true;
    }
    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {

            view = getLayoutInflater().inflate(R.layout.listview,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView_name = (TextView)view.findViewById(R.id.textView_name);
            TextView textView_description = (TextView)view.findViewById(R.id.textView_description);
            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_description.setText(DESCRIPTION[i]);
            return view;
        }
    }
}

