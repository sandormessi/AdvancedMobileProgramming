package com.beadando.petshop;

import com.beadando.petshop.Model.Account;

import java.util.List;

public interface AccountListProvider
{
    void  ProvideAccountList(List<Account> accounts);
}
