package com.android.superplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.superplayer.R;
import com.android.superplayer.config.LogUtil;
import com.android.superplayer.service.entity.MusicResult;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * anther: created by zuochunsheng on 2018/9/5 13 : 09
 * descript :
 */
public class MusicAdapter extends BaseAdapter {

    private List<MusicResult> oList;
    private Context context;
    private LayoutInflater inflater;


    public MusicAdapter(List<MusicResult> oList, Context context) {
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

        ViewHoulder viewHoulder = null;
        if (view == null) {

            viewHoulder = new ViewHoulder();
            view = inflater.inflate(R.layout.item_music, null);

            viewHoulder.img = (ImageView) view.findViewById(R.id.img);
            viewHoulder.name = (TextView) view.findViewById(R.id.name);
            viewHoulder.author = (TextView) view.findViewById(R.id.author);
            viewHoulder.duration = (TextView) view.findViewById(R.id.time);

            view.setTag(viewHoulder);

        } else {
            viewHoulder = (ViewHoulder) view.getTag();

        }
        MusicResult music = oList.get(position);
        viewHoulder.img.setImageResource(R.mipmap.icon_dui);
        viewHoulder.name.setText(music.getName());
        viewHoulder.author.setText(music.getAuthor());
        viewHoulder.duration.setText(getTime(music.getDuration()));


//        LogUtil.e("  --------- 单曲信息 -----------");
//        LogUtil.e("歌曲标题：" + music.getName());
//        LogUtil.e("歌曲编号：" + music.getId());//132483
//        LogUtil.e("歌曲的专辑名：" + music.getAlbum());// null
//        LogUtil.e("歌曲的专辑名id：" + music.getAlbumId());//42
//        LogUtil.e("歌曲的歌手名：" + music.getAuthor());
//        LogUtil.e("歌曲文件的路径：" + music.getPath());//   /storage/emulated/0/kgmusic/download/许嵩 - 断桥残雪.mp3
//        LogUtil.e("歌曲的总播放时长：" + getTime(music.getDuration()));
//        LogUtil.e("歌曲文件的大小：" + music.getSize()*1.0/1000 /1024 + "M");//m

        return view;
    }


    class ViewHoulder {
        ImageView img;
        TextView name;
        TextView author;
        TextView duration;

        TextView path;
        TextView id;
        TextView album;
        TextView albumId;
        TextView size;
    }


    /*
     * 将毫秒 转为
     */
    private String getTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String times = simpleDateFormat.format(time);

        return times;

    }
}
