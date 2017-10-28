package me.imhere.inshorts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    ServerRequest serverRequest;
    ProgressBar progressBar;
    TextView textView;
    FavDAO favDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        serverRequest = new ServerRequest();
        favDAO        = new FavDAO(getApplicationContext());
        progressBar   = (ProgressBar)findViewById(R.id.my_progress_bar);
        textView      = (TextView)findViewById(R.id.my_textView);
        makeRequest();
    }



    void makeRequest()
    {

        serverRequest.fetchNewsInBackground(new FetchNewsCallBack()
        {
            @Override
            public void done(ArrayList<News> newses)
            {

                DataContainer.getInstance().favs    = favDAO.getAllFavs();
                DataContainer.getInstance().newses  = newses;

                Intent intent =new Intent(getApplicationContext(),MainActivity.class);

                startActivity(intent);
                finish();
            }

            @Override
            public void error(String message)
            {
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                final AlertDialog.Builder layout     = new AlertDialog.Builder(Splash.this);
                layout.setMessage(message);
                layout.setCancelable(false);
                layout.setTitle("Connection Error");
                layout.setPositiveButton("Try Again", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        makeRequest();
                    }
                });
                layout.show();
            }
        });
    }
}
