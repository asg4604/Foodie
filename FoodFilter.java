import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class FoodFilter {
    private ArrayList<String> restrictedWords;
    private HashMap<String, HashSet<String>> saveWordMaps;


    public FoodFilter(String restrictedWordsStr, String saveWordsString) {
        this.restrictedWords = new ArrayList<String>();
        String[] restrictedWordsPieces = restrictedWordsStr.split(",");
        for(int x = 0; x < restrictedWordsPieces.length; x++) {
            this.restrictedWords.add(restrictedWordsPieces[x].trim().toLowerCase());
        }
        this.saveWordMaps = new HashMap<>();
        try {
            String[] saveWordPieces = saveWordsString.split(",");
            for (int x = 0; x < saveWordPieces.length; x++) {
                String[] subPieces = saveWordPieces[x].split(":");
                String keyStr = subPieces[0].trim().toLowerCase();
                String valueStr = subPieces[1].trim().toLowerCase();
                if (saveWordMaps.containsKey(keyStr)) {
                    HashSet<String> saveWordSet = saveWordMaps.get(keyStr);
                    if (!saveWordSet.contains(valueStr)) {
                        saveWordSet.add(valueStr);
                    }
                } else {
                    HashSet<String> saveWordSet = new HashSet<>();
                    saveWordSet.add(valueStr);
                    saveWordMaps.put(keyStr, saveWordSet);
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public FoodFilter() {
        this.saveWordMaps = new HashMap<>();
        this.restrictedWords = new ArrayList<>();
    }

    public void addRestriction(String restriction) {
        this.restrictedWords.add(restriction);
    }

    public void removeRestriction(String restriction) {
        this.restrictedWords.remove(restriction);
    }

    public void addSaveWord(String foodRestriction, String saveWord) {
        HashSet<String> saveWordSet = saveWordMaps.get(foodRestriction);
        if (saveWordSet == null) {
            saveWordSet = new HashSet<>();
        }
        saveWordSet.add(saveWord);
    }

    public void removeSaveWord(String foodRestriction, String saveWord) {
        HashSet saveWordSet = saveWordMaps.get(foodRestriction);
        if (saveWordSet == null) {
            saveWordSet = new HashSet();
        }
        saveWordSet.remove(saveWord);
    }

    public boolean deepSearch(String ingredients) {
        String[] ingredient_pieces = ingredients.split(",");
        String current_ingredient;
        boolean found = false;
        boolean saved = false;
        // Iterate through each ingredient
        for (int x = 0; x < ingredient_pieces.length; x++) {
            current_ingredient = ingredient_pieces[x].trim().toLowerCase();
            // Iterate through all possible unwanted ingredients for each ingredient
            for (int i = 0; i < restrictedWords.size(); i++) {
                String restricted_ing = restrictedWords.get(i);
                found = current_ingredient.contains(restricted_ing);
                // If a bad one is found check if there is a save phrase with in the products current ingredient that corresponds to the bad ingredient
                if (found) {
                    if (!saveWordMaps.containsKey(restricted_ing)) break;
                    for (String saveWord : saveWordMaps.get(restricted_ing)) {
                        if (current_ingredient.contains(saveWord)) {
                            saved = true;
                            found = false;
                        } else {
                            saved = false;
                        }
                    }
                }
                if (found) return true;
            }
        }
        return found;
    }


    public HashSet<String> getSaveWords(String key) {
        return saveWordMaps.get(key.toLowerCase());
    }

    public ArrayList<String> getRestrictedWords() {
        return restrictedWords;
    }

}
