package com.code.camping.controller;

import com.code.camping.entity.Wallet;
import com.code.camping.service.WalletService;
import com.code.camping.utils.dto.request.WalletRequest;
import com.code.camping.utils.dto.response.WalletResponse;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService wallet_service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody WalletRequest request) {
        WalletResponse response = WalletResponse.fromWallet(wallet_service.create(request));
        return Res.renderJson(response, "Wallet Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> index(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute WalletRequest request
    ) {
        PageResponse<Wallet> res = new PageResponse<>(wallet_service.getAll(page, request));
        return Res.renderJson(res, "ok", HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        return Res.renderJson(WalletResponse.fromWallet(wallet_service.getById(id)), "Wallet ID Retrieved Successfully", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody WalletRequest request) {
        WalletResponse response = WalletResponse.fromWallet(wallet_service.update(request));
        return Res.renderJson(response, "Wallet Update Successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            wallet_service.delete(id);
            return Res.renderJson(null, "Wallet Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return Res.renderJson(null, "Failed to Delete Wallet", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
