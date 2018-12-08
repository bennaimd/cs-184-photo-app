package edu.ucsb.cs.cs184.filtertest2;

import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;

public class CustomFilters {


    private CustomFilters(){}

    public static Filter getNightVisionFilter(){
        Point[] rgbKnots = new Point[4];

        rgbKnots[0] = new Point(0,10);
        rgbKnots[1] = new Point(152,135);
        rgbKnots[2] = new Point(206,170);
        rgbKnots[3] = new Point(255,235);

        Point[] greenKnots = new Point[5];

        greenKnots[0] = new Point(0,5);
        greenKnots[1] = new Point(55,122);
        greenKnots[2] = new Point(166,170);
        greenKnots[3] = new Point(226,213);
        greenKnots[4] = new Point(239,255);



        Filter filter = new Filter();
        filter.addSubFilter(new ContrastSubFilter(1.2f));
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots,null,greenKnots,null));
        filter.addSubFilter(new SaturationSubFilter(.72f));
        filter.addSubFilter(new BrightnessSubFilter(8));


        return filter;
    }

}
