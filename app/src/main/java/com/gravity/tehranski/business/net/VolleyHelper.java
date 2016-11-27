package com.gravity.tehranski.business.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gravity.tehranski.R;
import com.gravity.tehranski.business.model.ForeCast;
import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.util.XmlParser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * volley helper
 */
public class VolleyHelper {

    private RequestQueue requestQueue;

    private static String PROCESS_ERROR;
    private static String RETRIEVE_ERROR;

    public VolleyHelper(Context context) {

        requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();

        PROCESS_ERROR = context.getResources().getString(R.string.process_error);
        RETRIEVE_ERROR = context.getResources().getString(R.string.retrieve_error);

    }

    public void getResortInfo(final String resortName, final String height, final SkiResortListener listener) {


        if(requestQueue.getCache().get(createUrl(resortName)) == null) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, createUrl(resortName), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Document doc;
                    ArrayList<ForeCast> forecasts;
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                    try {
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        doc = builder.parse(new InputSource(new StringReader(response)));
                        forecasts = XmlParser.ParseXml(doc, height);
                        SkiResort skiResort = new SkiResort(resortName, height);
                        skiResort.setForecasts(forecasts);

                        listener.OnSuccess(skiResort);

                    } catch (ParserConfigurationException | IOException | SAXException e) {
                        listener.OnFailure(PROCESS_ERROR);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.OnFailure(RETRIEVE_ERROR);
                }
            });

            requestQueue.add(stringRequest);

        }
        else
        {
            listener.OnCached();

        }


    }

    public void CancelAll(String tag){

        requestQueue.cancelAll(createUrl(tag));
        System.out.println("canceled" + tag);
    }

    private String createUrl(String resortName) {
        return "http://www.snow-forecast.com/resorts/" + resortName + "/feed.xml";
    }

    public interface SkiResortListener {

        void OnSuccess(SkiResort skiresort);

        void OnFailure(String message);

        void OnCached();
    }
}