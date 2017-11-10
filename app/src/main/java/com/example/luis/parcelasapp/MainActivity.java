package com.example.luis.parcelasapp;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luis.parcelasapp.fragments.chartFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    ParcelasRepo studentRepo ;
    private final static String TAG= MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativelayuot_for_fragment, new ConsultaParcelasFragment()).commit();

        MenuView.ItemView search = (MenuView.ItemView) findViewById(R.id.search);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "nada", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        studentRepo = new ParcelasRepo(this);
        cursor=studentRepo.getStudentList();
        customAdapter = new CustomAdapter(MainActivity.this,  cursor, 0);
        listView = (ListView) findViewById(R.id.lstStudent);
        listView.setAdapter(customAdapter);

        //if(cursor==null) insertDummy();*
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
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);

        /*MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);*/


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(MainActivity.this,"No records found!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }
                    customAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    if (cursor!=null){
                        customAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Toast.makeText(this,"Options",Toast.LENGTH_LONG);
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
        Toast.makeText(this,"Options",Toast.LENGTH_LONG);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.nav_camera) {
            AltaParcelaFragment alta = new AltaParcelaFragment();
            manager.beginTransaction().replace(
                    R.id.relativelayuot_for_fragment,
                    alta,
                    alta.getTag()
            ).commit();
        } else if (id == R.id.nav_gallery) {
            EditarParcelaFragment editar = new EditarParcelaFragment();
            manager.beginTransaction().replace(
                    R.id.relativelayuot_for_fragment,
                    editar,
                    editar.getTag()
            ).commit();
        } else if (id == R.id.nav_slideshow) {
            ConsultaParcelasFragment consul = new ConsultaParcelasFragment();
            manager.beginTransaction().replace(
                    R.id.relativelayuot_for_fragment,
                    consul,
                    consul.getTag()
            ).commit();
        } else if (id == R.id.nav_manage) {
            BorrarParcelaFragment borrar = new BorrarParcelaFragment();
            manager.beginTransaction().replace(
                    R.id.relativelayuot_for_fragment,
                    borrar,
                    borrar.getTag()
            ).commit();
        } else if (id == R.id.graph) {
            chartFragment chart = new chartFragment();
            manager.beginTransaction().replace(
                    R.id.relativelayuot_for_fragment,
                    chart,
                    chart.getTag()
            ).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
