import org.apache.commons.lang3.time.StopWatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Vector;

public class SemanticMain {
    public List<String> listVocabulary = new ArrayList<>();  //List that contains all the vocabularies loaded from the csv file.
    public List<double[]> listVectors = new ArrayList<>(); //Associated vectors from the csv file.
    public List<Glove> listGlove = new ArrayList<>();
    public final List<String> STOPWORDS;

    public SemanticMain() throws IOException {
        STOPWORDS = Toolkit.loadStopWords();
        Toolkit.loadGLOVE();
    }


    public static void main(String[] args) throws IOException {
        StopWatch mySW = new StopWatch();
        mySW.start();
        SemanticMain mySM = new SemanticMain();
        mySM.listVocabulary = Toolkit.getListVocabulary();
        mySM.listVectors = Toolkit.getlistVectors();
        mySM.listGlove = mySM.CreateGloveList();

        List<CosSimilarityPair> listWN = mySM.WordsNearest("computer");
        Toolkit.PrintSemantic(listWN, 5);

        listWN = mySM.WordsNearest("phd");
        Toolkit.PrintSemantic(listWN, 5);

        List<CosSimilarityPair> listLA = mySM.LogicalAnalogies("china", "uk", "london", 5);
        Toolkit.PrintSemantic("china", "uk", "london", listLA);

        listLA = mySM.LogicalAnalogies("woman", "man", "king", 5);
        Toolkit.PrintSemantic("woman", "man", "king", listLA);

        listLA = mySM.LogicalAnalogies("banana", "apple", "red", 3);
        Toolkit.PrintSemantic("banana", "apple", "red", listLA);
        mySW.stop();

        if (mySW.getTime() > 2000)
            System.out.println("It takes too long to execute your code!\nIt should take less than 2 second to run.");
        else
            System.out.println("Well done!\nElapsed time in milliseconds: " + mySW.getTime());
    }

    public List<Glove> CreateGloveList() {
        List<Glove> listResult = new ArrayList<>();
        //TODO Task 6.1
        for (int i = 0; i < listVocabulary.toArray().length; i++) {
            String vocabulary = listVocabulary.get(i);
            if (!STOPWORDS.contains(vocabulary)) {
                double[] elements = listVectors.get(i);
                boolean f = true;
                if (f) {
                    for (double e : elements) System.out.println(e);
                    f = false;
                }

                Vector vec = new Vector(elements);
                Glove result = new Glove(vocabulary, vec);
                listResult.add(result);
            }
        }
        return listResult;
    }

    public List<CosSimilarityPair> WordsNearest(String _word) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<CosSimilarityPair>();
        //TODO Task 6.2
//        double[] elements= {};
        Vector vec_word = new Vector(new double[50]);
        boolean find_word = false;

        for (Glove g: listGlove)
            if (g.getVocabulary() == _word) {
                vec_word = g.getVector();
                find_word = true;
                System.out.println(g.getVocabulary());
                break;
            }

        if (!find_word) {
            _word = "error";
            for (Glove g: listGlove)
                if (g.getVocabulary() == _word) {
                    vec_word = g.getVector();
                    break;
                }
        }

        for (Glove g: listGlove) {
            if (g.getVocabulary() == _word) continue;
            double cos_similarity = g.getVector().cosineSimilarity(vec_word);
            CosSimilarityPair similarity_pair = new CosSimilarityPair(_word, g.getVocabulary(), cos_similarity);
            listCosineSimilarity.add(similarity_pair);
        }

        return HeapSort.doHeapSort(listCosineSimilarity);
//        return listCosineSimilarity;
    }

    public List<CosSimilarityPair> WordsNearest(Vector _vector) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<CosSimilarityPair>();
        //TODO Task 6.3
        for (Glove g: listGlove) {
            if (g.getVector() == _vector)   continue;
            double cos_similarity = g.getVector().cosineSimilarity(_vector);
//            double cos_similarity = _vector.cosineSimilarity(g.getVector());

            CosSimilarityPair similarity_pair = new CosSimilarityPair(_vector, g.getVocabulary(), cos_similarity);
            listCosineSimilarity.add(similarity_pair);
        }

        return HeapSort.doHeapSort(listCosineSimilarity);
//        return listCosineSimilarity;
    }

    /**
     * Method to calculate the logical analogies by using references.
     * <p>
     * Example: uk is to london as china is to XXXX.
     *       _firISRef  _firTORef _secISRef
     * In the above example, "uk" is the first IS reference; "london" is the first TO reference
     * and "china" is the second IS reference. Moreover, "XXXX" is the vocabulary(ies) we'd like
     * to get from this method.
     * <p>
     * If _top <= 0, then returns an empty listResult.
     * If the vocabulary list does not include _secISRef or _firISRef or _firTORef, then returns an empty listResult.
     *
     * @param _secISRef The second IS reference
     * @param _firISRef The first IS reference
     * @param _firTORef The first TO reference
     * @param _top      How many vocabularies to include.
     */
    public List<CosSimilarityPair> LogicalAnalogies(String _secISRef, String _firISRef, String _firTORef, int _top) {
        List<CosSimilarityPair> listResult = new ArrayList<>();
        //TODO Task 6.4
        Vector vec_secISRef = new Vector(new double[50]);
        Vector vec_firISRef = new Vector(new double[50]);
        Vector vec_firTORef = new Vector(new double[50]);

        for (Glove g: listGlove){
            if (g.getVocabulary() == _secISRef) vec_secISRef = g.getVector();
            if (g.getVocabulary() == _firISRef) vec_firISRef = g.getVector();
            if (g.getVocabulary() == _firTORef) vec_firTORef = g.getVector();
        }

        Vector vec_secTORef = vec_secISRef.subtraction(vec_firISRef).add(vec_firTORef);
        listResult = WordsNearest(vec_secTORef).subList(0, _top);
        return listResult;
    }
}