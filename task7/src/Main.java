import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.util.Iterator;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.IOException;
class sumArrayThread extends Thread
{
    ArrayList<Integer> arrayList;
    int res = 0;
    sumArrayThread(ArrayList<Integer> arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public void run()
    {
        for (Integer g : arrayList)
        {
            res += g;
        }

        System.out.println("Дочерний поток "+ this.getName() + " завершен. Результат: " + res);
    }
}
class multArrayThread extends Thread
{
    ArrayList<Integer> row;
    ArrayList<Integer> column;

    int rowIndex;
    int columnIndex;
    int res = 0;
    multArrayThread(ArrayList<Integer> row, ArrayList<Integer> column, int rowIndex, int columnIndex)
    {
        this.row = row;
        this.column = column;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < row.size(); ++i)
        {
            res += row.get(i) * column.get(i);
        }

        System.out.println("Дочерний поток "+ this.getName() + " завершен. Результат: " + res);
    }
}
class fileReadThread extends Thread
{
    Iterator<String> listIterator;
    fileReadThread(Iterator<String> listIterator)
    {
        this.listIterator = listIterator;
    }

    @Override
    public void run()
    {
        while (listIterator.hasNext())
        {
                System.out.println("Дочерний поток " + this.getName() + " Считал строку: " + listIterator.next());
        }
    }

}

public class Main
{
    public static void multiThreadArraySum()
    {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i)
        {
            arrayList.add(i);
        }

        int threadCount = 3;
        int temp = Math.round(arrayList.size() / threadCount);
        int leftElementsCount = arrayList.size();
        int startPos = 0;
        int result = 0;


        ArrayList<sumArrayThread> threadArray = new ArrayList<sumArrayThread>();
        while (leftElementsCount > threadCount)
        {
            ArrayList<Integer> splittedArray = new ArrayList<Integer>();
            for (int i = startPos; i < startPos + temp; ++i)
            {
                splittedArray.add(i);
            }

            sumArrayThread t = new sumArrayThread(splittedArray);
            t.start();
            threadArray.add(t);

            leftElementsCount -= threadCount;
            startPos += threadCount;
        }

        ArrayList<Integer> splittedArray = new ArrayList<Integer>();
        for (int i = startPos; i < startPos + leftElementsCount; ++i)
        {
            splittedArray.add(i);
        }

        sumArrayThread t = new sumArrayThread(splittedArray);
        t.start();
        threadArray.add(t);

        for (sumArrayThread thread : threadArray)
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            result += thread.res;
        }
        System.out.println("Result: " + result);
    }

    public static void multiThreadArrayMult()
    {
        Integer [][] firstArray = {{4, 2}, {3, 1}, {1, 5}};
        Integer [][] secondArray = {{1, 2, 2}, {3, 1, 1}};
        if (firstArray[0].length != secondArray.length)
        {
            return;
        }

        System.out.println("Исходная матрица 1: ");
        for (int i = 0; i < firstArray.length; ++i)
        {
            System.out.print("{");
            for (int j = 0; j < firstArray[0].length; ++j)
            {
                System.out.print(" " + firstArray[i][j] + " ");
            }
            System.out.print("}\n");
        }

        System.out.println("Исходная матрица 2: ");
        for (int i = 0; i < secondArray.length; ++i)
        {
            System.out.print("{");
            for (int j = 0; j < secondArray[0].length; ++j)
            {
                System.out.print(" " + secondArray[i][j] + " ");
            }
            System.out.print("}\n");
        }

        ArrayList<multArrayThread> threadArray = new ArrayList<multArrayThread>();
        for (int i = 0; i < firstArray.length; ++i)
        {
            ArrayList<Integer> row = new ArrayList<Integer>(Arrays.<Integer>asList(firstArray[i]));
            for (int j = 0; j < secondArray[0].length; ++j)
            {
                ArrayList<Integer> column = new ArrayList<Integer>();

                for (int k = 0; k < secondArray.length; ++k)
                {
                    column.add(secondArray[k][j]);
                }

                multArrayThread t = new multArrayThread(row, column, i, j);
                t.start();
                threadArray.add(t);
            }
        }

        int [][] result = new int[firstArray.length][firstArray.length];
        for (multArrayThread thread : threadArray)
        {
            try
            {
                thread.join();
                result[thread.rowIndex][thread.columnIndex] = thread.res;
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Result: ");
        for (int i = 0; i < result.length; ++i)
        {
            System.out.print("{");
            for (int j = 0; j < result.length; ++j)
            {
                System.out.print(" " + result[i][j] + " ");
            }
            System.out.print("}\n");
        }
    }

    public static void multiThreadFileRead()
    {

        fileReadThread firstThread = null;
        fileReadThread secondThread = null;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("text.txt"));// читаем файл
            Iterator<String> iterator = reader.lines().iterator();
            firstThread = new fileReadThread(iterator);
            secondThread = new fileReadThread(iterator);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        firstThread.start();
        secondThread.start();

        try
        {
            firstThread.join();
            secondThread.join();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args)
    {
        System.out.println("Сложение массива: ");
        multiThreadArraySum();

        System.out.println("Умножение матриц: ");
        multiThreadArrayMult();

        // Честно говоря, не очень понял, что от меня требуется на многопоточном чтении из файла
        // Из практики заметил что, какую бы архитектуру я бы не выбрал все потоки всё равно пробегутся по всем линиям
        // экономия выдастся только на записи в массив, но не на чтении
        // Потом нашел вот это https://stackoverflow.com/questions/18971951/multithreading-to-read-a-file-in-java
        // Сделал то на что интуитивно набрёл

        System.out.println("Чтение файла: ");
        multiThreadFileRead();

    }
}