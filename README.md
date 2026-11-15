# Restaurant Rush 

Welcome! This is the documentation for the Restaurant Rush Group Assignment done by SC2002 SCEA Group 1.

GitHub Repository: https://github.com/Chuanhhua/sc2002_group_assignment

### Contributors
| Name | GitHub Username |
| --- | --- |
| Bryan Ng Xu Hen | ```bryannxh``` |
| Caleb Fong Tze Wei (Feng Zhiwei) | ```ccalebbf``` |
| Liu Chuanhua | ```Chuanhhua``` |
| Ng Xin Yi | ```xxyyng11-create``` / ```techforgood39``` |
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
| ```ComboMeal``` | Salad + Pasta, built from two items already on the menu so its price follows theirs: 90% of their combined price ($24.30) and their prep units added (3). The Critic always orders it as the most expensive item. |
| ```Host``` | Acts before the waiter, such that the customer it seats can order within the same turn. |
| ```HappyHour``` | Asked at the start of a turn, before the Host and Waiter; usable once per game and lasts two turns. The VIP's 10% is applied first, then Happy Hour's 20%, and the price is fixed in the ```Order``` when it is taken. |
| Money | All prices are ```int``` cents; both discounts round to the nearest cent. |

## Stage 1 to Stage 2
Compare: https://github.com/Chuanhhua/sc2002_group_assignment/compare/stage-1...stage-2

### Additions
| File | Responsibility |
| --- | --- |
| ```src/VIPCustomer.java``` | A ```Customer``` that loses 5 satisfaction per waiting turn and pays 10% less (overrides ```getSatisfactionLossPerTurn()``` and ```calculateBill()```) |
| ```src/CriticCustomer.java``` | A ```Customer``` that loses 12 satisfaction per waiting turn and always orders the most expensive dish (overrides ```chooseMenuItem()```) |
| ```tests/stage2-victory.txt```, ```tests/stage2-defeat.txt``` | Reproducible Stage 2 runs. Each test file only replays at its own tag. |

### Modifications
| File | Change | Reason |
| --- | --- | --- |
| ```Customer.java``` | Added ```getTypeName()```, ```chooseMenuItem()```, ```calculateBill()``` | Each ```Customer``` type decides its own dish and bill, so ```GameController``` needs no type checks (Brief 4.2) |
| ```MenuItem.java``` | ```price``` (a ```double```) became ```priceCents``` (an ```int```) | The VIP discount creates prices like $13.50; the brief asks for whole cents |
| ```Menu.java``` | ```addMenuItem()``` takes cents; new ```getMostExpensiveItem()``` | Money in cents; ```CriticCustomer``` ordering rule |
| ```Order.java``` | Stores ```priceCents```, fixed when the order is taken | The bill now depends on who ordered |
| ```GameController.java``` | ```VIPCustomer``` arrives on turn 4, ```CriticCustomer``` arrives on turn 7 (replaces two ```Customer``` arrivals); ```takeOrder()``` asks the customer for its dish and bill; revenue in cents | Brief 4.2 arrival rule; behaviour stays inside the customer objects |
| ```ConsoleUI.java``` | ```money()``` ; shows each customer's type, e.g. "Customer 2 (VIP)" | Stores money in cents and shown as dollars; the player can see who is a ```VIPCustomer``` or ```CriticCustomer``` |

No source files were deleted.

### What still works
- The turn order, the staff rules, payment timing and the win and lose targets are unchanged.
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
| ```Menu.java``` | ```addComboMeal(first, second)``` | Build a combo from two existing items |
| ```Customer.java``` | ```recoverSatisfaction()``` | Happy Hour recovery, capped at 100 |
| ```GameController.java``` | 4 menu slots; ```Host``` phase (```runHostPhase()```) before the Waiter; Happy Hour start (```runHappyHourStart()```) and end of turn (```happyHour.endTurn()```); Happy Hour price in ```takeOrder()``` (```happyHour.applyTo(...)```) | Hooks for the three features; turn rules unchanged |
| ```ConsoleUI.java``` | ```Host``` menu (```askHostToSeat()```), ```HappyHour``` question (```askIfWantToStartHappyHour()```) and banner (```showHappyHourStatus()```) | Show the new choices |

No source files were deleted. ```Order```, ```Table```, ```Task```, ```CriticCustomer``` and ```Game``` did not change (5 of the 11 Stage 2 classes).

### What still works
- ```VIPCustomer``` and ```CriticCustomer``` behaviour unchanged.
- Brief examples: Salad + Pasta combo = $24.30, 3 units; VIP Burger in Happy Hour = $10.80.
- ```stage-1``` and ```stage-2``` tags still run their own test files.
- At ```stage-3```: ```tests/stage3-victory.txt``` VICTORY with 5 paid, $78.78, average 84.8; ```tests/stage3-defeat.txt``` gives DEFEAT.

### Effect on the design
```ComboMeal``` is a ```MenuItem```, so ```Order```, the ```Chef``` and the ```Cashier``` handle combos with no change.

## Video

[Watch our SC2002 Restaurant Rush Presentation](https://entuedu-my.sharepoint.com/:v:/g/personal/ng0024yi_e_ntu_edu_sg/IQAdOUOIDaz2RJtLhCB9FXewAc-wEtcb6QmNF7Ss3dYhgHc?e=e8Gb4w)

| Timestamp | Content | Speaker |
| --- | --- | --- |
| 00:00–01:45 | Introduction and game overview | Chuanhua |
| 01:45–02:55 | Stage 1 gameplay | Bryan |
| 03:00–03:45 | Stage 2 VIP and Critic | Caleb |
| 03:45–06:16 | Stage 3 Host, ComboMeal, Happy Hour | Bryan |
| 06:16–07:20 | Stage 1 class diagram | Chuanhua |
| 07:20–08:35 | Stage 2 class diagram | Caleb |
| 08:35–09:36 | Stage 3 class diagram | Zong Nan |
| 09:36–10:50 | Design changes and GitHub evidence | Xin Yi |
| 10:50–14:04 | Sequence diagram: VIP during Happy Hour | Zong Nan and Xin Yi |
| 14:04–16:15 | Individual contributions and reflections | All five members |

## AI history
| Member | Branch |
| --- | --- |
| Bryan | [https://github.com/Chuanhhua/sc2002_group_assignment/tree/stage3_combomeal/ai-history](https://github.com/Chuanhhua/sc2002_group_assignment/tree/stage3_combomeal/ai-history)|
| Caleb | [https://github.com/Chuanhhua/sc2002_group_assignment/tree/caleb/stage2/ai-history](https://github.com/Chuanhhua/sc2002_group_assignment/tree/caleb/stage2/ai-history) |
| Chuanhua | [https://github.com/Chuanhhua/sc2002_group_assignment/tree/chuan/feat/finalize-stage-1/ai-history](https://github.com/Chuanhhua/sc2002_group_assignment/tree/chuan/feat/finalize-stage-1/ai-history) |
| Xin Yi | [https://github.com/Chuanhhua/sc2002_group_assignment/blob/xinyi/ai-history/ai-history/AI_HISTORY_XINYI.pdf](https://github.com/Chuanhhua/sc2002_group_assignment/blob/xinyi/ai-history/ai-history/AI_HISTORY_XINYI.pdf) |
| Zong Nan | [https://github.com/Chuanhhua/sc2002_group_assignment/tree/zongnan/dev/ai-history/stage-3](https://github.com/Chuanhhua/sc2002_group_assignment/tree/zongnan/dev/ai-history/stage-3) |
