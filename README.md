

# İnterface implementation


`Customerşervicelnterface.java`

```java
package org.example;  
  
public interface CustomerServiceInterface  
{  
    // interface => soyut  
    void add();  
    void update();  
    void delete();  
}

```


-  `Customerşervicelnterface.java` interface ini kullanarak  iki farklı class oluşturduk ve içerisindeki methodları implement ettik

`CustomerService .java`

```java

package org.example;  
  
// class extends class  
// class implements interface  
public class CustomerService implements CustomerServiceInterface  
{  
    @Override  
    public void add() {  
        System.out.println("CustomerService.Add");  
    }  
  
    @Override  
    public void update() {  
  
    }  
  
    @Override  
    public void delete() {  
  
    }  
}  
// Çıplak class kalmasın

```

`CustomerManager.java`

```java
package org.example;  
  
public class CustomerManager implements CustomerServiceInterface  
{  
  
    @Override  
    public void add() {  
        System.out.println("CustomerManager.Add");  
    }  
  
    @Override  
    public void update() {  
  
    }  
  
    @Override  
    public void delete() {  
  
    }  
}
```


- böylece iki farklı class için de imzamız aynı oldu ve ilerleyen bir tarihte classda bir değişiklik yapmamız gerektiğinde veya aşağıdaki örnekteki gibi `CustomerManager.java` classından `CustomerService .java` class ına geçiş yapmamız gerektiğinde tek yapmamız gereken `CustomerService()` kullanımı yerine `CustomerManager()` olarak kullanabilirim.
- Tür yani `CustomerServiceInterface` kısmı  değişmediği takdirde tüm methodlarım aynı olduğu için direk değişim yapabilriz.
- Yani bir class a değil o class ın interface implementasyon yapması bu da o classın bizim dediklerimizi harfiyen yapması demek.

`Main.java`

```java

package org.example;  
  
public class Main  
{  
    public static void main(String[] args)  
    {  
        //Customer individualCustomer2 = new IndividualCustomer();  
        CustomerServiceInterface customerService = new CustomerService();  
        customerService.add();  
        customerService.update();  
        customerService.add();  
        customerService.add();  
        customerService.add();  
        customerService.add();  
    }  
}  
// 15:05

```





# Dependency İnjection



- programlamaya genelde `interfaceleri` yani imzamızı tanımlayarak başlarız.
- Burada ilk önce `dependencyinjection` paketi altında  `ProductService.java` dosyasında interface i aşağıdaki gibi oluşturduk


`ProductService.java`

```java

package org.example.dependencyinjection;  
  
public interface ProductService {  
    void add();  
    void update();  
}

```


- İmplementasyonu yaptığımız class için implementasyonu göstermek amacı ile `Productşervicelmpl.java` şeklinde isimlendirme yaparak bir dosya oluşturduk.

`Productşervicelmpl.java`

```java

package org.example.dependencyinjection;

public class ProductServiceImpl implements ProductService{

    @Override
    public void add() {
        // Repository nesnesi? // Bağımlılık
        System.out.println("Service işlemleri bitti, repository işlemleri başlıyor..");
        productRepository.addToDb();
    }

    @Override
    public void update() {
        productRepository.addToDb();
    }
}

```


- `Productşervicelmpl.java` da bağlı olduğumuz `Repository` nesnesi için de bir interface oluşturuyoruz


`ProductRepository.java`

```java

package org.example.dependencyinjection;  
  
public interface ProductRepository  
{  
    void addToDb();  
}
```


- Ve bu interface i implemente etmek için `MySqlProductRepository.java` dosyasında bir class oluşturduk

`MySqlProductRepository.jav`

```java

package org.example.dependencyinjection;  
  
public class MySqlProductRepository implements ProductRepository  
{  
  
    @Override  
    public void addToDb() {  
        System.out.println("Mysql veritabanına ürün ekleniyor..");  
    }  
}

```

- burada `ProductService.java` interface i  `ProductRepository.java` interface ine bağımlı

