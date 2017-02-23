package org.revenge.video;

import java.util.Comparator;

/**
 * Created by sdecleen.
 */
public class TupleComparator implements Comparator<Tuple> {

    @Override
    public int compare(Tuple t1, Tuple t2) {
        Integer distance1 = (Integer) t1.getRhs();
        Integer distance2 = (Integer) t2.getRhs();
        return distance1 < distance2 ? -1 : distance1.equals(distance2) ? 0 : 1;
    }

}
