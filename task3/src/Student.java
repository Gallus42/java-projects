import java.util.Date;

public class Student implements Comparable<Student>
{
    int id;
    String surname;
    String name;
    String fatherName;
    Date birthDate;
    float avgMark;

    Student (int id, String surname,  String name,  String fatherName, Date birthDate, float avgMark)
    {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.fatherName = fatherName;
        this.birthDate = birthDate;
        this.avgMark = avgMark;
    }

    public String toString()
    {
        return "ID: " + this.id + ", Фамилия: " + this.surname + ", Имя: " + this.name + ", Отчество: " + this.fatherName + ", Дата рождения: " + this.birthDate.getYear() + "." + this.birthDate.getMonth() + "." + this.birthDate.getDay() + ", Средняя оценка:" + this.avgMark;
    }

    public int compareTo(Student o)
    {
        return this.birthDate.compareTo(o.birthDate);
    }

    public String fio()
    {
        return this.surname + " " + this.name+ " " + this.fatherName;
    }

}
