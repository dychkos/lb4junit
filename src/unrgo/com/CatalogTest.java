package unrgo.com;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogTest {
    Catalog catalog;

    CatalogTest() {
    }

    @BeforeEach
    void createCatalog() {
        this.catalog = new Catalog("Iphone 7s", "Apple", 15000, 200);
    }

    @Test
    void getName() {
        Assertions.assertEquals("Iphone 7s", this.catalog.getName());
    }

    @Test
    void getProducer() {
        Assertions.assertEquals("Apple", this.catalog.getProducer());
    }

    @Test
    void getCount() {
        Assertions.assertEquals(200, this.catalog.getCount());
    }

    @Test
    void setCount() {
        this.catalog.setCount(150);
        Assertions.assertEquals(150, this.catalog.getCount());
    }

    @Test
    void deleteGood() {
        String str = "Iphone 7s";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        ArrayList<Catalog> list = new ArrayList();
        list.add(this.catalog);
        this.catalog.deleteGood(list);
        Assertions.assertEquals(0, list.size());
        System.setIn(System.in);
    }

    /*@Test
    void showCatalog() {
        ArrayList<Catalog> list = new ArrayList();
        list.add(this.catalog);
        String consoleOutput = null;
        PrintStream originalOut = System.out;
        String expectedStirng = "[Iphone 7s Apple, price=15000 count 200]\r\n";

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100);
            PrintStream capture = new PrintStream(outputStream);
            System.setOut(capture);
            this.catalog.showCatalog(list);
            capture.flush();
            consoleOutput = outputStream.toString();
            System.setOut(originalOut);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        Assertions.assertEquals(expectedStirng, consoleOutput);
    }

     */

    @Test
    void reduceCount() {
        this.catalog.reduceCount(10, this.catalog);
        Assertions.assertEquals(190, this.catalog.getCount());
    }
    @Test
    void getCatalogFromBase(){
        ArrayList<Catalog> p = null;
        this.catalog.deleteAllData();
        this.catalog.pushNewGoodInBase(this.catalog);
        p=this.catalog.getCatalogFromBase();


        Assertions.assertFalse(p.isEmpty());

    }
    @Test
    void deleteAllData(){
        this.catalog.deleteAllData();
        ArrayList<Catalog> list = this.catalog.getCatalogFromBase();
        Assertions.assertEquals(list.isEmpty(),true);
    }

    @Test
    void  deleteFromBase(){
        boolean result = false;
        ArrayList<Catalog> newCt=null;
        this.catalog.deleteAllData();
        this.catalog.pushNewGoodInBase(this.catalog);
        this.catalog.deleteFromBase(this.catalog);
        newCt=this.catalog.findInBase(this.catalog);


        Assertions.assertTrue(newCt.isEmpty());
    }
    @Test
    void findInBase(){
        ArrayList<Catalog> newCt=null;
        Catalog ct=null;
        try {
            DAO_Catalog dao = new DAO_Catalog();
            dao.insert(this.catalog);
            newCt=dao.find(this.catalog);
            ct = newCt.get(0);

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(this.catalog.toString(),ct.toString());
    }

    @Test
    void pushNewGoodInBase(){
        ArrayList<Catalog> newCt=null;
        Catalog ct=null;
        try {
            this.catalog.pushNewGoodInBase(this.catalog);
            newCt=this.catalog.findInBase(this.catalog);
            ct = newCt.get(0);

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(this.catalog.toString(),ct.toString());
    }
    /*@Test
    void getAndSaveCatalogFromBase() {
        ArrayList<Catalog> list = new ArrayList();
        list.add(this.catalog);
        DAO_Catalog dao_catalog = new DAO_Catalog();
        this.catalog.saveCatalogToBase(list, "catalog.dat");
        new ArrayList();
        ArrayList<Catalog> list2 = this.catalog.getCatalogFromBase();
        Assertions.assertEquals(list.toString(), list2.toString());
    }*/

    @Test
    void chooseMethodToSearch() {
        String str = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        int chosenMethod = this.catalog.chooseMethodToSearch();
        Assertions.assertEquals(chosenMethod, 1);
        System.setIn(System.in);
    }

    @Test
    void searchGoodByName() {
        String str = "Iphone 7s";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        ArrayList<Catalog> list = new ArrayList();
        list.add(this.catalog);
        int soughtIndex = this.catalog.searchGood(list, 1);
        Assertions.assertEquals(soughtIndex, 0);
        System.setIn(System.in);
    }

    @Test
    void searchGoodByProducerName() {
        String str = "Apple";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        ArrayList<Catalog> list = new ArrayList();
        new Catalog("AirPods", "Apple", 5600, 300);
        list.add(this.catalog);
        int soughtIndex = this.catalog.searchGood(list, 2);
        Assertions.assertEquals(soughtIndex, 0);
        System.setIn(System.in);
    }
}
