package unrgo.com;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {
    Order order;
    Catalog catalog;

    OrderTest() {
    }

    @BeforeEach
    void createTools() {
        this.catalog = new Catalog("Iphone 7s", "Apple", 15000, 200);
        this.order = new Order("Sergey Dychko", "380950830581", this.catalog);
    }

    @Test
    void getChosenCatalog() {
        ArrayList<Catalog> catalogsExpected = new ArrayList();
        catalogsExpected.add(this.catalog);
        ArrayList<Catalog> catalogsReal = this.order.getChosenCatalog();
        Assertions.assertEquals(catalogsExpected, catalogsReal);
    }

    @Test
    void setName() {
        this.order.setName("Elon Musk");
        Assertions.assertEquals("Elon Musk", this.order.getName());
    }

    @Test
    void setPhone() {
        this.order.setPhone("38067216732");
        Assertions.assertEquals("38067216732", this.order.getPhone());
    }

    @Test
    void editName() {
        String str = "Daria Example";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        this.order.editName();
        Assertions.assertEquals(str, this.order.getName());
        System.setIn(System.in);
    }

    @Test
    void editPhone() {
        String str = "380669857128";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        this.order.editPhone();
        Assertions.assertEquals(str, this.order.getPhone());
        System.setIn(System.in);
    }

    @Test
    void chooseMethodToEditGood() {
        String str = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        int realResult = this.order.chooseMethodToEditGood();
        Assertions.assertEquals(1, realResult);
        System.setIn(System.in);
    }

    @Test
    void deleteChosenGoods() {
        Catalog ct2 = new Catalog("Redmi AirDots", "Xioami", 900, 1500);
        ArrayList<Catalog> catalogList = new ArrayList();
        catalogList.add(this.catalog);
        catalogList.add(ct2);
        ArrayList<Catalog> catalogActual = new ArrayList();
        catalogActual.add(this.catalog);
        String str = "Iphone 7s";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        this.order.editChosenGoods(catalogList, catalogActual, this.catalog, 1);
        Assertions.assertEquals(0, catalogActual.size());
    }

    @Test
    void addToChosenGoods() {
        Catalog ct2 = new Catalog("Redmi AirDots", "Xiaomi", 900, 1500);
        ArrayList<Catalog> catalogList = new ArrayList();
        catalogList.add(this.catalog);
        catalogList.add(ct2);
        ArrayList<Catalog> catalogActual = new ArrayList();
        catalogActual.add(this.catalog);
        String str = "Redmi AirDots";
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        this.order.editChosenGoods(catalogList, catalogActual, this.catalog, 2);
        Assertions.assertEquals("Redmi AirDots Xiaomi, price=900 count 1500", ((Catalog)catalogActual.get(1)).toString());
    }

    @Test
    void isEmpty() {
        Order ord = new Order();
        Assertions.assertEquals(true, ord.isEmpty());
    }

    @Test
    void showMyOrder() {
        String expectedStirng = "Name: Sergey Dychko phone: 380950830581 chosen goods: [Iphone 7s Apple, price=15000 count 200]";
        Assertions.assertEquals(expectedStirng, this.order.showMyOrder(this.order));
    }

    @Test
    void createOrder() {
        String expectedStirng = "Name: Sergey Dychko phone: 380950830581 chosen goods: [Iphone 7s Apple, price=15000 count 200]";
        String str = "Sergey Dychko\n\r +380950839581\n\r1\n\r";
        ArrayList<Catalog> list = new ArrayList();
        list.add(this.catalog);
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
        this.order.createOrder(list,1);
        Assertions.assertEquals(expectedStirng, this.order.showMyOrder(this.order));
    }

    @Test
    void clientMode() {
    }
}