- burada sıkı bağımlılığı kırmak için  `MySqlProductRepository`  clasını `Productşervicelmpl.java` içerisinde `MySqlProductRepository`ı new lemekten kaçınmalıyız. Burada anlatmak istediğim sıkı bağımlılık,  `Productşervicelmpl.java` e eklenecek yeni bir method için tekrardan  `MySqlProductRepository` newlemek zorunda kalmak istememiz

![](ekler/Pasted%20image%2020240225031834.png)
- aynı zamanda  somut class olarak implemente edilmesi doğru değildir. soyut bir değişkenle implemente edilmesi bağımlılığı azaltır.


![](ekler/Pasted%20image%2020240225032032.png)
- bu şekilde soyut olarak tanımlanmasına **lose-coupling** denir
- her ne kadar bu yapı çalışacak olsa da biz bağımlılığı her zaman ilgili class ı kullandığımız noktada çözmek isteriz. Class içerisinde değil.
- Bunu yapmak için de `cunstructor` (CTOR) kullanabiliriz.
-  `cunstructor` ler `Classİsmi()` şeklinde çağrıldığında  class çalışmadan önce çalışır.
-
`ProductServiceImpl.java`

```java

package org.example.dependencyinjection;

public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;
    // CTOR
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void add() {
        // Repository nesnesi? // Bağımlılık
        System.out.println("Service işlemleri bitti, repository işlemleri başlıyor..");
        productRepository.addToDb();
    }

    @Override
    public void update() {
        productRepository.addToDb();
    }
}
```


`MongoDbProductRepository.java`

```java
package org.example.dependencyinjection;  
  
public class MongoDbProductRepository implements ProductRepository{  
    @Override  
    public void addToDb() {  
        System.out.println("Mongodb veritabanına ürün ekleniyor..");  
    }  
}

```

`Main.java`

```java

package org.example.dependencyinjection;  
  
  
public class Main {  
    public static void main(String[] args) {  
        // IoC  
        ProductRepository productRepository = new MongoDbProductRepository();  
        ProductRepository productRepository1 = new MySqlProductRepository();  
  
  
        // Loose-coupling  
        ProductService productService = new ProductServiceImpl(productRepository); // mongodb 
        productService.add();  
  
        ProductService productService1 = new ProductServiceImpl(productRepository1); // mysql   
        productService1.add();  
    }  
}

```

- **loose-coupling**





# Abstract Class

- soyut+somut

`Logger.java`

```java

package org.example.logging;  
  
public interface Logger {  
    void log(String message);  
}
 
```

`FileLogger.java`

```java
package org.example.logging;  
  
public class FileLogger implements Logger{  
    @Override  
    public void log(String message) {  
        System.out.println("Dosyaya loglandı:"+message);  
        // dosya aç, yaz, kaydet..  
    }  
}
```

`DatabaseLogger.java`

```java
package org.example.logging;  
  
public class DatabaseLogger implements Logger{  
    @Override  
    public void log(String message) {  
        System.out.println("Db'ye loglandı:"+message);  
    }  
}
```

- bağımlılık olduğu için yin **CTOR** içine eklememiz gerekiyor

`ProductServiceImpI.java`

```java

package org.example.dependencyinjection;  
   
import org.example.logging.Logger;  
  
public class ProductServiceImpl implements ProductService{  
    ProductRepository productRepository;  
    Logger logger;  
    // CTOR  
    public ProductServiceImpl(ProductRepository productRepository, Logger logger) {  
        this.productRepository = productRepository;  
        this.logger=logger;  
    }  
  
    @Override  
    public void add() {  
        // Repository nesnesi? // Bağımlılık  
        System.out.println("Service işlemleri bitti, repository işlemleri başlıyor..");  
        productRepository.addToDb();  
        logger.logMessage("Add işlemi loglandı");  
    }  
  
    @Override  
    public void update() {  
        productRepository.addToDb();  
    }  
}

```

- burada olduğu gibi daha önceki örnekteki gibi logger i **CTOR** ile birlikte *loose-coupling* yaptık

`Main.java`

