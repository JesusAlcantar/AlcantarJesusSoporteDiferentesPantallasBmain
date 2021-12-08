package net.ivanvega.soportediferentespantallasb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.SharedValues;
import androidx.core.app.ShareCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.content.pm.SharedLibraryInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        if(findViewById(R.id.contenedor_pequeno) != null &&
                getSupportFragmentManager().findFragmentById(R.id.contenedor_pequeno) ==null
           ) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.contenedor_pequeno,
                            SelectorFragment.class, null)
                    .commit();
        }
//barra de navegacion
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//comentario

                if (item.getItemId()==R.id.nav_todos){
                    Toast.makeText(MainActivity.this, "Inicio", Toast.LENGTH_SHORT).show();
                   

                }else  if (item.getItemId()==R.id.nav_poema_epico){
                    Toast.makeText(MainActivity.this, "Poemas epicos", Toast.LENGTH_SHORT).show();
                }else  if (item.getItemId()==R.id.nav_XIX){
                    Toast.makeText(MainActivity.this, "Literatura siglo XIX", Toast.LENGTH_SHORT).show();
                }else  if (item.getItemId()==R.id.nav_Suspenso){
                    Toast.makeText(MainActivity.this, "Suspenso woooo", Toast.LENGTH_SHORT).show();


                }else  if (item.getItemId()==R.id.nav_visitado){
                    irUltimoVisitado();
                }else  if (item.getItemId()==R.id.nav_Compartir){
                    Toast.makeText(MainActivity.this, "Seccion en trabajo ", Toast.LENGTH_SHORT).show();
                }
DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        

       //boton flotante
       FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view,"Remplasar Accion",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Snackbar.make(view,"Ir al ultimo Visitado??", Snackbar.LENGTH_LONG).setAction("Si", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        irUltimoVisitado();
                    }
                }).show();
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==R.id.menu_preferencias){
            Toast.makeText(this, "Preferencias", Toast.LENGTH_LONG).show();
            return true;
        }else if (id==R.id.menu_acerca){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Mensaje de Acerca De");
            builder.setPositiveButton(android.R.string.ok, null);
            return true;
        }else if (id==R.id.menu_ultimo){
            irUltimoVisitado();
            return true;
        }else if (id==R.id.menu_buscar){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void irUltimoVisitado() {
        SharedPreferences pref = getSharedPreferences("com.example.audilibros_internal", MODE_PRIVATE);
        int id = pref.getInt("ultimo",-1);
        if (id >= 0){
            mostrarDetalle(id);
        }else{
            Toast.makeText(this, "Sin Ultimas Vistas", Toast.LENGTH_LONG).show();
        }
    }



    public void mostrarDetalle(int posLibroSelectd) {
        DetalleFragment detalleFragment;

        detalleFragment =(DetalleFragment)
                getSupportFragmentManager().findFragmentById(R.id.detalle_fragment);

        if (detalleFragment!=null){
            detalleFragment.setInfoLibro(posLibroSelectd)   ;
        }else{

            detalleFragment = new DetalleFragment();

            Bundle param = new Bundle();
            param.putInt(DetalleFragment.ARG_LIBRO_POS, posLibroSelectd );

            detalleFragment.setArguments(param);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.contenedor_pequeno, detalleFragment, null )
                    .addToBackStack(null)
                    .commit();
        }
        SharedPreferences pref = getSharedPreferences("com.example.audilibros_internal", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ultimo", posLibroSelectd);
        editor.commit();
    }
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return false;
//    }
}