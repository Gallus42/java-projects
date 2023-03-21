import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;


enum PropertyType
{
    Surname,
    CityName,
    TelephoneNumber,
    SkypeUserName,
    PhilosophySchool, // Приверженность филосовской школе - важное поле для идентификации людей в этом справочнике
}


public class Main
{
    static ArrayList<DictionaryElement> dictionary = new ArrayList<DictionaryElement>();
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args)
    {
        DictionaryElement diogen = new DictionaryElement("Диогенский", "Коринф", "89513332142", "bochka_4_life", "Аскетизм");
        DictionaryElement eildenstein = new DictionaryElement("Эльденштейн", "Вятка", "7888442121", "f1l30bm3nn1k1337", "Прогрессионизм");
        DictionaryElement mandel = new DictionaryElement("Мандельный", "Псков", "648532", "nav_tyt_tam", "Авангардизм");
        DictionaryElement gandi = new DictionaryElement("Ганди", "Нью-Дели", "+91 555 3243", "neprot_zl_nas", "Сатьяграха");
        DictionaryElement akunin = new DictionaryElement("Чхартишвили", "Лондон", "+44 766 211", "akunin_official", "Сатьяграха");

        dictionary.add(diogen);
        dictionary.add(eildenstein);
        dictionary.add(mandel);
        dictionary.add(gandi);
        dictionary.add(akunin);

        sortDictionary();
        while (true) // Пусть крутится, мы ж не хотим каждый раз запускать заново (я уверен есть вариант поизящней)
        {
            mainPage();
        }
    }

    static void showFullDictionary()
    {
        sortDictionary();
        for (DictionaryElement row : dictionary)
        {
            System.out.println(row.toString());
        }
    }
    static void showAbonentByProperty()
    {
        System.out.println("Выберите свойство");
        System.out.println("1. Фамилия");
        System.out.println("2. Город");
        System.out.println("3. Скайп");
        int num = in.nextInt();
        PropertyType type;
        switch (num)
        {
            case 1:
                type = PropertyType.Surname;
                break;
            case 2:
                type = PropertyType.CityName;
                break;
            case 3:
                type = PropertyType.SkypeUserName;
                break;
            default:
                System.out.println("Нет такого свойства ⸨◺_◿⸩");
                showAbonentByProperty();
                return;
        }
        System.out.println("Введите значение свойства");

        String value = in.next();

        for (DictionaryElement row : dictionary)
        {
            if (row.propertyValue(type).equals(value))
            {
                System.out.println(row.toString());
                return;
            }
        }

        System.out.println("Ничего не найдено");

    }
    static void showAllAbonentsByProperty()
    {
        System.out.println("Выберите свойство");
        System.out.println("1. Филсосвская школа");
        int num = in.nextInt();
        PropertyType type;
        switch (num)
        {
            case 1:
                type = PropertyType.PhilosophySchool;
                break;
            default:
                System.out.println("Нет такого свойства ⸨◺_◿⸩");
                showAbonentByProperty();
                return;
        }

        System.out.println("Введите значение свойства");
        System.out.println("// тестовый вариант программы, поэтому ищем Сатьяграху, только она у двух людей");
        System.out.println("Сатьяграха\n");

        String value = "Сатьяграха";

        boolean isEmpty = true;
        for (DictionaryElement row : dictionary)
        {
            if (row.propertyValue(type).equals(value))
            {
                System.out.println(row.toString());
                isEmpty = false;
            }
        }

        if (isEmpty)
            System.out.println("Ничего не найдено");

    }

    static void sortDictionary()
    {
        dictionary.sort(Comparator.naturalOrder());
    }
    static void mainPage()
    {
        System.out.print("\n");
        System.out.print("Добро пожаловать в справочник\n");
        System.out.print("На выбор предложены наборы команд (введите соответствующее команде число):\n");
        System.out.print("1. Посмотреть весь справочник отсортированный по фамилии;\n");
        System.out.print("2. Найти абонента по заданному полю;\n");
        System.out.print("3. Найти всех абонентов по заданному полю;\n");
        int num = in.nextInt();
        switch (num)
        {
            case 1:
                showFullDictionary();
                break;
            case 2:
                showAbonentByProperty();
                break;
            case 3:
                showAllAbonentsByProperty();
                break;
            default:
                System.out.print("Нет такой команды ⸨◺_◿⸩\n");
                mainPage();
                break;
        }
    }
}

// todo: можно вынести это в отдельный файл, енумератор тоже
class DictionaryElement implements Comparable<DictionaryElement>
{
    String surname;
    String cityName;
    String telephoneNumber;
    String skypeUserName;
    String philosophySchool;

    //    хотел всех неопределившихся записывать в софисты, но у JAVA нет дефолтных значений(
    DictionaryElement(String surname, String cityName, String telephoneNumber, String skypeUserName, String philosophySchool /*= "софист"*/)
    {
        this.surname = surname;
        this.cityName = cityName;
        this.telephoneNumber = telephoneNumber;
        this.skypeUserName = skypeUserName;
        this.philosophySchool = philosophySchool;
    }

    public void setProperty(PropertyType type, String value)
    {
        switch (type)
        {
            case Surname:
                this.surname = value;
                break;
            case CityName:
                this.cityName = value;
                break;
            case TelephoneNumber:
                this.telephoneNumber = value;
                break;
            case SkypeUserName:
                this.skypeUserName = value;
                break;
            case PhilosophySchool:
                this.philosophySchool = value;
                break;
        }
    }

    public String propertyValue(PropertyType type)
    {
        switch (type)
        {
            case Surname:
                return this.surname;
            case CityName:
                return this.cityName;
            case TelephoneNumber:
                return this.telephoneNumber;
            case SkypeUserName:
                return this.skypeUserName;
            case PhilosophySchool:
                return this.philosophySchool;
            default:
                return "InvalidProperty";
        }
    }

    @Override
    public String toString()
    {
        // todo: Можно выводит при помощи цикла и Property value, а так же не выводить имя поля, а сделать строку заголовка
        return "Фамилия: " + this.surname + ", Город: " + this.cityName +  ", Номер Телефона: " + this.telephoneNumber +", Скайп: " + this.skypeUserName + ", Филосовская школа: " + this.philosophySchool;
    }

    @Override
    public int compareTo(DictionaryElement o)
    {
        return surname.compareTo(o.surname);
    }
}
