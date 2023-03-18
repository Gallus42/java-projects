import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Main
{
    static Map<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
    public static void main(String[] args)
    {
        while (true)
        {
            mainPage();
        }
    }

    static void mainPage()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("");
        System.out.println("Добро пожаловать в частотный анализ");
        System.out.println("Введите текст");
        System.out.println("");
        String text = in.nextLine();
        for (int charIndex = 0; charIndex < text.length(); ++charIndex)
        {
            addCharacter(text.charAt(charIndex));
        }

        showMap();
        frequencyMap.clear();
    }

    static void addCharacter(Character ch)
    {
        if ((ch <= 'А' || ch >= 'я') && (ch != 'ё' && ch != 'Ё'))
        {
            return;
        }

        ch = Character.toLowerCase(ch);

        if (!frequencyMap.containsKey(ch))
            frequencyMap.put(ch, 1);
        else
            frequencyMap.put(ch, frequencyMap.get(ch) + 1);
    }
    static void showMap()
    {
        ArrayList<Character> sortedKeys=new ArrayList(frequencyMap.keySet());
        Collections.sort(sortedKeys);

        for (Character ch : sortedKeys)
        {
            System.out.println("Символ: " + ch + " Встречается " + frequencyMap.get(ch) + " раз.");
        }
    }




}