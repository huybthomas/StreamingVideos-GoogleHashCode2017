package org.revenge.video;

import java.util.Comparator;

/**
 * Created by sdecleen.
 */
public class TupleComparator implements Comparator<Tuple> {

    @Override
    public int compare(Tuple t1, Tuple t2) {
        Double distance1 = (Double) t1.getRhs();
        Double distance2 = (Double) t2.getRhs();
        return distance1 < distance2 ? -1 : distance1.equals(distance2) ? 0 : 1;
    }

}
