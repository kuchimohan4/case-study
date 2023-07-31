package com.cropdeal.service;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.mail.mailsenderservice;
import com.cropdeal.rabbitmq.rabbitmqEmitter;
import com.cropdeal.repositry.BankAccountRepostry;
import com.cropdeal.repositry.addressRepositry;
import com.cropdeal.repositry.profileRepositry;

import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class profileserviceimplTest {

    @Mock
    private profileRepositry profileRepositry;

    @Mock
    private BankAccountRepostry bankAccountRepostry;

    @Mock
    private addressRepositry addressRepositry;

    @Mock
    private mailsenderservice mailsenderservice;

    @Mock
    private rabbitmqEmitter rabbitmqEmitter;

    @InjectMocks
    private profileserviceimpl profileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProfile() throws noProfileFoundException {
        // Arrange
        int userId = 1;
        profile profile = new profile();
        profile.setId(userId);
        address address = new address();
//        address.setId(userId);
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setId(userId);
        profile.setAddress(address);
        profile.setBankAccount(bankAccounts);
        Optional<profile> prOptional = Optional.empty();
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        // Mock the save method to return the same object
        when(addressRepositry.save(address)).thenReturn(address);
        when(bankAccountRepostry.save(bankAccounts)).thenReturn(bankAccounts);
        when(profileRepositry.save(profile)).thenReturn(profile);

        // Act
        profileService.addprofile(userId, profile);

        // Assert
        verify(addressRepositry, times(1)).save(address);
        verify(bankAccountRepostry, times(1)).save(bankAccounts);
        verify(profileRepositry, times(1)).save(profile);
    }



    @Test
    public void testAddProfile_ProfileAlreadyExists() {
        // Arrange
        int userId = 1;
        profile profile = new profile();
        profile.setId(userId);
        Optional<profile> prOptional = Optional.of(profile);
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        // Act and Assert
        assertThrows(noProfileFoundException.class, () -> profileService.addprofile(userId, profile));

    }

    @Test
    public void testUpdateProfile() throws noProfileFoundException {
        // Arrange
        int userId = 1;
        profile profile = new profile();
        profile.setId(userId);
        Optional<profile> prOptional = Optional.of(profile);
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        profileService.updateProfile(userId, profile);

        verify(profileRepositry, times(1)).save(profile);
    }

    @Test
    public void testUpdateProfile_ProfileNotFound() {
        int userId = 1;
        profile profile = new profile();
        profile.setId(userId);
        Optional<profile> prOptional = Optional.empty();
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        assertThrows(noProfileFoundException.class, () -> profileService.updateProfile(userId, profile));

    }

    @Test
    public void testGetProfileById() throws noProfileFoundException {
        int userId = 1;
        profile profile = new profile();
        profile.setId(2);
        Optional<profile> prOptional = Optional.of(profile);
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        assertEquals(2, profileService.getprofileById(userId).getId());
//        verify(profileRepositry, times(1)).findById(userId);
    }
    
    

    @Test
    public void testGetProfileById_ProfileNotFound() {
        int userId = 1;
        Optional<profile> prOptional = Optional.empty();
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        assertThrows(noProfileFoundException.class, () -> profileService.getprofileById(userId));

    }

    @Test
    public void testUpdateBankAccount() throws noProfileFoundException {
        // Arrange
        int userId = 1;
        profile profile = new profile();
        profile.setEmailId("test@example.com");
        profile.setName("John Doe");
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setAccountHolderName("John Doe");
        bankAccounts.setAccountNumber("1234567890");
        bankAccounts.setIfscCode("ABC1234567");
        Optional<profile> prOptional = Optional.of(profile);
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        // Act
        profileService.updateBankAccount(userId, bankAccounts);

        // Assert
        verify(bankAccountRepostry, times(1)).save(bankAccounts);
    }

    @Test
    public void testUpdateBankAccount_ProfileNotFound() {
        // Arrange
        int userId = 1;
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setAccountHolderName("John Doe");
        bankAccounts.setAccountNumber("1234567890");
        bankAccounts.setIfscCode("ABC1234567");
        Optional<profile> prOptional = Optional.empty();
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        assertThrows(noProfileFoundException.class, () -> profileService.updateBankAccount(userId, bankAccounts));

       
    }

    @Test
    public void testUpdateAddress() throws noProfileFoundException {
        int userId = 1;
        profile profile = new profile();
        profile.setEmailId("test@example.com");
        profile.setName("John Doe");
        address address = new address();
        address.setAddressLine1("123 Main St");
        address.setAddressLine2("Apt 4B");
        address.setCity("New York");
        address.setState("NY");
        address.setPinCode(12345);
        Optional<profile> prOptional = Optional.of(profile);
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        profileService.updateaddress(userId, address);

        verify(addressRepositry, times(1)).save(address);
    }

    @Test
    public void testUpdateAddress_ProfileNotFound() {
        int userId = 1;
        address address = new address();
        address.setAddressLine1("123 Main St");
        address.setAddressLine2("Apt 4B");
        address.setCity("New York");
        address.setState("NY");
        address.setPinCode(12345);
        Optional<profile> prOptional = Optional.empty();
        when(profileRepositry.findById(userId)).thenReturn(prOptional);

        assertThrows(noProfileFoundException.class, () -> profileService.updateaddress(userId, address));

        
    }
}