```java

package org.example.dependencyinjection;  
  
import org.example.logging.DatabaseLogger;  
import org.example.logging.FileLogger;  
import org.example.logging.Logger;  
  
public class Main {  
    public static void main(String[] args) {  
        // IoC  
        ProductRepository productRepository = new MongoDbProductRepository();  
        ProductRepository productRepository1 = new MySqlProductRepository();  
  
        Logger fileLogger = new FileLogger();  
        Logger dbLogger = new DatabaseLogger();  
  
        // Loose-coupling  
        ProductService productService = new ProductServiceImpl(productRepository,fileLogger); // mongodb
        productService.add();  
  
        ProductService productService1 = new ProductServiceImpl(productRepository1, dbLogger); // mysql   
        productService1.add();  
    }  
}
```

- Siz bir class a sahip olup yazılmış olan interface ile elde edilen *loose-coupling* implementasyonlarıyla ( `FileLogger.java` ve `DatabaseLogger.java`)  her halükarda log işlemi yapmadan farklı somut işlemler gerçekleştirip sonrasında log işlemini hangi implementasyondan gerçekleşecekse onu gerçekleştirmesini isterseniz`abstract class` kullanmanız gerekir


`BaseLogger.java`
![](ekler/Pasted%20image%2020240225041125.png)



![](ekler/Pasted%20image%2020240225041159.png)
```java

package org.example.logging;  
  
// Hem soyut - hem somut işlevler içerebilen yapılardır.  
public abstract class BaseLogger implements Logger  
{  
    public void logMessage(String message)  
    {  
        System.out.println("Önce console'a loglandı:"+message);  
        log(message);  
    }  
}

```

- artık interfaceden implemente ettiğim logger classlarımı `BaseLogger` ile `extends` edebilirim


`FileLogger.java`

```java
package org.example.logging;  
  
public class FileLogger extends BaseLogger{  
    @Override  
    public void log(String message) {  
        System.out.println("Dosyaya loglandı:"+message);  
        // dosya aç, yaz, kaydet..  
    }  
}
```

`DatabaseLogger.java`

```java
package org.example.logging;  
  
public class DatabaseLogger extends BaseLogger{  
    @Override  
    public void log(String message) {  
        System.out.println("Db'ye loglandı:"+message);  
    }  
}
```


- Ve `main.java` ve `Productşervicelmpl.java` da soyutluluğu bozmadan `Logger` yerine `BaseLogger` dan alabilirim

`Productşervicelmpl.java`

```java

package org.example.dependencyinjection;  
  
import org.example.logging.BaseLogger;  
import org.example.logging.Logger;  
  
public class ProductServiceImpl implements ProductService{  
    ProductRepository productRepository;  
    BaseLogger logger;  
    // CTOR  
    public ProductServiceImpl(ProductRepository productRepository, BaseLogger logger) {  
        this.productRepository = productRepository;  
        this.logger=logger;  
    }  
  
    @Override  
    public void add() {  
        // Repository nesnesi? // Bağımlılık  
        System.out.println("Service işlemleri bitti, repository işlemleri başlıyor..");  
        productRepository.addToDb();  
        logger.logMessage("Add işlemi loglandı");  
    }  
  
    @Override  
    public void update() {  
        productRepository.addToDb();  
    }  
}
```

`Main.java`

```java
package org.example.dependencyinjection;  
  
import org.example.logging.BaseLogger;  
import org.example.logging.DatabaseLogger;  
import org.example.logging.FileLogger;  
import org.example.logging.Logger;  
  
public class Main {  
    public static void main(String[] args) {  
        // IoC  
        ProductRepository productRepository = new MongoDbProductRepository();  
        ProductRepository productRepository1 = new MySqlProductRepository();  
  
        BaseLogger fileLogger = new FileLogger();  
        BaseLogger dbLogger = new DatabaseLogger();  
  
        // Loose-coupling  
        ProductService productService = new ProductServiceImpl(productRepository,fileLogger); // mysql  
        productService.add();  
  
        ProductService productService1 = new ProductServiceImpl(productRepository1, dbLogger); // mongodb  
        productService1.add();  
    }  
}

```

