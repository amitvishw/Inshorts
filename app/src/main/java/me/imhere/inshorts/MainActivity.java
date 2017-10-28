package me.imhere.inshorts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<News> newses;
    ArrayList<Integer> favs;
    AndroidListAdapter androidListAdapter;
    FavDAO favDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView    = (ListView) findViewById(R.id.listView);
        newses      = DataContainer.getInstance().newses;
        favDAO      = new FavDAO(getApplicationContext());
        favs        = DataContainer.getInstance().favs;

        listView.setFastScrollEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                showDialogWhenClicked(i);
            }
        });

        androidListAdapter = new AndroidListAdapter(getApplicationContext(),newses,favs);
        listView.setAdapter(androidListAdapter);

    }



    void showDialogWhenClicked(final int i)
    {
        AlertDialog.Builder layout  = new AlertDialog.Builder(MainActivity.this);
        layout.setTitle("Open News");
        layout.setPositiveButton("Show Full News", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent       =new Intent(getApplicationContext(),WebPage.class);
                intent.putExtra("url",newses.get(i).getUrl());
                startActivity(intent);
            }
        });
        layout.setNegativeButton("Mark It as Favorite", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                favDAO.insertFav(newses.get(i).getId());
                DataContainer.getInstance().favs.clear();
                DataContainer.getInstance().favs.addAll(favDAO.getAllFavs());
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        androidListAdapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        listView.refreshDrawableState();
                    }
                });
            }
        });
        layout.show();
    }
}
