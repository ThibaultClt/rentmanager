package epf.rentmanager.util;

import com.epf.rentmanager.model.Client;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClientTest {

    @Test
    void isLegal_should_return_true_when_age_is_over_18() {
        // Given
        Client legalUser = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2010,01,01));
        // Then
        assertTrue(Client.isLegal(legalUser));
    }

    @Test
    void isLegal_should_return_false_when_age_is_under_18() {
        // Given
        Client illegalUser = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2010,01,01));
        // Then
        assertFalse(Client.isLegal(illegalUser));
    }

    @Test
    void isNameLongEnough_should_return_true_when_name_is_long() {
        // Given
        Client legalUser = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2010,01,01));
        // Then
        assertTrue(Client.isNameLongEnough(legalUser));
    }

    @Test
    void isNameLongEnough_should_return_false_when_age_is_not_long() {
        // Given
        Client illegalUser = new Client("J", "D", "john.doe@ensta.fr", LocalDate.of(2010,01,01));
        // Then
        assertFalse(Client.isNameLongEnough(illegalUser));
    }





}
