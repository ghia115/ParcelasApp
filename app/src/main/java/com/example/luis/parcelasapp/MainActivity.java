package com.example.luis.parcelasapp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.luis.parcelasapp.fragments.AltaParcelaFragment;
import com.example.luis.parcelasapp.fragments.ConsultaParcelasFragment;
import com.example.luis.parcelasapp.fragments.EditarParcelaFragment;
import com.example.luis.parcelasapp.fragments.MapFragment;
import com.example.luis.parcelasapp.fragments.chartFragment;
import com.example.luis.parcelasapp.modelo.MresumenRiego;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(toolbar);

        ArrayList<String> lista = (ArrayList<String>) getIntent().getSerializableExtra("datosRiego");

        /*ConsultaParcelasFragment data = new ConsultaParcelasFragment();
        data.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(
                R.id.relativelayuot_for_fragment, data).commit();

        Toolbar toolbarActionBar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(toolbarActionBar);*/

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.tab_consultar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_consultar:
                        MapFragment mapFragment = new MapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayuot_for_fragment, mapFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_parcelas:
                        AltaParcelaFragment altaParcelaFragment = new AltaParcelaFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayuot_for_fragment, altaParcelaFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_calendarizacion:
                        ConsultaParcelasFragment consultaParcelasFragment = new ConsultaParcelasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayuot_for_fragment, consultaParcelasFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_riego:
                        chartFragment chart = new chartFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayuot_for_fragment, chart)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativelayuot_for_fragment, new ConsultaParcelasFragment()).commit();*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
        if (id == R.id.action_add) {

            // Do something
            return true;
        }
        if (id == R.id.action_edit) {

            // Do something
            return true;
        }
        if (id == R.id.action_del) {

            // Do something
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
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
    }*/

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
            MapFragment borrar = new MapFragment();
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
