package jerry.jerrynews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("guide",MODE_PRIVATE);
                boolean isfirst = preferences.getBoolean("isfirst",false);
                if (!isfirst) {
                    // TODO: 2017/5/22 把第二个改为跳转到 guideactivity
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    SharedPreferences.Editor editor =
                            getSharedPreferences("guide",MODE_PRIVATE).edit();
                    editor.putBoolean("isfirst",true);
                    editor.commit();
                    finish();
                }else{
                    startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
                    finish();
                }
            }
        },2000);
    }
}
