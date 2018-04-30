package farey;

/**
 *
 * @author Deisy Zambrano
 */
public class Farey {

    int num;
    int den;

    public Farey(int num, int den) {
        this.num = num;
        this.den = den;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDen() {
        return den;
    }

    public void setDen(int den) {
        this.den = den;
    }

    @Override
    public String toString() {
        return this.num + "/" + this.den;
    }

    public double getValue() {
        return (double) this.num / (double) this.den;
    }

}
