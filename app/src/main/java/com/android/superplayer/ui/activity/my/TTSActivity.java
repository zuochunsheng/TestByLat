package com.android.superplayer.ui.activity.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.superplayer.R;
import com.android.superplayer.ui.activity.MainActivity;
import com.android.superplayer.util.SharedData;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class TTSActivity extends Activity {


    private static final String TAG = "TTSActivity";
    private TextView tv_rich;


    private TextToSpeech mSpeech = null;
    private EditText edit = null;
    private String choosedLanguage;
    private RadioButton english,chainese,german,french,taiWan,language_jp;
    private RadioGroup languageGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        initUI();
        initLanguageList();

        mSpeech = new TextToSpeech(this, new TTSListener());
    }

    private void initUI() {
        edit = findViewById(R.id.test_text);
        languageGroup=findViewById(R.id.language_Group);
        english = findViewById(R.id.language_English);
        chainese = findViewById(R.id.language_Chainese);
        language_jp = findViewById(R.id.language_jp);
        german = findViewById(R.id.language_German);
        french = findViewById(R.id.language_French);
        taiWan=findViewById(R.id.language_TaiWan);
//        View.OnClickListener handle =  new View.OnClickListener(){
//            public void onClick(View v){
//                switch(v.getId()){
//                    case R.id.language_English:
//                        choosedLanguage="英文";
//                        break;
//                    case R.id.language_Chainese:
//                        choosedLanguage="中文";
//                        break;
//                    case R.id.language_German:
//                       choosedLanguage="德语";
//                        break;
//                    case R.id.language_French:
//                       choosedLanguage="法语";
//                        break;
//                }
//
//            }
//        };
//        english.setOnClickListener(handle);
//        chainese.setOnClickListener(handle);
//        german.setOnClickListener(handle);
//        french.setOnClickListener(handle);
    }

    private void initLanguageList() {

        SharedData.languageList.put("英语",Locale.ENGLISH);
        SharedData.languageList.put("中文",Locale.CHINESE);
        SharedData.languageList.put("日文",Locale.JAPANESE);
        SharedData.languageList.put("德语",Locale.GERMAN);
        SharedData.languageList.put("法语",Locale.FRENCH);
        SharedData.languageList.put("台湾话",Locale.TAIWAN);
    }

    public void openAudioFile(View view) {
        choosedLanguage=getLanguage(languageGroup);
        int supported = mSpeech.setLanguage(SharedData.languageList.get(choosedLanguage));
        mSpeech.setSpeechRate(SharedData.voice_speed);
        mSpeech.setPitch(SharedData.voice_pitch);

        Log.i(TAG, "选择语言: "+choosedLanguage+"--"+SharedData.languageList.get(choosedLanguage));
        //mSpeech.setAudioAttributes(new AudioAttributes());
        // mSpeech.setVoice(new Voice(null,Locale.US,Voice.QUALITY_HIGH,Voice.LATENCY_NORMAL,false,null));
        if((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)){
            //语言设置失败
            Log.i(TAG, "语言设置失败: "+choosedLanguage);
        }
        else{
            Log.i(TAG, "语言设置成功: "+choosedLanguage);
        }

        String tempStr = edit.getText().toString();
        mSpeech.speak(tempStr, TextToSpeech.QUEUE_FLUSH, null);
        Log.i(TAG, "测试文本: "+tempStr);
        Log.i(TAG, "当前语速: "+SharedData.voice_speed+"， 最快语速1.5");
        Log.i(TAG, "当前音调："+SharedData.voice_pitch+"， 最高音调2.0");
        //Log.i(TAG, "test: 执行了");
    }

    //保存音频文件
    public void saveAudioFile(View view) {
        HashMap<String, String> myHashRender = new HashMap<>();
        myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, edit.getText().toString());
        mSpeech.synthesizeToFile(edit.getText().toString(), myHashRender,
                "/mnt/sdcard/"+ new Date().toString().replace(" ","_").trim()+".wav");
        Log.i(TAG, "saveAudioFile: "+"/mnt/sdcard/"+ new Date().toString().replace(" ","_").trim()+".wav"+"文件保存成功");
        Toast.makeText(this,"文件保存成功",Toast.LENGTH_SHORT).show();
    }

    private String getLanguage(RadioGroup languageGroup) {
        int choosedButtonID=languageGroup.getCheckedRadioButtonId();
        String tempStr="";
        if(choosedButtonID==english.getId()){
            tempStr="英语";
        }
        else if(choosedButtonID==chainese.getId()){
            tempStr="中文";
        }
        else if(choosedButtonID==language_jp.getId()){
            tempStr="日文";
        }
        else if(choosedButtonID==german.getId()){
            tempStr="德语";
        }
        else if(choosedButtonID==french.getId()){
            tempStr="法语";
        }
        else if(choosedButtonID==taiWan.getId()){
            tempStr="台湾话";
        }
        else{

        }
        return tempStr;
    }

    //增加音量
    public void increVoice(View view){
        if(SharedData.voice_speed>=1.5f){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("速度已经最快，无法调整");
            dialog.show();
        }
        else{
            SharedData.voice_speed+=0.1f;
        }
    }
    //减小音量
    public void decreVoice(View view){
        if(SharedData.voice_speed<=0.1f){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("速度已经最慢，无法调整");
            dialog.show();
        }
        else{
            SharedData.voice_speed-=0.1f;
        }
    }
    //升高音调
    public void increPitch(View view){
        if(SharedData.voice_pitch>=2.0f){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("音调已经最高，无法调整");
            dialog.show();
        }
        else{
            SharedData.voice_pitch+=0.1f;
        }
    }

    //减低音调
    public void decrePitch(View view){
        if(SharedData.voice_pitch<=0.1f){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("音调已经最低，无法调整");
            dialog.show();
        }
        else{
            SharedData.voice_pitch-=0.1f;
        }
    }

    private class TTSListener implements TextToSpeech.OnInitListener {
        @Override
        public void onInit(int status) {
            // TODO Auto-generated method stub
            if (status == TextToSpeech.SUCCESS) {
//                int supported = mSpeech.setLanguage(Locale.US);
//                if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
//                    Toast.makeText(MainActivity.this, "不支持当前语言！", Toast.LENGTH_SHORT).show();
//                    Log.i(TAG, "onInit: 支持当前选择语言");
//                }else{
//
//                }
                Log.i(TAG, "onInit: TTS引擎初始化成功");
            }
            else{
                Log.i(TAG, "onInit: TTS引擎初始化失败");
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
            mSpeech = null;
        }
        super.onDestroy();
    }




    private int SetLanguage(String lang) {
        int result = 0;
        if (lang.equals("CANADA")) {
            result = mSpeech.setLanguage(Locale.CANADA);
        } else if (lang.equals("CANADA_FRENCH")) {
            result = mSpeech.setLanguage(Locale.CANADA_FRENCH);
        } else if (lang.equals("CHINA")) {
            result = mSpeech.setLanguage(Locale.CHINA);
        } else if (lang.equals("CHINESE")) {
            result = mSpeech.setLanguage(Locale.CHINESE);
        } else if (lang.equals("ENGLISH")) {
            result = mSpeech.setLanguage(Locale.ENGLISH);
        } else if (lang.equals("FRANCE")) {
            result = mSpeech.setLanguage(Locale.FRANCE);
        } else if (lang.equals("FRENCH")) {
            result = mSpeech.setLanguage(Locale.FRENCH);
        } else if (lang.equals("GERMAN")) {
            result = mSpeech.setLanguage(Locale.GERMAN);
        } else if (lang.equals("GERMANY")) {
            result = mSpeech.setLanguage(Locale.GERMANY);
        } else if (lang.equals("ITALIAN")) {
            result = mSpeech.setLanguage(Locale.ITALIAN);
        } else if (lang.equals("ITALY")) {
            result = mSpeech.setLanguage(Locale.ITALY);
        } else if (lang.equals("JAPAN")) {
            result = mSpeech.setLanguage(Locale.JAPAN);
        } else if (lang.equals("JAPANESE")) {
            result = mSpeech.setLanguage(Locale.JAPANESE);
        } else if (lang.equals("KOREA")) {
            result = mSpeech.setLanguage(Locale.KOREA);
        } else if (lang.equals("KOREAN")) {
            result = mSpeech.setLanguage(Locale.KOREAN);
        } else if (lang.equals("PRC")) {
            result = mSpeech.setLanguage(Locale.PRC);
        } else if (lang.equals("ROOT")) {
            result = mSpeech.setLanguage(Locale.ROOT);
        } else if (lang.equals("SIMPLIFIED_CHINESE")) {
            result = mSpeech.setLanguage(Locale.SIMPLIFIED_CHINESE);
        } else if (lang.equals("TAIWAN")) {
            result = mSpeech.setLanguage(Locale.TAIWAN);
        } else if (lang.equals("TRADITIONAL_CHINESE")) {
            result = mSpeech.setLanguage(Locale.TRADITIONAL_CHINESE);
        } else if (lang.equals("UK")) {
            result = mSpeech.setLanguage(Locale.UK);
        } else if (lang.equals("US")) {
            result = mSpeech.setLanguage(Locale.US);
        }
        return result;
    }


}
