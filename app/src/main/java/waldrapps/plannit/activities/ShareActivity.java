package waldrapps.plannit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import waldrapps.plannit.R;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Share your planner");
        setContentView(R.layout.activity_share);
    }

    public void loadQR(View v) {
        Intent intent = new Intent(this, ShareDisplayActivity.class);
        intent.putExtra(ShareDisplayActivity.DISPLAY_TYPE, ShareDisplayActivity.QR);
        startActivity(intent);
    }
    public void loadLink(View v) {
        Intent intent = new Intent(this, ShareDisplayActivity.class);
        intent.putExtra(ShareDisplayActivity.DISPLAY_TYPE, ShareDisplayActivity.LINK);
        startActivity(intent);
    }
}
