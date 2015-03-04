package edu.asu.bscs.csiebler.waypointapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
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
public class TaskLoadWaypoint extends AsyncTask<String, Void, Void> {

    // Declare the interface
    private ExpandableListInterface iface;

    // Declare the server connection
    private WaypointServerStub server;

    // Data retrieved from the server
    private Waypoint waypoint;

    /**
     * Constructor
     *
     * @param iface
     */
    public TaskLoadWaypoint(ExpandableListInterface iface) {
        this.iface = iface;
    }

    /**
     *
     * @return
     */
    @Override
    protected Void doInBackground(String... params) {
        server = new WaypointServerStub(iface.getCurrentResources().getString(R.string.rpc_url));

        waypoint = server.get(params[0]);

        return null;
    }

    /**
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        iface.requestForm(waypoint);
    }

}
