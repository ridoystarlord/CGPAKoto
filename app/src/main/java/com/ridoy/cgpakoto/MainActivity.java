package com.ridoy.cgpakoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ridoy.cgpakoto.Fragments.AllcoursesFragment;
import com.ridoy.cgpakoto.Fragments.BarchatFragment;
import com.ridoy.cgpakoto.Fragments.HomeFragment;
import com.ridoy.cgpakoto.Repository.GradeRepository;
import com.ridoy.cgpakoto.databinding.ActivityMainBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    GradeRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository=new GradeRepository(getApplication());

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();
        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        transaction.replace(R.id.container,new HomeFragment());
                        break;
                    case 1:
                        transaction.replace(R.id.container,new BarchatFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.container,new AllcoursesFragment());
                        break;

                }
                transaction.commit();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_top_share:
                try {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"CGPA Koto?");
                    intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(intent,"Share With"));
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to share this App\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_top_cleardata:
                repository.deleteAllsemester();
                repository.deleteAllcourse();
                finish();
                startActivity(getIntent());
                break;
            case R.id.menu_top_rate:
                Uri uri= Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());

                Intent i=new Intent(Intent.ACTION_VIEW,uri);
                try {
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to share this App\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            default:
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertdialog;
        alertdialog=new AlertDialog.Builder(MainActivity.this);
        alertdialog.setIcon(R.drawable.ic_alert);
        alertdialog.setTitle("Exit");
        alertdialog.setMessage("Do You Want to Exit");
        alertdialog.setCancelable(false);

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog=alertdialog.create();
        alertDialog.show();
    }
}