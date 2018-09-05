package com.android.superplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.service.entity.MusicResult;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * anther: created by zuochunsheng on 2018/9/5 13 : 09
 * descript :
 */
public class MusicAdapter extends BaseAdapter {

    private List<MusicResult> oList ;
    private Context context;
    private LayoutInflater inflater;


    public MusicAdapter(List<MusicResult> oList,Context context) {
        this.oList = oList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return oList.size();
    }

    @Override
    public Object getItem(int position) {
        return oList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHoulder viewHoulder =  null;
        if(view == null){

            viewHoulder  = new ViewHoulder();
            view = inflater.inflate(R.layout.item_music,null);

            viewHoulder.img = (ImageView) view.findViewById(R.id.img) ;
            viewHoulder.name = (TextView) view.findViewById(R.id.name) ;
            viewHoulder.author = (TextView) view.findViewById(R.id.author) ;
            viewHoulder.duration = (TextView) view.findViewById(R.id.time) ;

            view.setTag(viewHoulder);

        }
        else {
            viewHoulder = (ViewHoulder) view.getTag();

        }
        MusicResult music = oList.get(position);
        viewHoulder.img.setImageResource(R.mipmap.icon_dui);
        viewHoulder.name.setText(music.getName());
        viewHoulder.author.setText(music.getAuthor());
        viewHoulder.duration.setText(getTime(music.getDuration()));


        return view;
    }


    class  ViewHoulder{
        ImageView img;
        TextView name;
        TextView author;
        TextView duration;

    }



    /*
     * 将毫秒 转为
     */
    private String getTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String times = simpleDateFormat.format(time);

        return  times;

    }
}
