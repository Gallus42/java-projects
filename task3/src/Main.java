import javax.swing.*;
import java.util.*;

public class Main
{
    static ArrayList<Student> studentList = new ArrayList<Student>();
    static ArrayDeque<Student> studentDeque = new ArrayDeque<Student>();
    static HashMap<String, Float> studentHash = new HashMap<String, Float>();

    static Map<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
    static Scanner in = new Scanner(System.in);

    static void showList()
    {
        for (Student student : studentList)
        {
            System.out.println(student.toString());
        }
    }

    static void showDeque()
    {
        for (Student student : studentDeque)
        {
            System.out.println(student.toString());
        }
    }

    static void showHash()
    {
        for (Map.Entry<String, Float> student : studentHash.entrySet())
        {
            System.out.println(student.getKey() + " Средний бал: " + student.getValue());
        }
    }

    static void divideHash()
    {
        HashMap<String, Float> firstHash = new HashMap<String, Float>();
        HashMap<String, Float> secondHash = new HashMap<String, Float>();

        for (Map.Entry<String, Float> student : studentHash.entrySet())
        {
            if (student.getValue() < 3f)
            {
                firstHash.put(student.getKey(), student.getValue());
            }
            else
            {
                secondHash.put(student.getKey(), student.getValue());
            }
        }

        System.out.println("На отчисление:");
        for (Map.Entry<String, Float> student : firstHash.entrySet())
        {
            System.out.println(student.getKey() + " Средний бал: " + student.getValue());
        }
        System.out.println("Переходят на следующий курс:");
        for (Map.Entry<String, Float> student : secondHash.entrySet())
        {
            System.out.println(student.getKey() + " Средний бал: " + student.getValue());
        }
    }
    public static void main(String[] args)
    {
        Student first = new Student(4242, "Кандрашов", "Аркадий", "Степанович", new Date(2001, 12, 10), 3.4f);
        Student second = new Student(1234, "Интемиров", "Регин", "Тахир-оглы", new Date(2002, 5, 12), 1.8f);
        Student third = new Student(1555, "Тугодумов", "Арсений", "Петрович", new Date(2001, 6, 4), 5.0f);
        Student fourth = new Student(1218, "Ломто", "Джон", "Робертович", new Date(2003, 4, 1), 4.2f);
        Student fifth = new Student(1443, "Проблемный", "Темур", "Тэгович", new Date(2001, 8, 1), 2.4f);

        // 1 часть
        studentList.add(first);
        studentList.add(second);
        studentList.add(third);
        studentList.add(fourth);
        studentList.add(fifth);

        System.out.println("Вывод студентов из листа");
        showList();

        System.out.println("Изменён id третьего");
        studentList.get(2).id = 9999;
        showList();

        System.out.println("Удалён последний");
        studentList.remove(studentList.size() - 1);
        showList();

        System.out.println("Отсортировать по возрасту");
        studentList.sort(Comparator.naturalOrder());
        showList();

        System.out.println("Удалить всех студентов");
        studentList.clear();
        showList();

        // 2 часть
        System.out.println();
        System.out.println("2 часть");

        studentDeque.offer(first);
        studentDeque.offer(second);
        studentDeque.offer(third);
        studentDeque.offer(fourth);
        studentDeque.offer(fifth);

        System.out.println("Вывод студентов из очереди");
        showDeque();

        System.out.println("Удалить студентов из очереди при помощи poll()");
        while (studentDeque.size() > 0)
        {
            studentDeque.poll();
        }

        showDeque();


        // 3 часть
        System.out.println();
        System.out.println("3 часть");

        studentHash.put(first.fio(), first.avgMark);
        studentHash.put(second.fio(), second.avgMark);
        studentHash.put(third.fio(), third.avgMark);
        studentHash.put(fourth.fio(), fourth.avgMark);
        studentHash.put(fifth.fio(), fifth.avgMark);

        System.out.println("Создать и написать Hash");
        showHash();

        System.out.println("Разделить Hash");
        divideHash();

        while (true) // взял из своего же сделанного по ошибке задания 3.2
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

        showFrequencyMap();
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
    static void showFrequencyMap()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("");
        System.out.println("Сортировать по ключу (y/n):");
        System.out.println("");
        String text = in.nextLine();
        if (text.equals("y"))
        {
            ArrayList<Character> sortedKeys = new ArrayList(frequencyMap.keySet());
            Collections.sort(sortedKeys, Comparator.naturalOrder());
            for (Character ch : sortedKeys)
            {
                System.out.println("Символ: " + ch + " Встречается " + frequencyMap.get(ch) + " раз.");
            }
        }
        else
        {
            frequencyMap.entrySet().stream()
                    .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                    .forEach(Main::keyByValue);
        }
    }

    static void keyByValue(Object x)
    {
        Map.Entry<Character, Integer> entry =  (Map.Entry<Character, Integer>) x;
//
        System.out.println("Символ: " + entry.getKey() + " Встречается " + entry.getValue()  + " раз.");
    }

}