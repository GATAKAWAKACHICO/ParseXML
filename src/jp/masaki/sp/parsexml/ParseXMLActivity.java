package jp.masaki.sp.parsexml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ParseXMLActivity extends Activity implements OnClickListener {
	final String mUrl = "http://spgirl.jp/feed/rss";
	TextView tv1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv1 = (TextView)findViewById(R.id.textView1);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
    }

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//ArrayList<Item> list = getArray(getURLStream(mUrl));
		Intent intent = new Intent();
		intent.setClassName("jp.masaki.sp.parsexml", "jp.masaki.sp.parsexml.Rss");
		startActivity(intent);
	}
}