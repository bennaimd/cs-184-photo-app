<<<<<<< Updated upstream
package edu.cs.cs184.photoapp;

import android.content.Context;
import android.util.Pair;

import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomFilters {

    // TODO: Add all of your filters, translate data on the googledoc into the corresponding functions, and replace the placeholder code.
    // TODO: come up with goofy ass names like the ones from sample filters
    // TODO: if someone wants to create an object that wraps a filter and its name and refactor, feel free

    public static Filter getNightVisionFilter(){
        /**
         * night vision filter: should look good for night,darkness,evening,dusk,etc
         */
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

    public static Filter getPolaroidFilter(){
        /**
         * polaroid filter: should look good for most objects,people,landscapes,buildings,misc
         */

        Point[] rgbKnots = new Point[3];
        rgbKnots[0] = new Point(0,10);
        rgbKnots[1] = new Point(129,147);
        rgbKnots[2] = new Point(255,230);

        Point[] greenKnots = new Point[2];
        greenKnots[0] = new Point(30,0);
        greenKnots[1] = new Point(255,255);

        Point[] rgbKnots2 = new Point[3];
        rgbKnots2[0] = new Point(0,6);
        rgbKnots2[1] = new Point(140,141);
        rgbKnots2[2] = new Point(213,255);

        Point[] yellowKnots = new Point[3];
        yellowKnots[0] = new Point(0,0);
        yellowKnots[1] = new Point(126,131);
        yellowKnots[2] = new Point(230,255);

        Point[] blueKnots = new Point[4];
        blueKnots[0] = new Point(0,0);
        blueKnots[1] = new Point(171,173);
        blueKnots[2] = new Point(219,210);
        blueKnots[3] = new Point(255,255);

        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots,null,greenKnots,null));
        filter.addSubFilter(new BrightnessSubFilter(20));
        filter.addSubFilter(new SaturationSubFilter(.78f));
        filter.addSubFilter(new ContrastSubFilter(.73f));
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots2,yellowKnots, yellowKnots, blueKnots ));

        return filter;
    }

    public static Filter getBwFilter(){
        /**
         * bwfilter: should look good for landscapes, misc features (whatever doesn't have enough filters..)
         */

        Filter filter = new Filter();
        filter.addSubFilter(new BrightnessSubFilter(20));
        filter.addSubFilter(new ContrastSubFilter(1.3f));
        filter.addSubFilter(new SaturationSubFilter(0f));

        return filter;

    }


    public static Filter getSepiaFilter(Context context){

        /**
         * sepiafilter: anything antique, landscapes, objects, buildings, cars, trains
         */

        Point[] rgbKnots = new Point[2];

        rgbKnots[0] = new Point(0,4);
        rgbKnots[1] = new Point(240,255);

        Point[] greenKnots = new Point[2];
        greenKnots[0] = new Point(0,8);
        greenKnots[1] = new Point(243,255);

        Point[] redKnots = new Point[3];
        redKnots[0] = new Point(0,27);
        redKnots[1] = new Point(126,163);
        redKnots[2] = new Point(233,255);

        Point[] blueKnots = new Point[3];
        blueKnots[0] = new Point(4,0);
        blueKnots[1] = new Point(129,116);
        blueKnots[2] = new Point(255,238);

        Filter filter = getBwFilter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots,redKnots,greenKnots,blueKnots));
        filter.addSubFilter(new SaturationSubFilter(1.3f));
        filter.addSubFilter(new VignetteSubFilter(context, 70));
        filter.addSubFilter(new BrightnessSubFilter(10));
        return  filter;

    }


    /* * * * * * * * * * * * *
     * All filters starlit and below are taken from the sample filters in zomato photofilters.
     * * * * * * * * * * * * */
    public static Filter getStarLitFilter() {
        Point[] rgbKnots;
        rgbKnots = new Point[8];
        rgbKnots[0] = new Point(0, 0);
        rgbKnots[1] = new Point(34, 6);
        rgbKnots[2] = new Point(69, 23);
        rgbKnots[3] = new Point(100, 58);
        rgbKnots[4] = new Point(150, 154);
        rgbKnots[5] = new Point(176, 196);
        rgbKnots[6] = new Point(207, 233);
        rgbKnots[7] = new Point(255, 255);
        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, null, null, null));
        return filter;
    }

    public static Filter getBlueMessFilter() {
        Point[] redKnots;
        redKnots = new Point[8];
        redKnots[0] = new Point(0, 0);
        redKnots[1] = new Point(86, 34);
        redKnots[2] = new Point(117, 41);
        redKnots[3] = new Point(146, 80);
        redKnots[4] = new Point(170, 151);
        redKnots[5] = new Point(200, 214);
        redKnots[6] = new Point(225, 242);
        redKnots[7] = new Point(255, 255);
        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(null, redKnots, null, null));
        filter.addSubFilter(new BrightnessSubFilter(30));
        filter.addSubFilter(new ContrastSubFilter(1f));
        return filter;
    }

    public static Filter getAweStruckVibeFilter() {
        Point[] rgbKnots;
        Point[] redKnots;
        Point[] greenKnots;
        Point[] blueKnots;

        rgbKnots = new Point[5];
        rgbKnots[0] = new Point(0, 0);
        rgbKnots[1] = new Point(80, 43);
        rgbKnots[2] = new Point(149, 102);
        rgbKnots[3] = new Point(201, 173);
        rgbKnots[4] = new Point(255, 255);

        redKnots = new Point[5];
        redKnots[0] = new Point(0, 0);
        redKnots[1] = new Point(125, 147);
        redKnots[2] = new Point(177, 199);
        redKnots[3] = new Point(213, 228);
        redKnots[4] = new Point(255, 255);


        greenKnots = new Point[6];
        greenKnots[0] = new Point(0, 0);
        greenKnots[1] = new Point(57, 76);
        greenKnots[2] = new Point(103, 130);
        greenKnots[3] = new Point(167, 192);
        greenKnots[4] = new Point(211, 229);
        greenKnots[5] = new Point(255, 255);


        blueKnots = new Point[7];
        blueKnots[0] = new Point(0, 0);
        blueKnots[1] = new Point(38, 62);
        blueKnots[2] = new Point(75, 112);
        blueKnots[3] = new Point(116, 158);
        blueKnots[4] = new Point(171, 204);
        blueKnots[5] = new Point(212, 233);
        blueKnots[6] = new Point(255, 255);

        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots));
        return filter;
    }

    public static Filter getLimeStutterFilter() {
        Point[] blueKnots;
        blueKnots = new Point[3];
        blueKnots[0] = new Point(0, 0);
        blueKnots[1] = new Point(165, 114);
        blueKnots[2] = new Point(255, 255);
        // Check whether output is null or not.
        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(null, null, null, blueKnots));
        return filter;
    }

    public static Filter getNightWhisperFilter() {
        Point[] rgbKnots;
        Point[] redKnots;
        Point[] greenKnots;
        Point[] blueKnots;

        rgbKnots = new Point[3];
        rgbKnots[0] = new Point(0, 0);
        rgbKnots[1] = new Point(174, 109);
        rgbKnots[2] = new Point(255, 255);

        redKnots = new Point[4];
        redKnots[0] = new Point(0, 0);
        redKnots[1] = new Point(70, 114);
        redKnots[2] = new Point(157, 145);
        redKnots[3] = new Point(255, 255);

        greenKnots = new Point[3];
        greenKnots[0] = new Point(0, 0);
        greenKnots[1] = new Point(109, 138);
        greenKnots[2] = new Point(255, 255);

        blueKnots = new Point[3];
        blueKnots[0] = new Point(0, 0);
        blueKnots[1] = new Point(113, 152);
        blueKnots[2] = new Point(255, 255);

        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots));
        return filter;
    }

    // TODO: add your filters to this array in alphabetical order.
    public static ArrayList<Filter> getAllFilters(Context context){
        return new ArrayList<>( Arrays.asList( new Filter[]{getAweStruckVibeFilter(),getBlueMessFilter(),getBwFilter(),getLimeStutterFilter(),getNightVisionFilter(),getNightWhisperFilter(),getPolaroidFilter(),getSepiaFilter(context),getStarLitFilter(),new Filter()}));
    }

    // TODO: add your filters' names to this array in alphabetical order.
    public static ArrayList<String> getAllNames(){
        return new ArrayList<>( Arrays.asList(new String[]{"Awestruck Vibe","Blue Mess","Graphite","Lime Stutter","Night Vision","Night Whisper","Polaroid","Sepia","Star Lit","No Filter"}));
    }

    // TODO: generate the lists of features corresponding to each filter (copypaste from the googledoc)
    // TODO: add corresponding filters/names in order of the average of the percent certainties of the features corresponding to each filter
    // TODO: add remaining filters/names in arbitrary (probably alphabetic) order, but with blank last.
    public static ArrayList<Pair<Filter,String>> getFiltersInOrder(ArrayList<Double> percentages, ArrayList<String> features,Context context){
        // Invariant: percentages and features have the same length.
        // Invariant: All names and All filters have the same length.
        // Both lists should always have a blank filter at the end.

        // placeholder code so that all the filters show up as tabs
        ArrayList<Pair<Filter,String>> result = new ArrayList<>();
        ArrayList<Filter> filters = getAllFilters(context);
        ArrayList<String> names = getAllNames();
        for(int i=0; i<filters.size();i++)
            result.add(new Pair<>(filters.get(i),names.get(i)));


        return result;
    }

    // Done. Does what the name suggests.
    public static Map<String,Filter> getFilterMap(Context context){
        ArrayList<Filter> filters = getAllFilters(context);
        ArrayList<String> names = getAllNames();
        HashMap<String,Filter> result = new HashMap<>();

        for(int i=0; i<names.size(); i++) result.put(names.get(i),filters.get(i));

        return result;
    }

    // Done. Does what the name suggests.
    public static Filter getFilter(String name, Context context){
        return getFilterMap(context).get(name);
    }

}


