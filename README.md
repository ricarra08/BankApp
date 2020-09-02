# BankApp
Hello Everyone!!!
This is a draft ReadMe.md
--IMPORTANT--

Employee Username:Big Mama

Employee Password:BigMa

BankAdmin Username:Big Daddy

BankAdmin Password:BigPapa

In my Bank Application, A user can apply/register for a Bank Account and then a Bank Admin (or Employee)
can either deny or approve there application. 
If the application is approved then the user will have access to their own bank account with an intial funds of $10.
The Bank Account holder can use their registration info to login to their bank account and make multiple transactions.
In additon, they can create other bank accounts under own respective user. They can also logout from their sessions

The Bank Admin can view accounts from both serialized data and database. He may also create and or delete bank accounts.
They can also make transactions on behalf of the account holder as well.

BackEnd info:
I utilized a if/else-if tree no manuver around users and functionalities. I utilized sequences to insert new rows/bank account holder
info into the Bank Account database. A user table is created so as to input registration info.
I provided Junit testing for my methods and utilized a dao design pattern.
The main attraction of my application is the use of serialized data and database data that is updated equally.
If internet goes down a user can still make transactions as the info is serialized and once the internet is back it will update
automatically.
