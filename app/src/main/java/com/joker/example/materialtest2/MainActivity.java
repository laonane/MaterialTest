package com.joker.example.materialtest2;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFloatingActionButton;

    private Fruit[] fruits = {
            new Fruit("Apple", R.drawable.apple),
            new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange),
            new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear),
            new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple),
            new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry),
            new Fruit("Mango", R.drawable.mango)
    };

    private List<Fruit> fruitList =  new ArrayList<>();
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar设置
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //DrawerLayout设置
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mNavigationView设置
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        //mFloatingActionButton设置
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        }
        mNavigationView.setCheckedItem(R.id.nav_personal_info);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "You click the FloatingActionButton!", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Snackbar is clicked!",Snackbar.LENGTH_SHORT)
                        .setAction("Cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "The Snackbar is cancel!", Toast.LENGTH_SHORT).show();
    }
}).show();
        }
        });

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    /**
     * 刷新水果列表
     */
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        //隐藏刷新动作
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /**
     * 初始化界面item
     */
    public void initFruits(){
        fruitList.clear();
        for (int i=0; i < 30; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    //加载菜单按钮 inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //menu点击事件处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(getApplicationContext(), "You click the Backup button!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(getApplicationContext(), "You click the Delete button!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "You click the Settings button!", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "You click the Home button!", Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
                default:
        }
        return true;
    }
}
