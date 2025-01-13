package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account){
        //check for null username
        if(account.getUsername() == null || account.getUsername().trim().isEmpty()){
            System.out.println("Validation failed: Username is null or blank");
            return null;
        } 
        //check pw length
        if(account.getPassword().length() < 4){
            System.out.println("Validation failed: Password is too short");
            return null;
        }

        //check if account exists
        Account existingAccount = accountDAO.getAccountByUserName(account.getUsername());
        if(existingAccount != null){
            System.out.println("Validation failed: Username already exists");
            return  null;
        } 

        Account createdAccount = accountDAO.createAccount(account);
            if (createdAccount == null) {
        System.out.println("DAO failed to create account");
    }
    return createdAccount;
    }


    

    public Account login(String username, String password){
        if(username == null || password == null) return null;
        Account account = accountDAO.login(username,password);
        if(account == null){
            return null;
        }
        return account;

    }

}
