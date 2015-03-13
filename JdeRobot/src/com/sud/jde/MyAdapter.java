package com.sud.jde;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bletemp.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends ActionBarActivity implements View.OnClickListener{

	private static final String url = "http://quiet-stone-2094.herokuapp.com/transactions.json";
	private ProgressDialog pDialog;
	private List<String> theList;
	private List<ListObject> theTransaction;
	MyListView ListAdapter1;
	JSONArray response = null;
	Button Refresh;
	HashSet<String> isPresent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longlist);
		Refresh=(Button)findViewById(R.id.brefresh);
		Refresh.setOnClickListener(this);
		isPresent=new HashSet<String>();
		new GetContacts().execute();
		theList=new ArrayList<String>();
		theTransaction=new ArrayList<ListObject>();
		ListAdapter1 = new MyListView(theList);     
        ListView temp = (ListView)findViewById(R.id.listView1);
        temp.setAdapter(ListAdapter1);
        temp.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				String chapter = ListAdapter1.getInformation(arg2);
				
				AlertDialog.Builder builderSingle = new AlertDialog.Builder(
	                    MyAdapter.this);
	            builderSingle.setIcon(R.drawable.ic_launcher);
	            builderSingle.setTitle("Transactions for "+ chapter);
	            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	                    MyAdapter.this,
	                    android.R.layout.select_dialog_singlechoice);
	            for(int i=0;i<theTransaction.size();i++)
	            	if(theTransaction.get(i).getObjectName().equals(chapter))
	            		arrayAdapter.add(theTransaction.get(i).getToString());
	            builderSingle.setNegativeButton("cancel",
	                    new DialogInterface.OnClickListener() {

	                        @Override
	                        public void onClick(DialogInterface dialog, int which) {
	                            dialog.dismiss();
	                        }
	                    });

	            builderSingle.setAdapter(arrayAdapter,
	                    new DialogInterface.OnClickListener() {

	                        @Override
	                        public void onClick(DialogInterface dialog, int which) {
	                            String str = arrayAdapter.getItem(which);
	                            AlertDialog.Builder builderInner = new AlertDialog.Builder(
	                                    MyAdapter.this);
	                            builderInner.setMessage(str);
	                            builderInner.setTitle("Your Selected Transaction is");
	                            builderInner.setPositiveButton("Ok",
	                                    new DialogInterface.OnClickListener() {

	                                        @Override
	                                        public void onClick(
	                                                DialogInterface dialog,
	                                                int which) {
	                                            dialog.dismiss();
	                                        }
	                                    });
	                            builderInner.show();
	                        }
	                    });
	            builderSingle.show();
				
			}
		});
	}

@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


public class MyListView extends BaseAdapter {

		List<String> MyList=null;
		
        public MyListView(List<String> myList) {
			super();
			MyList = myList;
		}

		public String getInformation(int arg2) {
			// TODO Auto-generated method stub
			return MyList.get(arg2);
		}

		@Override
        public int getCount() {
            // TODO Auto-generated method stub
            return MyList.size();
        }

		@Override
        public String getItem(int arg0) {
            // TODO Auto-generated method stub
            return MyList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
        	if(arg1==null)
	        {
	        	
	        	LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(arg2.getContext());
	            arg1 = inflater.inflate(R.layout.listitem, arg2,false);
	        }
        	TextView prod = (TextView)arg1.findViewById(R.id.tvsku);
	        //TextView price = (TextView)arg1.findViewById(R.id.tvprice);
	        //TextView curr = (TextView)arg1.findViewById(R.id.tvcurrency);
 	        String chapter = MyList.get(arg0);
 	        prod.setText(chapter);
	        /*price.setText(chapter.getObjectPrice()+"");
	        curr.setText(chapter.getObjectCurrency());//*/
        	return arg1;
        }

    }
private class GetContacts extends AsyncTask<Void, Void, Void> {

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Showing progress dialog
		pDialog = new ProgressDialog(MyAdapter.this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(true);
		pDialog.show();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// Creating service handler class instance
		ServiceHandler sh = new ServiceHandler();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

		Log.d("Response: ", "> " + jsonStr);

		if (jsonStr != null) {
			try {
				for (int i = 0; i < response.length(); i++) {
					JSONObject obj = response.getJSONObject(i);
					ListObject objl = new ListObject();
					objl.setObjectName(obj.getString("sku"));
					objl.setObjectPrice(obj.getDouble("amount"));
					objl.setObjectCurrency(obj.getString("currency"));
					if(isPresent.contains(objl.getObjectName())==false)
					{
						theList.add(objl.getObjectName());
						isPresent.add(objl.getObjectName());
					}
					theTransaction.add(objl);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		// Dismiss the progress dialog
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	ListAdapter1.notifyDataSetChanged();
	if(ListAdapter1.getCount()==0)
		Toast.makeText(getApplicationContext(), "Please wait a little bit.",Toast.LENGTH_SHORT).show();
}
}