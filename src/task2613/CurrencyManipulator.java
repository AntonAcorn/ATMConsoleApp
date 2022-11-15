package task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;


import java.util.*;


public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        this.denominations = new TreeMap<>();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        int totalAmount = 0;

        for (Integer amount : denominations.keySet()
        ) {
            totalAmount += amount * denominations.get(amount);
        }
        return totalAmount;
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        if (getTotalAmount() >= expectedAmount) {
            return true;
        }
        return false;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int sum = expectedAmount; // остаток
        HashMap<Integer, Integer> copyDenominations = new HashMap<>(denominations);
        List<Integer> keys = new ArrayList<>(this.denominations.keySet());
        Collections.sort(keys); //отсортировали банкноты // 100, 500, 1000
        Collections.reverse(keys); //Перевернули // 1000, 500, 100, т.к. сначала вытягиваем самую крупную банкноту

        TreeMap<Integer, Integer> resultMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        for (Integer denomination : keys) {
            int key = denomination;
            int value = copyDenominations.get(key);
            while (true) {
                if (sum < key || value == 0){
                    copyDenominations.put(key, value);
                    break;
                }
                sum -= key;
                value--;

                if (resultMap.containsKey(key))
                    resultMap.put(key, resultMap.get(key) + 1);
                else
                    resultMap.put(key, 1);
            }
        }

        if (sum > 0)
            throw new NotEnoughMoneyException();
        else {
            this.denominations.clear();
            this.denominations.putAll(copyDenominations);
        }
        return resultMap;
    }
}