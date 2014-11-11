package eu.fusepool.p3.dictionarymatcher;

import java.util.List;

/**
 * Test class for DMA
 *
 * @author Gábor Reményi
 */
public class Main {

    static String test = "Barack Hussein Obama II (Listeni/bəˈrɑːk huːˈseɪn oʊˈbɑːmə/; born August 4, 1961) is the 44th and current President of the "
            + "United States, and the first African American to hold the office. Born in Honolulu, Hawaii, Obama is a graduate of Columbia University "
            + "and Harvard Law School, where he served as president of the Harvard Law Review. He was a community organizer in Chicago before earning "
            + "his law degree. He worked as a civil rights attorney and taught constitutional law at the University of Chicago Law School from 1992 "
            + "to 2004. He served three terms representing the 13th District in the Illinois Senate from 1997 to 2004, running unsuccessfully for "
            + "the United States House of Representatives in 2000.";

    static String testSpec = "Az első Őr Pr a Prség vízben Ŋaaa and Jaaa lerakni az Őrség ČäÁÓý €řЖжЦ №ЯФКЛ.";

    static Concept[] dict = {
        new Concept("Barack Hussein Obama", "prefLabel", "http://en.wikipedia.org/wiki/Barack_Obama", "Person"),
        new Concept("United States", "prefLabel", "http://en.wikipedia.org/wiki/United_States", "Location"),
        new Concept("President", "prefLabel", "http://en.wikipedia.org/wiki/President", "Misc"),
        new Concept("Illinois Senate", "prefLabel", "http://en.wikipedia.org/wiki/Illinois_Senate", "Organization"),
        new Concept("Honolulu", "prefLabel", "http://en.wikipedia.org/wiki/Honolulu", "Location"),
        new Concept("Hawaii", "prefLabel", "http://en.wikipedia.org/wiki/Hawaii", "Location")
    };

    static Concept[] dictSpec = {
        new Concept("Őr", "prefLabel", "uri1", "Entity"),
        new Concept("Őrség", "prefLabel", "uri2", "Entity"),
        new Concept("Ŋaaa", "prefLabel", "uri3", "Entity"),
        new Concept("€řЖжЦ №ЯФКЛ", "prefLabel", "uri4", "Entity"),
        new Concept("első", "prefLabel", "uri5", "Entity"),
        new Concept("ČäÁÓý", "prefLabel", "uri6", "Entity")
    };

    private static DictionaryStore dictionary;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test();
        //testSpec();

    }

    private static void test() {
        dictionary = new DictionaryStore();
        for (Concept c : dict) {
            if (Skos.isConceptValid(c.labelText, c.labelType.getName(), c.uri)) {
                dictionary.AddOriginalElement(c.labelText, c.labelType.getName(), c.uri, c.type);
            }
        }
        // no stemming, no case sensitivity
        DictionaryAnnotator da = new DictionaryAnnotator(dictionary, "None", false, 0, false);

        // run annotation
        List<Annotation> entities = da.GetEntities(test);

        for (Annotation e : entities) {
            System.out.println(e);
        }
        System.out.println("---------------");
        System.out.println("Number of entities found: " + entities.size());
        System.out.println("---------------");
    }

    private static void testSpec() {
        dictionary = new DictionaryStore();
        for (Concept c : dictSpec) {
            if (Skos.isConceptValid(c.labelText, c.labelType.getName(), c.uri)) {
                dictionary.AddOriginalElement(c.labelText, c.labelType.getName(), c.uri, c.type);
            }
        }
        // hun stemming, case sensitivity
        DictionaryAnnotator da = new DictionaryAnnotator(dictionary, "Hungarian", true, 0, false);

        // run annotation
        List<Annotation> entities = da.GetEntities(test);

        for (Annotation e : entities) {
            System.out.println(e);
        }
        System.out.println("---------------");
        System.out.println("Number of entities found: " + entities.size());
        System.out.println("---------------");
    }
}