=======
package edu.cs.cs184.photoapp;

import android.content.Context;
import android.util.Pair;

import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomFilters {

    // TODO: Add all of your filters, translate data on the googledoc into the corresponding functions, and replace the placeholder code.
    // TODO: come up with goofy ass names like the ones from sample filters
    // TODO: if someone wants to create an object that wraps a filter and its name and refactor, feel free

    public static Filter getNightVisionFilter(){
        /**
         * night vision filter: should look good for night,darkness,evening,dusk,etc
         */
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

    public static Filter getPolaroidFilter(){
        /**
         * polaroid filter: should look good for most objects,people,landscapes,buildings,misc
         */

        Point[] rgbKnots = new Point[3];
        rgbKnots[0] = new Point(0,10);
        rgbKnots[1] = new Point(129,147);
        rgbKnots[2] = new Point(255,230);

        Point[] greenKnots = new Point[2];
        greenKnots[0] = new Point(30,0);
        greenKnots[1] = new Point(255,255);

        Point[] rgbKnots2 = new Point[3];
        rgbKnots2[0] = new Point(0,6);
        rgbKnots2[1] = new Point(140,141);
        rgbKnots2[2] = new Point(213,255);

        Point[] yellowKnots = new Point[3];
        yellowKnots[0] = new Point(0,0);
        yellowKnots[1] = new Point(126,131);
        yellowKnots[2] = new Point(230,255);

        Point[] blueKnots = new Point[4];
        blueKnots[0] = new Point(0,0);
        blueKnots[1] = new Point(171,173);
        blueKnots[2] = new Point(219,210);
        blueKnots[3] = new Point(255,255);

        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots,null,greenKnots,null));
        filter.addSubFilter(new BrightnessSubFilter(20));
        filter.addSubFilter(new SaturationSubFilter(.78f));
        filter.addSubFilter(new ContrastSubFilter(.73f));
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots2,yellowKnots, yellowKnots, blueKnots ));

        return filter;
    }

    public static Filter getBwFilter(){
        /**
         * bwfilter: should look good for landscapes, misc features (whatever doesn't have enough filters..)
         */

        Filter filter = new Filter();
        filter.addSubFilter(new BrightnessSubFilter(20));
        filter.addSubFilter(new ContrastSubFilter(1.3f));
        filter.addSubFilter(new SaturationSubFilter(0f));

        return filter;

    }


    public static Filter getSepiaFilter(Context context){

        /**
         * sepiafilter: anything antique, landscapes, objects, buildings, cars, trains
         */

        Point[] rgbKnots = new Point[2];

        rgbKnots[0] = new Point(0,4);
        rgbKnots[1] = new Point(240,255);

        Point[] greenKnots = new Point[2];
        greenKnots[0] = new Point(0,8);
        greenKnots[1] = new Point(243,255);

        Point[] redKnots = new Point[3];
        redKnots[0] = new Point(0,27);
        redKnots[1] = new Point(126,163);
        redKnots[2] = new Point(233,255);

        Point[] blueKnots = new Point[3];
        blueKnots[0] = new Point(4,0);
        blueKnots[1] = new Point(129,116);
        blueKnots[2] = new Point(255,238);

        Filter filter = getBwFilter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots,redKnots,greenKnots,blueKnots));
        filter.addSubFilter(new SaturationSubFilter(1.3f));
        filter.addSubFilter(new VignetteSubFilter(context, 70));
        filter.addSubFilter(new BrightnessSubFilter(10));
        return  filter;

    }


    /* * * * * * * * * * * * *
     * All filters starlit and below are taken from the sample filters in zomato photofilters.
     * * * * * * * * * * * * */
    public static Filter getStarLitFilter() {
        Point[] rgbKnots;
        rgbKnots = new Point[8];
        rgbKnots[0] = new Point(0, 0);
        rgbKnots[1] = new Point(34, 6);
        rgbKnots[2] = new Point(69, 23);
        rgbKnots[3] = new Point(100, 58);
        rgbKnots[4] = new Point(150, 154);
        rgbKnots[5] = new Point(176, 196);
        rgbKnots[6] = new Point(207, 233);
        rgbKnots[7] = new Point(255, 255);
        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, null, null, null));
        return filter;
    }

    public static Filter getBlueMessFilter() {
        Point[] redKnots;
        redKnots = new Point[8];
        redKnots[0] = new Point(0, 0);
        redKnots[1] = new Point(86, 34);
        redKnots[2] = new Point(117, 41);
        redKnots[3] = new Point(146, 80);
        redKnots[4] = new Point(170, 151);
        redKnots[5] = new Point(200, 214);
        redKnots[6] = new Point(225, 242);
        redKnots[7] = new Point(255, 255);
        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(null, redKnots, null, null));
        filter.addSubFilter(new BrightnessSubFilter(30));
        filter.addSubFilter(new ContrastSubFilter(1f));
        return filter;
    }

    public static Filter getAweStruckVibeFilter() {
        Point[] rgbKnots;
        Point[] redKnots;
        Point[] greenKnots;
        Point[] blueKnots;

        rgbKnots = new Point[5];
        rgbKnots[0] = new Point(0, 0);
        rgbKnots[1] = new Point(80, 43);
        rgbKnots[2] = new Point(149, 102);
        rgbKnots[3] = new Point(201, 173);
        rgbKnots[4] = new Point(255, 255);

        redKnots = new Point[5];
        redKnots[0] = new Point(0, 0);
        redKnots[1] = new Point(125, 147);
        redKnots[2] = new Point(177, 199);
        redKnots[3] = new Point(213, 228);
        redKnots[4] = new Point(255, 255);


        greenKnots = new Point[6];
        greenKnots[0] = new Point(0, 0);
        greenKnots[1] = new Point(57, 76);
        greenKnots[2] = new Point(103, 130);
        greenKnots[3] = new Point(167, 192);
        greenKnots[4] = new Point(211, 229);
        greenKnots[5] = new Point(255, 255);


        blueKnots = new Point[7];
        blueKnots[0] = new Point(0, 0);
        blueKnots[1] = new Point(38, 62);
        blueKnots[2] = new Point(75, 112);
        blueKnots[3] = new Point(116, 158);
        blueKnots[4] = new Point(171, 204);
        blueKnots[5] = new Point(212, 233);
        blueKnots[6] = new Point(255, 255);

        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots));
        return filter;
    }

    public static Filter getLimeStutterFilter() {
        Point[] blueKnots;
        blueKnots = new Point[3];
        blueKnots[0] = new Point(0, 0);
        blueKnots[1] = new Point(165, 114);
        blueKnots[2] = new Point(255, 255);
        // Check whether output is null or not.
        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(null, null, null, blueKnots));
        return filter;
    }

    public static Filter getNightWhisperFilter() {
        Point[] rgbKnots;
        Point[] redKnots;
        Point[] greenKnots;
        Point[] blueKnots;

        rgbKnots = new Point[3];
        rgbKnots[0] = new Point(0, 0);
        rgbKnots[1] = new Point(174, 109);
        rgbKnots[2] = new Point(255, 255);

        redKnots = new Point[4];
        redKnots[0] = new Point(0, 0);
        redKnots[1] = new Point(70, 114);
        redKnots[2] = new Point(157, 145);
        redKnots[3] = new Point(255, 255);

        greenKnots = new Point[3];
        greenKnots[0] = new Point(0, 0);
        greenKnots[1] = new Point(109, 138);
        greenKnots[2] = new Point(255, 255);

        blueKnots = new Point[3];
        blueKnots[0] = new Point(0, 0);
        blueKnots[1] = new Point(113, 152);
        blueKnots[2] = new Point(255, 255);

        Filter filter = new Filter();
        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots));
        return filter;
    }

    // TODO: add your filters to this array in alphabetical order.
    public static ArrayList<Filter> getAllFilters(Context context){
        return new ArrayList<>( Arrays.asList( new Filter[]{getAweStruckVibeFilter(),getBlueMessFilter(),getBwFilter(),getLimeStutterFilter(),getNightVisionFilter(),getNightWhisperFilter(),getPolaroidFilter(),getSepiaFilter(context),getStarLitFilter(),new Filter()}));
    }

    // TODO: add your filters' names to this array in alphabetical order.
    public static ArrayList<String> getAllNames(){
        return new ArrayList<>( Arrays.asList(new String[]{"Awestruck Vibe","Blue Mess","Graphite","Lime Stutter","Night Vision","Night Whisper","Polaroid","Sepia","Star Lit","No Filter"}));
    }

    // TODO: generate the lists of features corresponding to each filter (copypaste from the googledoc)
    // TODO: add corresponding filters/names in order of the average of the percent certainties of the features corresponding to each filter
    // TODO: add remaining filters/names in arbitrary (probably alphabetic) order, but with blank last.
    public static ArrayList<Pair<Filter,String>> getFiltersInOrder(ArrayList<Double> percentages, ArrayList<String> features,Context context){
        // Invariant: percentages and features have the same length.
        // Invariant: All names and All filters have the same length.
        // Both lists should always have a blank filter at the end.

        // placeholder code so that all the filters show up as tabs
        ArrayList<Pair<Filter,String>> result = new ArrayList<>();
        ArrayList<Filter> filters = getAllFilters(context);
        ArrayList<String> names = getAllNames();
        for(int i=0; i<filters.size();i++)
            result.add(new Pair<>(filters.get(i),names.get(i)));


        return result;
    }

    // Done. Does what the name suggests.
    public static Map<String,Filter> getFilterMap(Context context){
        ArrayList<Filter> filters = getAllFilters(context);
        ArrayList<String> names = getAllNames();
        HashMap<String,Filter> result = new HashMap<>();

        for(int i=0; i<names.size(); i++) result.put(names.get(i),filters.get(i));

        return result;
    }

    // Done. Does what the name suggests.
    public static Filter getFilter(String name, Context context){
        return getFilterMap(context).get(name);
    }

}


>>>>>>> Stashed changes
