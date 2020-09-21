package com.android.superplayer.ui.activity.hign;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.superplayer.R;
import com.android.superplayer.util.websocketutil.ExampleClient;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketTestctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket_testctivity);

        ExampleClient c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        try {
            c = new ExampleClient(new URI("wss://lab.ledcas.com/lazyman2/ws/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        c.connect();
    }
}