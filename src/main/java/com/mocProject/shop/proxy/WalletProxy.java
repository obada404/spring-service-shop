package com.mocProject.shop.proxy;



import com.mocProject.shop.DTO.TransactionDTO;
import com.mocProject.shop.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet")
public interface WalletProxy {

    @GetMapping("/decrease-balance")
    String retrieveDecreaseBalance();


    @GetMapping("/users/{userId}")
    UserDTO retrieveUser(@PathVariable int userId);


    @PostMapping("wallet/wallets/{userId}/transactions")
    void addTransaction(@PathVariable int userId, @RequestBody TransactionDTO transaction);

}
