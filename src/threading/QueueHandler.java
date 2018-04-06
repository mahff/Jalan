package threading;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import route.Route;

public class QueueHandler
{
    //This Queue class is a thread safe (written in house) class
    public static SynchronousQueue<ArrayList<Route>> readQ = new SynchronousQueue<ArrayList<Route>>();

    public static void enqueue(ArrayList<Route> route)
    {
        readQ.add(route);
    }

    public static ArrayList<Route> dequeue()
    {
        return readQ.poll();
    }
}