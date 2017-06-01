package bwie.com.myexam;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by $USER_NAME on 2017/5/31.
 */

public class MyPageAdapter extends PagerAdapter {
    List<Bean.ResultBean.BrandsBean> list = new ArrayList<>();
    Context context;

    public MyPageAdapter(List<Bean.ResultBean.BrandsBean> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
         ImageView imageView = new ImageView(context);
        ImageLoader.getInstance().displayImage(list.get(1).getProducts().get(position%list.size()).getPic(),imageView);
        container.addView(imageView);
      imageView.getParent().requestDisallowInterceptTouchEvent(true);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
