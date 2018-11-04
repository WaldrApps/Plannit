package waldrapps.plannit.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import waldrapps.plannit.R;

public class ShareDisplayActivity extends AppCompatActivity {

    public final static String DISPLAY_TYPE = "DISPLAY_TYPE";
    public final static int ERROR = 0;
    public final static int QR = 1;
    public final static int LINK = 2;

    private String linkText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_display);
        int display = getIntent().getIntExtra(DISPLAY_TYPE, 0);
        setTitle("Share your planner");
        ImageView qrDisplay = findViewById(R.id.qrDisplay);
        TextView linkDisplay = findViewById(R.id.linkDisplay);
        switch (display) {
            case QR:
                qrDisplay.setVisibility(View.VISIBLE);
                linkDisplay.setVisibility(View.GONE);
                qrDisplay.setImageDrawable(getDrawable(R.drawable.ic_qrcode));
                break;
            case LINK:
                linkDisplay.setVisibility(View.VISIBLE);
                qrDisplay.setVisibility(View.GONE);
                findViewById(R.id.copy).setVisibility(View.VISIBLE);
                linkText = "Waldrapps.com/sampleLink";
                linkDisplay.setText(linkText);

                break;

            case ERROR:
            default:
                linkDisplay.setVisibility(View.VISIBLE);
                qrDisplay.setVisibility(View.GONE);
                linkDisplay.setText(getString(R.string.share_error));

        }
    }


    public void copyToClipBoard(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.link_label), linkText);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, getString(R.string.clipboard), Toast.LENGTH_SHORT);
    }
}
