import java.util.Arrays;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 1.1
        doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 1.2
        int lowerBound = 0;
        int upperBound = doubElements.length - 1;
        if ((_index < lowerBound) || (_index > upperBound ))    return -1;
        return doubElements[_index];
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 1.3
        int lowerBound = 0;
        int upperBound = doubElements.length - 1;
        if ( (_index >= lowerBound) && (_index <= upperBound))
            doubElements[_index] = _value;
        else
            doubElements[upperBound] = _value;
    }

    public double[] getAllElements() {
        //TODO Task 1.4
        return doubElements;
    }

    public int getVectorSize() {
        //TODO Task 1.5
        int vec_size = doubElements.length;
        return vec_size;
    }

    public Vector reSize(int _size) {
        //TODO Task 1.6
        int vec_size = doubElements.length;
        if ((_size == vec_size) || (_size <= 0))   return this;
        if (_size < vec_size){
            doubElements = Arrays.copyOfRange(doubElements, 0, _size);
        }
        else {
            doubElements = Arrays.copyOf(doubElements, _size);
            for (int i = vec_size; i < _size; i++)
                doubElements[i] = -1;
        }
        return this;
    }

    public Vector add(Vector _v) {
        //TODO Task 1.7
        int vec1_size = getVectorSize();
        int vec2_size = _v.getVectorSize();
        int vec_size = vec1_size;
        if (vec2_size > vec1_size){
            vec_size = vec2_size;
            reSize(vec_size);
        }
        else if (vec1_size > vec2_size){
            vec_size = vec1_size;
            _v.reSize(vec_size);
        }

        double[] empty_elements = new double[vec_size];
        Vector sum_vector = new Vector(empty_elements);
        for (int i = 0; i < vec_size; i++)
            sum_vector.setElementatIndex(getElementatIndex(i) + _v.getElementatIndex(i), i);

        return  sum_vector;
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 1.8
        int vec1_size = getVectorSize();
        int vec2_size = _v.getVectorSize();
        int vec_size = vec1_size;
        if (vec2_size > vec1_size){
            vec_size = vec2_size;
            reSize(vec_size);
        }
        else if (vec1_size > vec2_size){
            vec_size = vec1_size;
            _v.reSize(vec_size);
        }

        double[] empty_elements = new double[vec_size];
        Vector subtraction_vector = new Vector(empty_elements);
        for (int i = 0; i < vec_size; i++)
            subtraction_vector.setElementatIndex(getElementatIndex(i) - _v.getElementatIndex(i), i);

        return  subtraction_vector;
    }

    public double dotProduct(Vector _v) {
        //TODO Task 1.9
        int vec1_size = getVectorSize();
        int vec2_size = _v.getVectorSize();
        int vec_size = vec1_size;
        if (vec2_size > vec1_size){
            vec_size = vec2_size;
            reSize(vec_size);
        }
        else if (vec1_size > vec2_size){
            vec_size = vec1_size;
            _v.reSize(vec_size);
        }

        double dot_product = 0;
        for (int i = 0; i < vec_size; i++)
            dot_product += getElementatIndex(i)*_v.getElementatIndex(i);

        return  dot_product;
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 1.10
        int vec1_size = getVectorSize();
        int vec2_size = _v.getVectorSize();
        int vec_size = vec1_size;
        if (vec2_size > vec1_size){
            vec_size = vec2_size;
            reSize(vec_size);
        }
        else if (vec1_size > vec2_size){
            vec_size = vec1_size;
            _v.reSize(vec_size);
        }

        double cosine_similarity = dotProduct(_v)/(Math.sqrt(dotProduct(this))*Math.sqrt(_v.dotProduct(_v)));
        return  cosine_similarity;
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;
        //TODO Task 1.11
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
