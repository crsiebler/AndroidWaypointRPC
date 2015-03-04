package edu.asu.bscs.csiebler.waypointapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Copyright 2015 Cory Siebler
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author Cory Siebler csiebler@asu.edu
 * @version Feb 15, 2015
 */
public class TaskLoadCategories extends AsyncTask<Void, Void, Void> {

    // Declare the interface
    private ExpandableListInterface iface;

    // Declare the server connection
    private WaypointServerStub server;

    /**
     * Constructor
     *
     * @param iface
     */
    public TaskLoadCategories(ExpandableListInterface iface) {
        this.iface = iface;
    }

    /**
     *
     * @return
     */
    @Override
    protected Void doInBackground(Void... params) {
        iface.model.clear();

        server = new WaypointServerStub(iface.getCurrentResources().getString(R.string.rpc_url));

        // Retrieve the categories from the server
        String[] categories = server.getCategoryNames();

        // Make sure the server request succeeded
        if (categories != null) {
            Log.d(getClass().getSimpleName(), "Retrieving Categories");

            for (int i = 0; i < categories.length; ++i) {
                Log.d(getClass().getSimpleName(), "Retrieving Category [" + categories[i].toString() + "]");
                Waypoint.Category category;

                try {
                    category = Waypoint.Category.valueOf(categories[i]);
                } catch (Exception ex) {
                    category = Waypoint.Category.OTHER;
                }

                // Load each Waypoint in the specific category and add it to the model
                iface.model.put(category.toString(), server.getNamesInCategory(categories[i]));
            }
        } else {
            Log.d(getClass().getSimpleName(), "ERROR: No result from server");
        }

        return null;
    }

    /**
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        iface.refreshList();
    }

}
