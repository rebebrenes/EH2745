package assignment2;

import java.util.Comparator;

public class DistanceComparator implements Comparator<distclData> {

	@Override
    public int compare(distclData a, distclData b) {
       return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
    }

}
