package task2613;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class CurrencyManipulatorFactory {
    private static Map <String, CurrencyManipulator> map = new HashMap<>();
    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
       currencyCode = currencyCode.toUpperCase();
       if(!map.containsKey(currencyCode)){
           CurrencyManipulator manipulator = new CurrencyManipulator(currencyCode);
           map.put(currencyCode, manipulator);
       }
       return map.get(currencyCode);
    }
    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){
        Collection<CurrencyManipulator> allCurrencyManipulators = new ArrayList<>();
        for (String cm: map.keySet()
             ) {
            allCurrencyManipulators.add(map.get(cm));
        }
        return allCurrencyManipulators;
    }
}
