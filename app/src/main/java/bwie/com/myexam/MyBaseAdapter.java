package bwie.com.myexam;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by $USER_NAME on 2017/5/31.
 */

public class MyBaseAdapter extends BaseAdapter {
    Context context;
    List<Bean.ResultBean.IndexProductsBean>list;

    public MyBaseAdapter(Context context, List<Bean.ResultBean.IndexProductsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(context,R.layout.item,null);
            viewHolder=new ViewHolder();
            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
            viewHolder.pic= (ImageView) convertView.findViewById(R.id.item_iv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getName());
        ImageLoader.getInstance().displayImage(list.get(position).getPic(),viewHolder.pic);
        return convertView;
    }


    class ViewHolder{

        TextView name;
        ImageView pic;
    }
}
