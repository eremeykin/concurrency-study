package pete.eremeykin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeCalculator {

    private int currentPrime;
    private boolean[] nonPrime;

    public PrimeCalculator() {
    }

    public List<Integer> calculate(BigInteger number) {
        PrimeCalculator primeCalculator = new PrimeCalculator();
        return primeCalculator.doCalculate(number);
    }

    private List<Integer> doCalculate(BigInteger number) {
        int n = number.intValueExact();
        nonPrime = new boolean[(int) Math.sqrt(n)];
        currentPrime = 2;
        return getPrimes(n);
    }

    private List<Integer> getPrimes(int n) {
        List<Integer> result = new ArrayList<>();
        while (n != 1) {
            for (int p = getNextPrime(n); n % p == 0; n /= p) {
                result.add(p);
            }
        }
        return result;
    }

    private int getNextPrime(int n) {
        if (currentPrime > Math.sqrt(n)) {
            return n;
        }
        for (int v = currentPrime << 1; v < nonPrime.length; v += currentPrime) {
            nonPrime[v] = true;
        }
        int result = currentPrime++;
        for (; currentPrime < nonPrime.length && nonPrime[currentPrime]; currentPrime++) ;
        return result;
    }
}
