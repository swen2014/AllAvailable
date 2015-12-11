package com.cmu.smartphone.allavailable.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.exception.IPException;
import com.cmu.smartphone.allavailable.ws.remote.SessionControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This class represents the IP page to accept user's input
 *
 * @author Xi Wang
 * @version 1.0
 */
public class IPActivity extends AppCompatActivity {

    private static final String IPV4_FORMAT = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String IPV6_FORMAT = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    private static Pattern VALID_IPV4_PATTERN = null;
    private static Pattern VALID_IPV6_PATTERN = null;

    static {
        try {
            VALID_IPV4_PATTERN = Pattern.compile(IPV4_FORMAT, Pattern.CASE_INSENSITIVE);
            VALID_IPV6_PATTERN = Pattern.compile(IPV6_FORMAT, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
    }

    private EditText ipField;
    private Button submit;

    /**
     * Override the default onCreate function
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        ipField = (EditText) findViewById(R.id.ip_field);
        submit = (Button) findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultIp = verifyIP(ipField.getText().toString());
                if (!resultIp.equals("Invalid")) {
                    resultIp = "http://" + resultIp + ":8080/AllAvailableServer/";
                    SessionControl session = SessionControl.getInstance();
                    session.createIpSession(IPActivity.this, resultIp);

                    Intent intent = new Intent(IPActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        throw new IPException();
                    } catch (IPException ipe) {
                        ipe.fix(IPActivity.this);
                    }
                }
            }
        });
    }

    /**
     * Verify the correctness
     *
     * @param ip
     * @return
     */
    public String verifyIP(String ip) {
        Matcher match1 = VALID_IPV4_PATTERN.matcher(ip);
        if (match1.matches()) {
            return ip;
        }
        Matcher match2 = VALID_IPV6_PATTERN.matcher(ip);
        if (match2.matches()) {
            return "[" + ip + "]";
        }

        return "Invalid";
    }
}
