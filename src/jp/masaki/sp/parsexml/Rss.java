package jp.masaki.sp.parsexml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Rss extends Activity {
	 TextView tv1;
	 TextView tv2;
	 static List<RssItem> dataList = new ArrayList<RssItem>();
	 static RssAdapter adapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss);
 
        //-----[xmlの取得先]
        String uri = "http://spgirl.jp/feed/rss";
 
        //-----[httpクライアントの設定]
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet();
 
        try{
            get.setURI(new URI(uri));
            HttpResponse res = client.execute(get);
            InputStream in = res.getEntity().getContent();
 
            //-----[パーサーの設定]
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(in, "UTF-8");
 
            //-----[アダプターの設定]
            //ArrayAdapter adapter;
            //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
            RssAdapter adapter;
            adapter = new RssAdapter();
 
            int eventType = parser.getEventType();
 
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                switch(eventType)
                {
                    case XmlPullParser.START_TAG:
 
                        String tag = parser.getName();
                        if("title".equals(tag)){
                                String title = parser.nextText();
                                String link = null;
                                if("link".equals(tag)){
                                	link = parser.nextText();
                                }else{
                                	link = "http://";
                                }
                                //adapter.add(txt);
                                dataList.add(new RssItem(title, link));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
 
            //-----[リストビューにアダプターをセット]
            ListView lv = (ListView)findViewById(R.id.listView1);
            lv.setAdapter(adapter);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
    
    private class RssAdapter extends BaseAdapter {
    	public int getCount() {
    		// TODO Auto-generated method stub
    		return dataList.size();
    	}

    	public Object getItem(int arg0) {
    		// TODO Auto-generated method stub
    		return dataList.get(arg0);
    	}

    	public long getItemId(int arg0) {
    		// TODO Auto-generated method stub
    		return arg0;
    	}

    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		// TODO Auto-generated method stub
    		View v = arg1;
    		if(v==null){
    	        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	        v = inflater.inflate(R.layout.row, null);
    	    }
    		RssItem rss = (RssItem)getItem(arg0);
    		 if(rss != null){
    		        tv1 = (TextView) v.findViewById(R.id.textViewTitle);
    		        tv2 = (TextView) v.findViewById(R.id.textViewUrl);
    		        
    		        tv1.setText(rss.title);
    		        tv2.setText(rss.link);
    		 }
    		return v;
    	}
    	
    }
    
    public class RssItem {
    	 String title;
    	 String link;
    	 String txt;
		public RssItem(String title, String link){
    		    this.title = title;
    		    this.link = link;
    	 }
		
		 public String getTxt(){
		    return txt;
		  }
		 
    	 public String getTitle(){
    		    return title;
    		  }
    	 
    	 public String getlink(){
    		    return link;
    		  }
    }
}
