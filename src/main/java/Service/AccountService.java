package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if(account.getPassword().length() < 4)
            return null;
        else if(("").equals(account.getUsername()))
            return null;
        else if(accountDAO.getAccountbyUsername(account.getUsername()) != null)
            return null;
        else{
            Account newAccount = accountDAO.insertAccount(account);
            return newAccount;
        }
    }

    public Account accountLogin(Account account){
        if(("").equals(account.getUsername()) || ("").equals(account.getPassword()))
            return null;
        Account confAccount = accountDAO.getAccountbyUsername(account.getUsername());
        if(confAccount == null)
            return null;
        else if(confAccount.getPassword().equals(account.getPassword()))
            return confAccount;
        else
            return null;
    }
}