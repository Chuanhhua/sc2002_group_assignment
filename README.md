# Restaurant Rush 

Welcome! This is the documentation for the responsible for the Restaurant Rush Group Assginment done by SC2002 SCEA Group 1.

GitHub Repository: https://github.com/Chuanhhua/sc2002_group_assignment

### Contributors
| Name | GitHub Username |
| --- | --- |
| Bryan Ng Xu Hen | ```bryannxh``` |
| Caleb Fong Tze Wei (Feng Zhiwei) | ```ccalebbf``` |
| Liu Chuanhua | ```Chuanhhua``` |
| Ng Xin Yi | ```xxyyng11``` / ```techforgood39``` |
| Tang Zong Nan | ```ztang017``` |

## How to build and Run
Needs Java 17 or newer. From the repository's top folder:

    javac -d out src/*.java
    java -cp out Game

Replay a recorded test run:

    java -cp out Game < tests/stage3-victory.txt

## The Three Stages
| Stage | Tag | What it adds | Test runs in that tag |
| --- | --- | --- | --- |
| 1 | ```stage-1``` | Regular Customers, Menu, Waiter, Chef, Cashier, Full Turns, Both Endings| ```tests/stage1-victory.txt```, ```tests/stage1-defeat.txt``` |
| 2 | ```stage-2``` | VIP and Critic Customers | ```tests/stage2-victory.txt```, ```tests/stage2-defeat.txt``` |
| 3 | ```stage-3``` | Host, Combo Meals, Happy Hours | ```tests/stage3-victory.txt```, ```tests/stage3-defeat.txt``` |

## Rules chosen by our Group
| Objects Involved | Rule |
| --- | --- |
| Seed | Dishes are picked with new Random(2002), such that the nth order is always the same dish. |
| ```CriticCustomer``` | Always orders the most expensive item on the menu. |
| ```ComboMeal``` | Insert Rule implemented by Zong Nan |
| ```Host``` | Acts before the waiter, such that the customer it seats can order within the same turn. |

## Stage 1 to Stage 2
Compare: https://github.com/Chuanhhua/sc2002_group_assignment/compare/stage-1...stage-2

### Additions
| File | Responsibility |
| --- | --- |
| ```src/VIPCustomer.java``` | A ```Customer``` that loses 5 satisfaction per waiting turn and pays 10% less (overrides ```getSatisfactionLossPerTurn()``` and ```calculateBill()```) |
| ```src/CriticCustomer.java``` | A ```Customer``` that loses 12 satisfaction per waiting turn and always orders the most expensive dish (overrides ```chooseMenuItem()```) |
| ```tests/stage2-victory.txt```, ```tests/stage2-defeat.txt``` | Reproducible Stage 2 runs. They replace the Stage 1 test files, which stay in the ```stage-1 tag```. |

### Modifications
| File | Change | Reason |
| --- | --- | --- |
| ```Customer.java``` | Added ```getTypeName()```, ```chooseMenuItem()```, ```calculateBill()``` | Each ```Customer``` type decides its own dish and bill, so ```GameController``` needs no type checks (Brief 4.2) |
| ```MenuItem.java``` | ```priceCents``` is now an ```int``` instead of a ```double``` | The VIP discount creates prices like $13.50; the brief asks for whole cents |
| ```Menu.java``` | ```addMenuItem()``` takes cents; new ```getMostExpensiveItem()``` | Money in cents; ```CriticCustomer``` ordering rule |
| ```Order.java``` | Stores ```priceCents```, fixed when the order is taken | The bill now depends on who ordered |
| ```GameController.java``` | ```VIPCustomer``` arrives on turn 4, ```CriticCustomer``` arrives on turn 7 (replaces two ```Customer``` arrivals); ```takeOrder()``` asks the customer for its dish and bill; revenue in cents | Brief 4.2 arrival rule; behaviour stays inside the customer objects |
| ```ConsoleUI.java``` | ```money()``` ; shows each customer's type, e.g. "Customer 2 (VIP)" | Stores money in cents and shown as dolllars; the player can see who is a ```VIPCustomer``` or ```CriticCustomer``` |

No source files were deleted.

### What still works
- The turn order, the staff rules, payment timing and the win and lose targets are unchanged.az
- Regular customers still lose 8 satisfaction per turn and pay full price: they use Customer's default methods.
- The ```stage-1``` tag still compiles and its test files still give VICTORY and DEFEAT.
- At ```stage-2```: ```tests/stage2-victory.txt``` gives VICTORY (5 paid, $70.50, average satisfaction 78.0); ```tests/stage2-defeat.txt``` gives DEFEAT.

### Effect on Design
What differs between customer types lives in subclasses that override three ```Customer``` methods. ```GameController``` calls the same methods for every customer, so a new customer type needs a new subclass and one arrival line, not changes to the turn logic.

## Stage 2 to Stage 3
Compare: https://github.com/Chuanhhua/sc2002_group_assignment/compare/stage-2...stage-3

### Additions
| File | Responsibility |
| --- | --- |
| ```src/ComboMeal.java``` | A ```MenuItem``` made of two ```MenuItem```, ```firstItem``` and ```secondItem```: 90% of their combined price, prep units added together |
| ```src/Host.java``` | Seats at most one queued customer per turn, on top of the Waiter |
| ```src/HappyHour.java``` | Once-per-game, two-turn policy: 20% off orders taken, +15 satisfaction for waiting customers |
| ```tests/stage3-victory.txt```, ```tests/stage3-defeat.txt``` | Reproducible Stage 3 runs |

### Modifications
| File | Change | Reason |
| --- | --- | --- |
| ```Menu.java``` | ```addCombo()```, ```findItem()``` | Build a combo from two existing items |
| ```Customer.java``` | ```recoverSatisfaction()``` | Happy Hour recovery, capped at 100 |
| ```GameController.java``` | 4 menu slots; ```Host``` phase before the Waiter; Happy Hour start and end of turn; Happy Hour price in ```takeOrder()``` | Hooks for the three features; turn rules unchanged |
| ```ConsoleUI.java``` | ```Host``` menu, ```HappyHour``` question and banner | Show the new choices |

### What still works
- ```VIPCustomer``` and ```CriticCustomer``` behaviour unchanged: <check in the run>.
- Brief examples: Salad + Pasta combo = $24.30, 3 units; VIP Burger in Happy Hour = $10.80.
- ```stage-1``` and ```stage-2``` tags still run their own test files.

### Effect on the design
```ComboMeal``` is a ```MenuItem```, so ```Order```, the ```Chef``` and the ```Cashier``` handle combos with no change.

## Video
Link: <video link>

| # | Segment | Timestamp | Presenter |
| --- | --- | --- | --- |
| 1 | The game launching from the submitted project | 00:00 | |
| 2 | Manager choices, including a decision where staff are limited | | |
| 3 | A dish going READY → SERVED → payment | | |
| 4 | A VIP or Critic behaviour | | |
| 5 | A Stage 3 feature (Host, combo or Happy Hour) | | |
| 6 | One victory and one defeat (the recorded test runs) | | |
| 7 | The stage tag comparisons and the diagrams | | |

## AI history
| Member | Branch |
| --- | --- |
| Bryan | https://github.com/Chuanhhua/sc2002_group_assignment/tree/<branch>/ai-history |
| Caleb | https://github.com/Chuanhhua/sc2002_group_assignment/tree/<branch>/ai-history |
| Chuanhua | https://github.com/Chuanhhua/sc2002_group_assignment/tree/<branch>/ai-history |
| Xin Yi | https://github.com/Chuanhhua/sc2002_group_assignment/tree/<branch>/ai-history |
| Zong Nan | https://github.com/Chuanhhua/sc2002_group_assignment/tree/<branch>/ai-history |
