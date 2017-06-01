package bwie.com.myexam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class FragmentFragment extends Fragment {

    private ViewPager fragVp;
    private TextView more;
    private GridView gv;
    List<Bean.ResultBean.IndexProductsBean> list = new ArrayList<>();
    List<Bean.ResultBean.BrandsBean> viewpage = new ArrayList<>();
    private MyBaseAdapter adapter;
    private MyPageAdapter pageAdapter;
    private LinearLayout dotlayout;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 1:
                    int count = fragVp.getCurrentItem()+1;
                    fragVp.setCurrentItem(count);
                    change();
                    break;
            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragVp = (ViewPager) view.findViewById(R.id.frag_vp);
        more = (TextView) view.findViewById(R.id.more);
        gv = (GridView) view.findViewById(R.id.gv);


        dotlayout = (LinearLayout)view.findViewById(R.id.group);

        view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Manger.class);
                getActivity().startActivity(intent);
            }
        });

        fragVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i=0;i<dotlayout.getChildCount();i++){
                    if (position==i){
                        dotlayout.getChildAt(position).setEnabled(false);

                    }else {
                        dotlayout.getChildAt(i).setEnabled(true);

                    }
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestData();

        adapter = new MyBaseAdapter(getActivity(), list);
        gv.setAdapter(adapter);
        fragVp.setFocusableInTouchMode(true);

        fragVp.setCurrentItem(10000);
        change();
    }

    private void requestData() {
        RequestParams parms = new RequestParams("http://www.babybuy100.com/API/getShopOverview.ashx");
        x.http().post(parms, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                List<Bean.ResultBean.IndexProductsBean> indexProducts = bean.getResult().getIndexProducts();
                list.addAll(indexProducts);
                adapter.notifyDataSetChanged();

                List<Bean.ResultBean.BrandsBean> brands = bean.getResult().getBrands();
                viewpage.addAll(brands);

                for (int i=0;i<11;i++){

                }
                pageAdapter = new MyPageAdapter(viewpage, getActivity());
                fragVp.setAdapter(pageAdapter);
                System.out.println(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void change(){

        handler.sendEmptyMessageDelayed(1,3000);
    }

}
