
package edu.cs.cs184.photoapp;

import android.content.Context;
import android.util.Log;
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

import static java.lang.Math.max;

public class CustomFilters {

    final static int MIN_FILTERS = 5;

    // TODO: Add all of your filters, translate data on the googledoc into the corresponding functions, and replace the placeholder code.
    // TODO: come up with goofy ass names like the ones from sample filters
    // TODO: if someone wants to create an object that wraps a filter and its name and refactor, feel free

    public static ArrayList<CustomFilter> getFilters(ArrayList<String> features, ArrayList<Double> percentCertainties, Context context){
        // TODO: match features with filters
        //Filter m = new Filter();
        //return m;
        ArrayList<CustomFilter> matchingFilters = new ArrayList<>();
        for(int i=0; i<getAllFeatures().size(); i++){

            CustomFilter customFilter = new CustomFilter();

            // adds Filter
            customFilter.addFilter(getAllNames().get(i));

            // for each imgFeature, if filterFeatures contains it, adds imgFeature and certainty
            for(int j=0; j<features.size(); j++){

                // for testing -
                if(getAllFeatures().get(i).isEmpty()){
                    //customFilter.addFeature(features.get(j), percentCertainties.get(j));
                }

                // adds newline to end of feature which allows it to search for an exact match
                // e.g. if looking for photograph, "nature photography\n" contains "photograph", but not "photograph\n"
                if(getAllFeatures().get(i).contains(features.get(j)+"\n")){
                    customFilter.addFeature(features.get(j), percentCertainties.get(j));
                }
            }


            // puts new customFilter in list, maintains descending sort by score
            double score = customFilter.getScore();
            Log.d("Filter sorting", customFilter.getFilterName()+" has score: "+score);
            boolean entered = false;
            int index = 0;
            while(true){
                // if empty, simply add
                if(matchingFilters.size() == 0){
                    matchingFilters.add(customFilter);
                    break;
                }
                if(index < matchingFilters.size()) {
                    // if score >= score at index, add at index
                    if (score >= matchingFilters.get(index).getScore()){
                        matchingFilters.add(index, customFilter);
                        break;
                    }

                    // else check next index
                    else index++;
                }

                // if score < all other scores, add at end
                else{
                    matchingFilters.add(customFilter);
                    break;
                }
            }
            Log.d("Filter sorting", customFilter.getFilterName() + " entered at: "+index);

        }

        // removes filters that don't match
        boolean endFound = false;
        int i=0;
        while(!endFound) {
            if(matchingFilters.get(i).getFilterName().equals("No Filter")) endFound = true;
            else i++;
        }
        Log.d("filtersort", "end found at: "+i);
        for(int j = matchingFilters.size()-1; j>max(i, 4); j--){
            Log.d("filtersort", "Removing: "+matchingFilters.get(j).getFilterName());
            matchingFilters.remove(j);

        }


        return matchingFilters;

    }


    // TODO: Add filters, use this skeleton, then add to method to getFilters, name to getNames, and feature list to getFeatures
    public static Filter getFILTERNAME(){
        //specify constants

        Filter filter = new Filter();

        // filter.addSubfilter for all subfilters

        return filter;
    }

