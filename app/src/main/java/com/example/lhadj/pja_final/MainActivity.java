package com.example.lhadj.pja_final;

import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lhadj.pja_final.RecyclerViewFiles.FileRecyclerViewAdapter;
import com.example.lhadj.pja_final.RecyclerViewNav.RecyclerItemClickListener;
import com.example.lhadj.pja_final.RecyclerViewNav.RecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerViewFile;
    FileRecyclerViewAdapter recyclerViewAdapterFile;

    private final int PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 100;
    private final int PERMISSIONS_REQUEST_EXTERNAL_STORAGEW = 101;
    public static ArrayList<RequestDetail> requestDetails ;
    public static WifiManager wm;
    public static String directory;
    public static ListFile fileToDow;
    int currentSelectedClient;
    public static int clientPosition ;
    public static ArrayList<String> fileDownloadQue;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //  Android access permissison


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_EXTERNAL_STORAGEW);
        }


        //creating the file que object
        fileDownloadQue = new ArrayList<String>();

        wm = (WifiManager) getSystemService(WIFI_SERVICE);
        directory=Environment.getExternalStorageDirectory()+File.separator+"PJA_Final";
        File file = new File(directory);
        //creating a shared directory of the application
        if(!file.isDirectory()){
            file.mkdirs();
        }

        requestDetails =new ArrayList<RequestDetail>();
        //adding the current list files availebalin the phone
        requestDetails.add(new GetListFiles().getFiles());

        currentSelectedClient = 0;


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(requestDetails);
        recyclerView.setAdapter(recyclerViewAdapter);


        recyclerViewFile = (RecyclerView)findViewById(R.id.FilesRecyclerView);
        recyclerViewFile.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapterFile = new FileRecyclerViewAdapter(requestDetails.get(0).getFiles());
        recyclerViewFile.setAdapter(recyclerViewAdapterFile);


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                clientPosition = position ;
                recyclerViewAdapterFile.loadNewData(requestDetails.get(clientPosition).getFiles());
                drawer.closeDrawers();
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));


        recyclerViewFile.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));




        Handler hn = new Handler();


        /*Start the proces of listening queries */

        ListeningTCP listeningTCP = new ListeningTCP();
        listeningTCP.start();



        /*Start the process of listening the files receiving*/

        ListeningUDP listeningUDP = new ListeningUDP();
        listeningUDP.start();



        /*Starting the file refreshing every 4 second process */

        ProcessingUDPFiles processingUDPFiles = new ProcessingUDPFiles(recyclerViewAdapter,recyclerViewAdapterFile,hn);
        processingUDPFiles.start();

        /* Start the process of requesting the list of files */







    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
