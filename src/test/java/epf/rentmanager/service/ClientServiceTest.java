package epf.rentmanager.service;

import com.epf.rentmanager.model.Client;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

public class ClientServiceTest {

    @Test
    void isCreate(){
        //When
        Client client = new Client(1,"Collet", "Thibault","tibo@gmail.com", LocalDate.now());
        
        //Then

    }

}