    public static Filter getMacroFilter(Context context){
        //specify constants
        Point[] rgbKnots = new Point[5];
        rgbKnots[0] = new Point(0,0);
        rgbKnots[1] = new Point(64,40);
        rgbKnots[2] = new Point(128,118);
        rgbKnots[3] = new Point(191,185);
        rgbKnots[4] = new Point(255,255);

        Filter filter = new Filter();

        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, null, null, null));
        filter.addSubFilter(new VignetteSubFilter(context, 120));
        filter.addSubFilter(new ContrastSubFilter(1.3f));
        filter.addSubFilter(new BrightnessSubFilter(30));
        filter.addSubFilter(new SaturationSubFilter(1.2f));

        return filter;
    }

    public static Filter getPortraitFilter(Context context){
        //specify constants
        Point[] rgbKnots = new Point[5];
        rgbKnots[0] = new Point(0,0);
        rgbKnots[1] = new Point(64,50);
        rgbKnots[2] = new Point(128,128);
        rgbKnots[3] = new Point(191,195);
        rgbKnots[4] = new Point(255,250);

        Filter filter = new Filter();

        filter.addSubFilter(new ToneCurveSubFilter(rgbKnots, null, null, null));
        filter.addSubFilter(new VignetteSubFilter(context, 90));
        filter.addSubFilter(new ContrastSubFilter(1.1f));
        filter.addSubFilter(new BrightnessSubFilter(20));
        filter.addSubFilter(new SaturationSubFilter(0.7f));

        return filter;
    }

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
         *
         * maybe architecture?
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
        return new ArrayList<>( Arrays.asList( new Filter[]{getAweStruckVibeFilter(),getBlueMessFilter(),getBwFilter(),getLimeStutterFilter(),getMacroFilter(context),getNightVisionFilter(),getNightWhisperFilter(),getPolaroidFilter(),getPortraitFilter(context),getSepiaFilter(context),getStarLitFilter(),new Filter()}));
    }

    // TODO: add your filters' names to this array in alphabetical order.
    public static ArrayList<String> getAllNames(){
        return new ArrayList<>( Arrays.asList(new String[]{"Awestruck Vibe","Blue Mess","Graphite","Lime Stutter","Macro","Night Vision","Night Whisper","Polaroid","Portrait","Sepia","Star Lit","No Filter"}));
    }

    public static ArrayList<String> getAllFeatures(){
        ArrayList<String> featureList = new ArrayList<>();
        featureList.add("nature\n" + //                     start awestruck vibe
                "\"nebula\n" +
                "\"\n" +
                "neighbourhood\n" +
                "night\n" +
                "ocean\n" +
                "ophthalmology\n" +
                "organ\n" +
                "organism\n" +
                "outer space\n" +
                "party supply\n" +
                "path\n" +
                "pencil\n" +
                "performance car\n" +
                "personal luxury car\n" +
                "photograph\n" +
                "photography\n" +
                "pier\n" +
                "plaine\n" +
                "plant\n" +
                "plant stem\n" +
                "plucked string instruments\n" +
                "plywood\n" +
                "prairie\n" +
                "purple\n" +
                "rail transport\n" +
                "recipe\n" +
                "reflection\n" +
                "residential area\n" +
                "ridge\n" +
                "ristretto\n" +
                "road\n" +
                "road trip\n" +
                "rodent\n" +
                "marine mammal\n" +
                "metropolis\n" +
                "metropolitan area\n" +
                "mist\n" +
                "mixed use\n" +
                "moisture\n" +
                "monochrome\n" +
                "monochrome photography\n" +
                "morning\n" +
                "motor vehicle\n" +
                "mountain\n" +
                "mountainous landforms\n" +
                "musical instrument\n" +
                "musical instrument accessory"); //         awestruck vibe
        featureList.add("floor\n" + //                      start blue mess / vintage for now
                "flooring\n" +
                "flower\n" +
                "flowering plant\n" +
                "fog\n" +
                "food\n" +
                "fox squirrel\n" +
                "freezing\n" +
                "fried food\n" +
                "fritter\n" +
                "frost\n" +
                "fun\n" +
                "furniture\n" +
                "girl\n" +
                "grass\n" +
                "grassland\n" +
                "guitar\n" +
                "hand\n" +
                "hardwood\n" +
                "highland\n" +
                "highway\n" +
                "hill\n" +
                "home\n" +
                "horizon\n" +
                "house\n" +
                "human\n" +
                "ice cream\n" +
                "infrastructure\n" +
                "instant coffee\n" +
                "interior design\n" +
                "invertebrate\n" +
                "iris\n" +
                "jamaican blue mountain coffee\n" +
                "jellyfish\n" +
                "landmark\n" +
                "lane\n" +
                "light\n" +
                "line\n" +
                "male\n" +
                "mammal\n" +
                "marine biology\n" +
                "marine invertebrates"); //                 end blue mess
        featureList.add("architecture\n" + //               start graphite
                "building\n" +
                "downtown\n" +
                "facade\n" +
                "home\n" +
                "house\n" +
                "infrastructure\n" +
                "landmark\n" +
                "metropolis\n" +
                "metropolitan area\n" +
                "prairie\n" +
                "rail transport\n" +
                "residential area\n" +
                "road\n" +
                "road trip\n" +
                "skyline\n" +
                "skyscraper\n" +
                "street\n" +
                "tower\n" +
                "tower block\n" +
                "town\n" +
                "urban area\n" +
                "window"); //                               end graphite
        featureList.add("shoe\n" + //                       start lime stutter
                "shore\n" +
                "silhouette\n" +
                "sky\n" +
                "skyline\n" +
                "skyscraper\n" +
                "snapshot\n" +
                "snout\n" +
                "snow\n" +
                "space\n" +
                "sports car\n" +
                "spring\n" +
                "square\n" +
                "squirrel\n" +
                "standing\n" +
                "star\n" +
                "steppe\n" +
                "still life photography\n" +
                "street\n" +
                "string instrument\n" +
                "sun\n" +
                "sunglasses\n" +
                "sunlight\n" +
                "sunrise\n" +
                "sunset\n" +
                "table\n" +
                "terrestrial animal\n" +
                "tower\n" +
                "tower block\n" +
                "town\n" +
                "toy\n" +
                "track\n" +
                "transport\n" +
                "tree\n" +
                "tropics\n" +
                "tulip\n" +
                "twig\n" +
                "universe\n" +
                "romance\n" +
                "sand\n" +
                "sea\n" +
                "seals\n" +
                "selfie\n" +
                "shadow\n"); //                             end lime stutter
        featureList.add("asphalt\n" + //                    start MACRO filter
                        "beauty\n" +
                        "branch\n" +
                        "café au lait\n" +
                        "caffè macchiato\n" +
                        "caffeine\n" +
                        "cappuccino\n" +
                        "close up\n" +
                        "coffee\n" +
                        "coffee cup\n" +
                        "cuban espresso\n" +
                        "cup\n" +
                        "dew\n" +
                        "drop\n" +
                        "espresso\n" +
                        "eyelash\n" +
                        "eyelash extensions\n" +
                        "fauna\n" +
                        "finger\n" +
                        "finger food\n" +
                        "flower\n" +
                        "flowering plant\n" +
                        "food\n" +
                        "frost\n" +
                        "grass\n" +
                        "hand\n" +
                        "ice cream\n" +
                        "insect\n" +
                        "instant coffee\n" +
                        "invertebrate\n" +
                        "iris\n" +
                        "jamaican blue mountain coffee\n" +
                        "larva\n" +
                        "leaf\n" +
                        "macro photography\n" +
                        "mist\n" +
                        "moisture\n" +
                        "pencil\n" +
                        "petal\n" +
                        "plant\n" +
                        "plant stem\n" +
                        "sand\n" +
                        "snapshot\n" +
                        "still life photography\n" +
                        "tulip\n" +
                        "twig\n" +
                        "vegetation\n" +
                        "wildflower\n" +
                        "woody plant"); //                  end macro
        featureList.add("Night \n" + //                     start night vision
                "Darkness\n" +
                "Evening\n" +
                "astronomical object\n" +
                "space\n" +
                "astronomy\n" +
                "outer space\n" +
                "universe\n" +
                "backlighting"); //                         end night vision
        featureList.add("vacation\n" + //                   start night whisper
                "vegetable\n" +
                "vegetarian food\n" +
                "vegetation\n" +
                "vehicle\n" +
                "vertebrate\n" +
                "vision care\n" +
                "wall\n" +
                "walrus\n" +
                "water\n" +
                "water resources\n" +
                "wave\n" +
                "whiskers\n" +
                "wiener melange\n" +
                "wilderness\n" +
                "wildlife\n" +
                "wind wave\n" +
                "window\n" +
                "winter\n" +
                "wood\n" +
                "wood stain\n" +
                "woody plant\n" +
                "yellow\n" +
                "darkness\n" +
                "dawn\n" +
                "desk\n" +
                "dish\n" +
                "door\n" +
                "downtown\n" +
                "drop\n" +
                "dusk\n" +
                "ecoregion\n" +
                "ecosystem\n" +
                "electronic instrument\n" +
                "emotion\n" +
                "espresso\n" +
                "evening\n" +
                "extreme sport\n" +
                "eyelash extensions\n" +
                "eyewear"); //                              end night whisper
        featureList.add("beauty\n" + //                     start polaroid
                "café au lait\n" +
                "caffè macchiato\n" +
                "cappuccino\n" +
                "coffee\n" +
                "coffee cup\n" +
                "cup\n" +
                "dish\n" +
                "espresso\n" +
                "fauna\n" +
                "finger food\n" +
                "flower\n" +
                "food\n" +
                "fried food\n" +
                "girl\n" +
                "human\n" +
                "ice cream\n" +
                "nature\n" +
                "selfie\n" +
                "snapshot\n" +
                "vegetable\n" +
                "vegetarian food\n" +
                "wilderness\n" +
                "wildlife"); //                             end polaroid
        featureList.add("angle\n" + //                      start portrait
                "backlighting\n" +
                "beauty\n" +
                "black hair\n" +
                "ceremony\n" +
                "emotion\n" +
                "eyelash\n" +
                "eyelash extensions\n" +
                "eyewear\n" +
                "face\n" +
                "field\n" +
                "finger\n" +
                "girl\n" +
                "hand\n" +
                "human\n" +
                "human body\n" +
                "iris\n" +
                "male\n" +
                "mammal\n" +
                "man\n" +
                "organism\n" +
                "path\n" +
                "photograph\n" +
                "photography\n" +
                "pier\n" +
                "portrait\n" +
                "reflection\n" +
                "romance\n" +
                "selfie\n" +
                "silhouette\n" +
                "standing\n" +
                "sunglasses"); //                           end portrait
        featureList.add("black and white\n" + //            start sepia
                "downtown\n" +
                "fixed link\n" +
                "highway\n" +
                "monochrome\n" +
                "monochrome photography\n" +
                "photograph\n" +
                "rail transport\n" +
                "snapshot\n" +
                "still life photography\n" +
                "track\n" +
                "transport\n" +
                "vacation\n" +
                "wood\n" +
                "wood stain\n" +
                "vehicle"); //                              end sepia
        featureList.add("angle\n" + //                      start star lit
                "architecture\n" +
                "area\n" +
                "arecales\n" +
                "asphalt\n" +
                "astronomical object\n" +
                "astronomy\n" +
                "atmosphere\n" +
                "automotive design\n" +
                "autumn\n" +
                "backlighting\n" +
                "balloon\n" +
                "beach\n" +
                "bed\n" +
                "black\n" +
                "black and white\n" +
                "blue\n" +
                "brick\n" +
                "building\n" +
                "café au lait\n" +
                "caffè macchiato\n" +
                "caffeine\n" +
                "calm\n" +
                "cappuccino\n" +
                "car\n" +
                "ceremony\n" +
                "circle\n" +
                "city\n" +
                "cityscape\n" +
                "cloud\n" +
                "cnidaria\n" +
                "coast\n" +
                "coffee\n" +
                "coffee cup\n" +
                "computer wallpaper\n" +
                "cortado\n" +
                "couch\n" +
                "cuban espresso\n" +
                "cup\n" +
                "facade\n" +
                "fauna\n" +
                "field\n" +
                "finger\n" +
                "finger food\n" +
                "fixed link\n" +
                "flat white"); // end star lit
        featureList.add(""); // no filter
        return featureList;
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



