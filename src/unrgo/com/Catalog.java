package unrgo.com;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Catalog implements Serializable {
    private int id;
    private String name;
    private String producer;
    private int price;
    private int count;


    public Catalog( String name, String producer, int price, int count) {
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.count = count;
    }

    public Catalog() {

    }

    public String getName() {
        return name;
    }


    public String getProducer() {
        return producer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int price) {
        this.count = price;
    }


    @Override
    public String toString() {
        return name + " " + producer + ", price=" + price + " count " + count;
    }


    private Catalog generateGood() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите имя товара:");
        String goodName = sc.nextLine();
        System.out.println("Введите производителя товара:");
        String goodProducerName = sc.nextLine();
        System.out.println("Введите цену товара:");
        int goodPrice = sc.nextInt();
        System.out.println("Введите количесвто товара:");
        int goodCount = sc.nextInt();

        Catalog good = new Catalog(goodName, goodProducerName, goodPrice, goodCount);

        return good;


    }

    //добавить в каталог новый товар
    private void pushNewGood(ArrayList<Catalog> goods) {
        Catalog good = new Catalog();
        Catalog newGood = good.generateGood();
        goods.add(newGood);


    }

    public void deleteGood(ArrayList<Catalog> list) {
        int operation = -1;
        while (operation == -1) {
            operation = searchGood(list,1);

        }


        list.remove(operation);
    }


    //показать каталог
    public void showCatalog(ArrayList<Catalog> list) {
        System.out.println(list);

    }

    //сохранить каталог в базу
    public void saveCatalogToBase(ArrayList<Catalog> goods, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(goods);
            System.out.println("Данные обновлены!");
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

    }

    private ArrayList<Catalog> searchValueInList(String searchName, ArrayList<Catalog> list, String trueName, Catalog sCatalog,ArrayList<Catalog> foundGoods) {

        if (trueName.equals(searchName)) {
            foundGoods.add(sCatalog);
            System.out.println("Найдено: " + sCatalog.toString());
        }
        return foundGoods;
    }

    public void reduceCount(int countToReduce,Catalog ct) {
        int newCount = ct.getCount() - countToReduce;
        ct.setCount(newCount);
    }


    //получить каталог из базы
    public ArrayList<Catalog> getCatalogFromBase(String fname) {
        ArrayList<Catalog> p = new ArrayList<Catalog>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fname))) {

            p = ((ArrayList<Catalog>) ois.readObject());
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return p;

    }
    public int chooseMethodToSearch(){
        int chosenOperation = 0;
        System.out.println("Укажите критерий поиска :" + '\n' + "1- За назвою" + '\n' + "2-За виробником" + '\n');
        Scanner sc = new Scanner(System.in);
        try {
            chosenOperation = sc.nextInt();
        } catch (Exception ex) {
            System.out.println("Ошибка ввода!");
        }
        return chosenOperation;
    }

    public int searchGood(ArrayList<Catalog> list, int chosenOperation) {


        Scanner scan = new Scanner(System.in);
        int soughtIndex = -1;
        ArrayList<Catalog> foundGoods=new ArrayList<Catalog>();
        String searchName;
        String trueName;
        switch (chosenOperation) {
            case 1:
                System.out.println("Введите имя товара:");
                searchName = scan.nextLine();
                for (Catalog sCatalog : list) {
                    trueName = sCatalog.getName();
                    foundGoods= searchValueInList(searchName, list, trueName, sCatalog,foundGoods);


                }
                if(foundGoods.size()>1){
                    searchGood(foundGoods,1);
                }
                try {
                    soughtIndex = list.indexOf(foundGoods.get(0));
                }catch (IndexOutOfBoundsException ex){
                    System.out.println("Повторите ввод!");
                }

                break;
            case 2:
                System.out.println("Введите производителя товара:");
                searchName = scan.nextLine();
                for (Catalog sCatalog : list) {
                    trueName = sCatalog.getProducer();
                    foundGoods= searchValueInList(searchName, list, trueName, sCatalog,foundGoods);


                }
                if(foundGoods.size()>1){
                    System.out.println("Найдено несколько совпадений!");
                    searchGood(foundGoods,1);
                }
                try {
                    soughtIndex = list.indexOf(foundGoods.get(0));
                }catch (IndexOutOfBoundsException ex){
                    System.out.println("EXEPTION");
                }

                break;
            default:
                System.out.println("Неверный выбор!");
                soughtIndex = -1;
                break;
        }
        if (soughtIndex == -1) {
            System.out.println("Товара по вашему запросу не найдено");
            System.out.println("Весь доступный каталог:");
            System.out.println(list);

        }

        return soughtIndex;
    }


    //интерфейс администратора
    public void adminMode(ArrayList<Catalog> list, String fname, String orderBase, ArrayList<Order> orders) {
        int chosenOperation = 0;
        Order ord = new Order();
        int loop = 0;
        while (loop != 1) {
            System.out.println("Выберите действие :" + '\n' + "1-Добавить товар" + '\n' + "2-Удалить товар" + '\n' + "3-Найти товар" + '\n' + "4-Посмотреть каталог" + '\n' + "5-Посмотреть заказы" + '\n' + "6-Завершить работу");
            Scanner sc = new Scanner(System.in);
            try {
                chosenOperation = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Ошибка ввода!");
            }
            switch (chosenOperation) {
                case 1:
                    pushNewGood(list);
                    saveCatalogToBase(list, fname);
                    System.out.println("Добавлено!");
                    break;
                case 2:
                    deleteGood(list);
                    saveCatalogToBase(list, fname);
                    System.out.println("Удалено!");
                    break;
                case 3:
                    searchGood(list,chooseMethodToSearch());
                    break;
                case 4:
                    showCatalog(list);
                    break;
                case 5:
                    ord.showAllOrder(orders, orderBase);
                    break;

                case 6:

                    loop = 1;
                    break;
                default:
                    System.out.println("Выбрана неверная операция!");
                    break;
            }
        }
    }
}