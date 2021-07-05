package com.techelevator.tenmo.services;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

public class TransferService {

    private String BASE_URL;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();
    private ConsoleService console;

    public TransferService(AuthenticatedUser currentUser, String BASE_URL){
        this.currentUser = currentUser;
        this.BASE_URL = BASE_URL;
    }


    public Transfer createTransfer(Transfer transfer){
        //transfer = new Transfer()

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        //Not sure if we need have this token here vvv
        header.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity(transfer, header);

        transfer =  restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST, entity, Transfer.class).getBody();
        return  transfer;
    }

    private Transfer getATransferById(int id){
        Transfer transfer = null;
        String endpoint = BASE_URL + "transfers/" + id;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(header);

        try{
            transfer = restTemplate.exchange(endpoint, HttpMethod.GET, entity, Transfer.class).getBody();
        } catch (ResourceAccessException e){
            //How would I print to the console without printing directly?
            console.errorCannotConnect();
        } catch (RestClientResponseException e){
            //in examples we used console.PrintError()
            System.out.println(e.getRawStatusCode() + " " + e.getStatusText());
        }
        return transfer;
    }
}
