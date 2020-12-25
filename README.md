# Movie-Rental-Kiosk
Code in Java for a Movie Rental Kiosk that uses files that have information about movies such as the name of the movie, how many copies are available and how many are not
Two files are needed, an inventory file that has all the movies that are available in the kiosk and a transaction file that has all of the transactions.

Inventory file looks like this(sample):

"Passengers",0,0

"Storks",1,0

"Patriots Day",5,0

"Fences",4,0

"Gaurdians of the Galaxy",6,0

"Collateral Beauty",2,0

"The Secret Life of Pets",3,0

Where it is written in the order of movie name, number of available copies, number of rented or unavailable copies and no spaces between each line and a comma used to seperate the movie name, available number and the unavailable number

The transaction file looks like this(sample):

 "add "War on Everyone"3"
 
rent Tro"lls"

return "Guardians of the Galaxy",4

remove "Storks",

addd "Shut In",2

retn "Moana"

q=[p? SDGHJKLKH,34567

Where a transaction can only happen if the keywords "add", "rent", "return", and "remove" are written first without a quotation or parenthesis followed by the movie name and the number you would like to rent, return, add or remove and no space between each line and a comma used to seperate the number and movie name
