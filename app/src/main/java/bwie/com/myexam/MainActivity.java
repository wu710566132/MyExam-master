package bwie.com.myexam;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity {

    private ViewPager vp;
    private RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        if (!isNetworkAvailable()){
            Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
            startActivity(wifiSettingsIntent);
            return;
        }

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                FragmentFragment fragment=new FragmentFragment();

                return fragment;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
            }

            @Override
            public void onPageSelected(int position) {
                group.check(group.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0;i<4;i++){
                    if (group.getChildAt(i).getId()==checkedId){
                        vp.setCurrentItem(i);
                    }
                }
            }
        });

    }





    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);

        group = (RadioGroup) findViewById(R.id.group);


    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
