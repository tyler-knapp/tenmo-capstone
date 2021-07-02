package com.techelevator.tenmo.services;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private String BASE_URL;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(AuthenticatedUser currentUser, String BASE_URL){
        this.currentUser = currentUser;
        this.BASE_URL = BASE_URL;
    }

    public Transfer updatingTransfer(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(header);

        Transfer transfer = restTemplate.exchange(BASE_URL + "transactions", HttpMethod.PUT, entity, Transfer.class).getBody();
        return  transfer;
    }
}
