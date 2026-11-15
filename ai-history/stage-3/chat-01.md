# Gemini chat 01, Stage 3
I am about to start on my Group Project and I am in charge of Stage 3, HappyHour and Host as well as testing. Analyse the materials I have attached and give me an overview of what I need to achieve.

You are stepping into Stage 3 of Restaurant Rush, extending the saved Stage 2 codebase (stage-2) to build the stage-3 deliverable. Because your uploaded repository archive (sc2002_group_assignment-e947973273a77d7556874cd2ff186824d8f1f13e) is a compressed binary ZIP rather than extracted plain-text .java files, I can see the exact file tree in your repository—including ComboMeal.java, HappyHour.java, Host.java, stage2-victory.txt, and stage2-defeat.txt—though I cannot read the raw lines inside those zipped files directly.   
SC2002_Group_Project_Briefing.pdf
+ 2

Working in Tutorial/Socratic Mode as your SC2002 tutor, here is the structured breakdown of what you need to achieve based on the project brief and your repository layout, followed by a quick check-in to inspect your current code.

1. Stage 3 Core Requirements & Your Responsibilities
   In Stage 3 (New role and menu policy), you are extending Stage 2 without breaking existing Stage 1 or Stage 2 functionality.   
   SC2002_Group_Project_Briefing.pdf
+ 1

Host (Host.java):   
chuanhhua/sc2002_group_assignment

Introduces a new staff role in Stage 3 alongside Waiter, Chef, and Cashier.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Depending on your group's design, Host likely inherits from your staff abstraction (or implements a staff/seating interface) to offload seating responsibilities from the Waiter—allow the manager to assign seating priority while freeing the Waiter to focus on ordering and serving.

Happy Hour (HappyHour.java):   
chuanhhua/sc2002_group_assignment

Activation & Duration: A pricing and satisfaction policy the manager can activate once per game at the start of a turn, lasting for that turn and the next (2 turns total). The UI must display its remaining duration.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Pricing Rule: Orders taken while Happy Hour is active receive 20% off their bill. If a VIP customer orders during Happy Hour, apply the VIP discount first, then Happy Hour (e.g.,
$
15
×
0.9
×
0.8
=
$
10.80
$15×0.9×0.8=$10.80). The price is locked when the order is taken and does not affect orders already placed.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 2

Satisfaction Recovery: During each active Happy Hour turn, customers still waiting for a table or food recover 15 satisfaction at turn start (capped at a maximum of 100), excluding customers who have already left.   
SC2002_Restaurant_Rush_Group_Assignment.docx

ComboMeal Integration (ComboMeal.java):   
chuanhhua/sc2002_group_assignment

Even though a teammate may own ComboMeal, your Happy Hour pricing and testing must account for it: customers choose a combo according to a rule your group documents, and Happy Hour's 20% discount applies to orders taken while active.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Testing & Verification (tests/):   
chuanhhua/sc2002_group_assignment

Your tests/ folder already contains stage2-victory.txt and stage2-defeat.txt.   
chuanhhua/sc2002_group_assignment

You need to ensure Stage 3 is independently playable under the base setting (18 turns max, 2 tables, arrivals at turns 1, 4, 7, 10, 13, 16; victory requires
≥
4
≥4 paid customers, revenue
≥
$
45
≥$45, and average satisfaction
≥
60
≥60).   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Create reproducible test runs (e.g., stage3-victory.txt and stage3-defeat.txt) verifying Stage 1 & 2 regression plus Stage 3 edge cases (VIP + Happy Hour stacking, satisfaction cap at 100, Host seating, and single-use Happy Hour lock).   
SC2002_Group_Project_Briefing.pdf
+ 1

2. Documentation, Git & Presentation Deliverables
   Git Tags & Changelog: Commit and tag the completed version as stage-3 in GitHub. Update README.md with the stage-2
   →
   → stage-3 comparison (listing new files like Host.java, HappyHour.java, ComboMeal.java, and modified files with reasons).   
   SC2002_Group_Project_Briefing.pdf
+ 4

AI History: Save all your project-related AI prompts and replies in order inside your personal branch under ai-history/stage-3/.   
SC2002_Group_Project_Briefing.pdf

UML & Report: Ensure the Stage 3 UML Class Diagram matches your final Stage 3 code, consider using a Stage 3 scenario (such as Happy Hour activation + VIP order + Host seating) for the Sequence Diagram, and write your individual reflection.   
SC2002_Group_Project_Briefing.pdf
+ 4

Recorded Video: Cover Segment 5 ("A Stage 3 feature") and your testing/stage comparisons in the 15-minute group video.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Socratic Check-In: Inspecting Your Current Starter Code
I notice Host.java, HappyHour.java, and ComboMeal.java are already present in your src/ folder. Before we design or refine their logic:   
chuanhhua/sc2002_group_assignment

What is currently inside Host.java and HappyHour.java? (Are they empty skeletons right now, or has someone already written partial attributes/methods in them?)

Thinking about OOP & SOLID (specifically SRP and OCP): How does your Order or Customer class currently calculate the price of an order in Stage 2, and where do you think the HappyHour discount check should live so you don't end up with messy if/else chains inside GameController?   
chuanhhua/sc2002_group_assignment
+ 1

(Paste the contents of Host.java, HappyHour.java, and Order.java if you'd like us to review them together!)

How do i clone the repository, and merge and pull requests after I am done with each step?

To collaborate on your SC2002 group repository (chuanhhua/sc2002_group_assignment) while keeping each member's personal branch, AI history (ai-history/stage-X/), and runnable stage tags (stage-1, stage-2, stage-3) clean, follow this Git and GitHub workflow.   
SC2002_Group_Project_Briefing.pdf
+ 1

1. Clone the Repository & Create Your Personal Branch
   Copy the repository URL from GitHub ([https://github.com/chuanhhua/sc2002_group_assignment.git](https://github.com/chuanhhua/sc2002_group_assignment.git)).

Open your terminal (or Git Bash / VS Code terminal), navigate to your coursework folder, and clone it:

Bash
git clone https://github.com/chuanhhua/sc2002_group_assignment.git
cd sc2002_group_assignment
Verification: Running git status shows you are on main (or your default branch) with a clean working tree.

Create and switch to your personal branch (do not commit directly to main so your personal branch holds your ai-history/ and work-in-progress safely):   
SC2002_Group_Project_Briefing.pdf

Bash
git checkout -b <your-name>-stage3
Verification: Running git branch displays * <your-name>-stage3 highlighted in green.

2. Commit & Push Your Work After Each Feature/Step
   After finishing a discrete unit of work (e.g., implementing Host.java, adding HappyHour.java, or updating tests/):

Pull any recent group changes from main into your branch first to avoid merge conflicts later:

Bash
git fetch origin
git merge origin/main
Verification: Git outputs Already up to date. or cleanly merges teammates' latest updates without conflict markers (<<<<<<<).

Stage and commit your changes (including your updated ai-history/stage-3/ markdown logs):   
SC2002_Group_Project_Briefing.pdf

Bash
git add .
git commit -m "Implement HappyHour discount and satisfaction recovery"
Verification: Running git log -1 shows your new commit message and hash at the top.

Push your branch to GitHub:

Bash
git push -u origin <your-name>-stage3
Verification: Refreshing your repository page on GitHub shows a yellow banner with Compare & pull request for your branch.

3. Open & Merge a Pull Request (PR) on GitHub
   Create the PR: On GitHub, click Compare & pull request (or go to Pull requests - New pull request), set base: main and compare: <your-name>-stage3, write a brief summary of what you added/modified (which also helps build your changelog), and click Create pull request.   
   SC2002_Group_Project_Briefing.pdf

Resolve any conflicts & merge: Once your teammates review or verify that the project compiles and passes tests, click Merge pull request - Confirm merge. (Important: Do not delete your personal branch after merging, because the submission rubric requires your personal branch with ai-history/ to remain on GitHub.)   
SC2002_Group_Project_Briefing.pdf

Sync your local main and tag the completed stage: Once all Stage 3 features are merged into main and verified as a complete runnable version:   
SC2002_Group_Project_Briefing.pdf
+ 1

Bash
git checkout main
git pull origin main
git tag -a stage-3 -m "Stage 3: Host, ComboMeal, and Happy Hour"
git push origin stage-3
Verification: On GitHub under Releases / Tags, stage-3 appears alongside stage-1 and stage-2.   
SC2002_Group_Project_Briefing.pdf
+ 1

Want a quick checklist of what to verify before tagging stage-3?

Yes
Lets look at HappyHour, for the price after 20% off, how do i make sure that the VIP's 10% come first, and that it is round half up to the whole cent?

Since we are working on your group assignment, let's tackle this in Tutorial/Socratic Mode—instead of handing you finished code, let's break down the two parts of your question (the discount ordering and the rounding) so you can design a clean OOP solution yourself.

1. Enforcing the Discount Order (VIP First, Then Happy Hour)
   The project specification states that when Happy Hour is active, orders taken receive 20% off, and you must apply the VIP discount first, then Happy Hour, locking the price when the order is taken. Notice also that in Stage 2, customer-specific billing must be dispatched through customer objects rather than a chain of type checks (instanceof) in the controller.   
   SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Let's look at how your classes interact when the Waiter takes an order (similar to the Order and Customer delegation pattern in your lecture notes):   
21S1_CE2002_PPT_Chapter7 UML-SequenceDiagram_V1.0.pptx

Guiding Question 1 (OOP & Polymorphism): In your current Stage 2 code, where is the VIP 10% discount calculated—inside VIPCustomer (e.g., an overridden method on Customer), or inside Order? If Order asks the Customer object to compute the post-customer-discount subtotal first, and then passes that result to HappyHour (if active), what happens automatically for Regular, Critic, and VIP customers without needing any if (customer instanceof VIPCustomer) checks?

2. Rounding Half-Up to the Whole Cent
   In Java, standard floating-point arithmetic (double) can introduce tiny binary representation errors (e.g., 10.804999999999998), and Math.round(double) rounds to the nearest whole integer (long), not two decimal places.

To round accurately to the nearest whole cent using round half up, Java gives you two standard approaches:

BigDecimal (in java.math.BigDecimal and java.math.RoundingMode): Converting the value (or using BigDecimal.valueOf(...)) and calling .setScale(2, RoundingMode.HALF_UP).

Scaling with Math.round: Multiplying by 100.0, calling Math.round(...), and dividing back by 100.0.

Guiding Question 2 (Precision & Edge Cases): Suppose a VIP customer orders a ComboMeal during Happy Hour, and the intermediate VIP price has a half-cent (for example,
$
24.30
×
0.9
=
$
21.87
$24.30×0.9=$21.87, or imagine an item where the VIP step produces
$
10.005
$10.005 before the
×
0.8
×0.8 Happy Hour step). Should you round to the whole cent after each individual discount step, or only once at the very end when locking the final order bill? Why?

Your Turn
Share how your Stage 2 Customer / VIPCustomer and Order classes currently calculate the bill (you can paste those few lines if you'd like).

Tell me where you plan to place the pricing calculation and how you'll handle the rounding step—then I'll review your approach!

can you use math.ceil here ?

Let's trace what Math.ceil actually does versus what your project specification requires.

1. What Math.ceil Does vs. What the Brief Says
   Math.ceil(x) always rounds up to the next whole integer, even if the fractional part is .01 (for example, Math.ceil(10.01) becomes 11.0).

In your assignment brief (Section 2.2), the rule for money states:

"Money. If fractional prices arise, store amounts as integer cents. Display dollars with two decimals."   
SC2002_Restaurant_Rush_Group_Assignment.docx

And in Section 4.3, ComboMeal explicitly specifies: "rounded to the nearest cent", while you just noted needing round half up to the whole cent.   
SC2002_Restaurant_Rush_Group_Assignment.docx

If you stored prices in integer cents (e.g.,
$
10.80
=
1080
$10.80=1080 cents) and used Math.ceil, a calculated value of 1080.1 cents would jump up to 1081 cents (
$
10.81
$10.81) instead of rounding to the nearest cent (1080 cents, or
$
10.80
$10.80).

2. Socratic Check-In: Testing Math.ceil on a Concrete Case
   Try running a quick mental test with numbers from your brief:

Suppose a VIP customer orders a ComboMeal (Salad + Pasta =
$
24.30
$24.30, or 2430 cents) during Happy Hour (20% off):   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

First, apply the VIP 10% discount:
2430
×
0.9
=
2187
2430×0.9=2187 cents.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Next, apply the Happy Hour 20% discount:
2187
×
0.8
=
1749.6
2187×0.8=1749.6 cents.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Here, Math.ceil(1749.6) gives 1750 cents (
$
17.50
$17.50), which matches round-half-up.

Now, what if a Regular customer orders that same Salad + Pasta combo (2430 cents) during Happy Hour?

2430
×
0.8
=
1944.0
2430×0.8=1944.0 cents (exact).

What if an item or discount produces 1749.2 cents? What would Math.ceil(1749.2) return compared to Math.round(1749.2) (or BigDecimal with RoundingMode.HALF_UP)?

Your Turn
Why would Math.ceil give the wrong cent amount on 1749.2 cents, and which Java method or class gives you round half up on integer cents instead?

Looking at Section 2.2 ("store amounts as integer cents"), how does storing price in int cents (e.g., 900, 1500, 1800) make both the VIP
→
→ Happy Hour calculation and the rounding step cleaner?   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Also for the customer class, i intend to add a recoverSatisfaction method, how do i make sure that the score doesnt exceed 100?

You can enforce the ceiling of 100 directly inside Customer.recoverSatisfaction(int amount) using Math.min(100, this.satisfaction + amount).   
SC2002_Restaurant_Rush_Group_Assignment.docx

1. Enforcing the Class Invariant via Encapsulation
   In SC2002, encapsulation means an object is responsible for protecting its own valid state (its class invariants) rather than relying on external callers like HappyHour or GameController to check bounds before mutating it.   
   17S1_CECZ2002_PPT_Chapter9DesignPrinciples_V1.0.pdf

In Restaurant Rush, a customer's satisfaction score must always stay within the range [0, 100]:

When losing satisfaction at turn end, it floors at 0 (Math.max(0, this.satisfaction - loss)).   
SC2002_Restaurant_Rush_Group_Assignment.docx

When recovering 15 points at the start of an active Happy Hour turn, it caps at 100 (Math.min(100, this.satisfaction + amount)).   
SC2002_Restaurant_Rush_Group_Assignment.docx

By encapsulating that clamping logic inside Customer.recoverSatisfaction(int amount), no external class can accidentally push satisfaction above 100.   
17S1_CECZ2002_PPT_Chapter9DesignPrinciples_V1.0.pdf

2. Socratic Check-In: Eligibility & Edge Cases
   Before you write recoverSatisfaction(int amount) and call it from HappyHour or your turn processor, think through these two domain constraints from Section 4.3 of the brief:   
   SC2002_Restaurant_Rush_Group_Assignment.docx

Defensive Input Validation: What should happen if someone passes a negative amount into recoverSatisfaction(int amount)? If you only wrote Math.min(100, this.satisfaction + amount) without guarding against amount < 0, a negative argument would secretly reduce satisfaction instead of throwing an IllegalArgumentException or ignoring it.

Who is Eligible to Recover Satisfaction? Section 4.3 states: "During each active turn, customers still waiting for a table or food recover 15 satisfaction at turn start (maximum 100)."   
SC2002_Restaurant_Rush_Group_Assignment.docx

Suppose a customer is currently SERVED, EATING, READY_TO_PAY, or has already LEFT (abandoned at 0 satisfaction).   
SC2002_Restaurant_Rush_Group_Assignment.docx

Should the eligibility check ("is this customer still waiting for a table or food?") live inside Customer (e.g., an isWaitingForTableOrFood() query method or a guard inside recoverSatisfaction), or inside GameController? Why does placing it on Customer better respect Information Expert / Encapsulation?   
17S1_CECZ2002_PPT_Chapter9DesignPrinciples_V1.0.pdf

Your Turn
Write out the method signature and 2–3 lines of body logic you would put in Customer.recoverSatisfaction(int amount) (including any guard for negative numbers or customer state).

In your Stage 2 Customer class, what enum or boolean fields currently track whether a customer is waiting for a table, waiting for food, served, or left? (Paste those lines if you'd like to check them against the Stage 3 rule!)

For Host, should it be called before or after Waiter

In Tutorial/Socratic Mode, let's look closely at Section 3.2 (Turn steps) and Section 4.3 (Stage 3: Host) of your Restaurant Rush Group Assignment brief to trace how calling the Host before versus after the Waiter affects gameplay within a single turn!   
SC2002_Restaurant_Rush_Group_Assignment.docx

1. What the Brief Actually Specifies
   Section 3.2 (Turn steps):

Step 1 (Arrivals): Scheduled customer joins the waiting queue.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Step 2 (Manager choices): Manager chooses at most one task for the Waiter (seat one queued customer, take one seated customer's order, or serve one READY order) and at most one order for the Chef.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Step 3 (Kitchen and serving): The Waiter acts before the Chef.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Section 4.3 (Stage 3 – Host):

"The Host seats at most one queued customer per turn, in addition to the Waiter’s task. The Waiter can still seat customers."   
SC2002_Restaurant_Rush_Group_Assignment.docx

Notice that the brief deliberately leaves how the Host operates—whether the Host is a manager-controlled choice in Step 2 or an automatic seating role, and whether the Host acts before or after the Waiter—up to your group's design and documentation (Section 4.4: "You choose class names and design patterns").   
SC2002_Restaurant_Rush_Group_Assignment.docx

2. Socratic Trace: Before Waiter vs. After Waiter
   Imagine it is the start of Turn 1. Both tables (Table 1 and Table 2) are empty, and one customer (Customer 1) just arrived in Step 1:   
   SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Scenario A: Host acts BEFORE Waiter (or Manager assigns Host before Waiter)

Host seats Customer 1 at Table 1.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Now, when it is the Waiter's turn to act in that same turn, Customer 1 is already seated! Can the Waiter immediately take Customer 1's order in Turn 1, saving an entire turn of waiting?   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Scenario B: Host acts AFTER Waiter

When the Waiter acts first, Customer 1 is still in the queue. If the manager chooses not to seat Customer 1 with the Waiter (hoping the Host will do it later), the Waiter has nothing to do (Wait).   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Then the Host seats Customer 1 at Table 1—meaning nobody can take Customer 1's order until Turn 2.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Scenario C: Two customers are waiting in the queue, and two tables are free

Does the order between Host and Waiter matter when both are seating different customers?   
SC2002_Restaurant_Rush_Group_Assignment.docx

Your Turn
How did your group design Host—does the manager choose whom the Host seats during Step 2 (alongside Waiter and Chef), or does the Host automatically seat the front customer in the queue?   
SC2002_Restaurant_Rush_Group_Assignment.docx

Comparing Scenario A (Host before Waiter) and Scenario B (Host after Waiter) above, which sequence gives the player more meaningful tactical flexibility in Turn 1 without breaking any rules in Section 3.4? Why?   
SC2002_Restaurant_Rush_Group_Assignment.docx

Who removes the customer from the waiting queue, Host or the controller? Does both work? Does the Host need to know whether the queue even exists?

You are asking the exact right architectural question here—this comes down to Responsibility Assignment (BCE / MVC), Low Coupling, and the Single Responsibility Principle (SRP). Let's work through your three questions in Tutorial/Socratic Mode by looking at how your Stage 1/2 Waiter already handles seating.   
UML-ClassDiagram - 2026S1.pdf
+ 1

1. Does Host Even Need to Know the Waiting Queue Exists?
   No—and keeping Host unaware of the waiting queue is actually the cleaner object-oriented design.

Think about what a Host entity represents in your domain model:   
UML-ClassDiagram - 2026S1.pdf

Does Host own the restaurant's floor layout or the front-door waiting line (Queue<Customer>), or does your restaurant state / GameController hold the waiting queue and the list of Table objects?   
SC2002_Restaurant_Rush_Group_Assignment.docx

Compare this to how your Waiter currently seats a customer in Stage 1 and Stage 2. When the manager chooses "Seat Customer X at Table Y", GameController already validates that Customer X is in the waiting queue and Table Y is free, removes Customer X from the queue, and tells Waiter (or Table / Customer) to execute the seating.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

If Host is passed a Customer and a Table—for example, host.seatCustomer(Customer customer, Table table)—Host never needs a reference to the Queue<Customer> at all. That keeps coupling low (Host depends only on Customer and Table, not on how the queue data structure is stored).   
UML-ClassDiagram - 2026S1.pdf
+ 1

2. Controller vs. Host Removing from the Queue: Do Both Work?
   Technically, both can compile and run, so let's compare the trade-offs in terms of SRP and Control vs. Entity:   
   UML-ClassDiagram - 2026S1.pdf

Option A: GameController (Control) manages the queue; Host (Entity) executes the seating action

GameController selects or receives the target Customer from the waiting queue and an available Table.   
SC2002_Restaurant_Rush_Group_Assignment.docx

GameController removes the Customer from the waiting queue (or pops the head of the queue) and calls host.seatCustomer(customer, table).

Inside host.seatCustomer(customer, table), Host coordinates the domain state change between the two entities—assigning customer to table and updating customer's status from waiting in queue to seated.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Option B: Host receives the Queue<Customer> and removes the customer itself

Now Host has two responsibilities: mutating the restaurant's global waiting queue and seating the customer at a table. Worse, if both Waiter and Host mutate the queue in different places, queue-removal logic is duplicated across two staff classes.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

3. Socratic Check-In: Tracing Consistency with Your Existing Waiter
   Look at how your Waiter.java is currently written in Stage 2:

In your current Waiter.java, what parameters does the Waiter's seating method take? Does Waiter take (Customer, Table) or does Waiter touch the waiting queue directly?

Remember Section 4.3 of the brief: "The Host seats at most one queued customer per turn, in addition to the Waiter’s task. The Waiter can still seat customers."   
SC2002_Restaurant_Rush_Group_Assignment.docx

Suppose in GameController, you have a helper or workflow where GameController removes the customer from the queue and hands (customer, table) to either Waiter or Host. What happens to your Stage 3 Sequence Diagram (and your code reuse between Waiter and Host) if both Waiter and Host share the exact same seatCustomer(Customer, Table) contract?   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 2

Mini Design Exercise
Write down the exact method signature you will put in Host.java (and check whether Host should inherit from Staff just like Waiter, Chef, and Cashier).

Trace the 3–4 lines inside GameController when Host performs its seating action in a turn: how does GameController check if a customer is waiting and a table is free before calling Host?   
SC2002_Restaurant_Rush_Group_Assignment.docx

The Host can seat one queued customer. Must it be the one at the front of the queue? If yes how does it work in code?

1. Must It Be the Customer at the Front of the Queue?
   No, the brief does not force the Host to take strictly the head of the queue. In Section 4.3, the specification states:   
   SC2002_Restaurant_Rush_Group_Assignment.docx

"Host. The Host seats at most one queued customer per turn, in addition to the Waiter’s task. The Waiter can still seat customers."   
SC2002_Restaurant_Rush_Group_Assignment.docx

Compare that wording with how Section 3.2 specifies the Cashier's rule: "The Cashier automatically handles at most one READY_TO_PAY customer per turn, oldest first." Notice the difference: for the Cashier, "oldest first" (FIFO) is explicitly mandated; for the Host, the brief simply says "one queued customer per turn" and gives you design freedom in Section 4.4.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 2

This gives your group two valid design choices (both work as long as you document your rule clearly):

Manager-chosen queued customer: In Step 2, the manager picks which queued customer (and free table) the Host seats, giving tactical control (for example, seating a Critic who loses 12 satisfaction/turn ahead of a VIP who only loses 5/turn).   
SC2002_Restaurant_Rush_Group_Assignment.docx

Automatic FIFO (front of the queue): The Host automatically seats the customer at the front of the queue whenever a free table is available.   
SC2002_Restaurant_Rush_Group_Assignment.docx

2. Socratic Check-In: How Front-of-Queue Works in Java Collections
   Since we are in Tutorial/Socratic Mode, let's think through how your waiting queue is represented in Java if you choose FIFO (front of the queue) versus Manager-chosen:

Data Structure & Methods (java.util.Queue vs. java.util.List):

Suppose your waiting line is declared as Queue<Customer> waitingQueue = new LinkedList<>() (or ArrayDeque<>()).

In Java's Queue interface, what is the difference between calling waitingQueue.poll() versus waitingQueue.remove() when the queue might be empty? Which one safely returns null instead of throwing a NoSuchElementException?

Conversely, if you allow the manager to pick any queued customer (or if the Waiter seats a customer who isn't at the front), why is a List<Customer> (or remove(Customer) on the collection) easier to work with than a strict FIFO Queue?

Checking Preconditions Before Polling:

Imagine it is the Host's turn to act, and there are 2 customers in waitingQueue, 2 occupied tables, and 0 free tables.   
SC2002_Restaurant_Rush_Group_Assignment.docx

If your controller calls waitingQueue.poll() before checking whether a free Table exists, what bug happens to the customer at the front of the queue?

Your Turn
Tell me which design your group wants for Host: Manager-chosen (like the Waiter) or Automatic FIFO (front of the queue)?   
SC2002_Restaurant_Rush_Group_Assignment.docx

Write out the 4–5 lines of Java logic (or pseudocode) you would use in GameController to safely find a free Table, retrieve the queued Customer, and call host.seatCustomer(customer, table) without accidentally removing a customer when no table is free.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Does everything go through Console.UI

No, not everything goes through ConsoleUI. Under the Boundary–Control–Entity (BCE) / MVC architecture in your SC2002 materials, ConsoleUI is strictly your Boundary (View) class.   
UML-ClassDiagram - 2026S1.pdf

What Goes Through ConsoleUI vs. What Does Not
Goes through ConsoleUI (Boundary / View):

Reading player inputs from the terminal (Scanner)—such as choosing whether to activate Happy Hour, picking a task for the Waiter or Host, or selecting an order for the Chef.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Printing the current restaurant state, turn summaries, Happy Hour remaining turns, and end-of-turn results (System.out.println).   
SC2002_Restaurant_Rush_Group_Assignment.docx

Does NOT go through ConsoleUI (Control & Entity):

Turn orchestration & workflow (GameController): Coordinating Step 1 arrivals, Step 2 manager choices, Step 3 kitchen/serving execution, and Step 4 end-of-turn updates happens inside GameController.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Domain rules & state mutations (Entities): Calculating prices (Order, Customer, HappyHour), updating satisfaction (Customer), preparing food (Chef), seating (Host, Waiter, Table), and processing payments (Cashier) happen directly between GameController and your entity classes without touching ConsoleUI.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Think of it like the sequence diagrams in your lecture slides (:User
→
→ :SelectForm
→
→ :AppMgr
→
→ :Entity): ConsoleUI sits strictly at the edge between the human player and GameController. Your entities (Host, HappyHour, Customer, Order) should never import or call ConsoleUI.   
21S1_CE2002_PPT_Chapter8 ModelOOApp_V1.0.pptx
+ 3

Socratic Check-In: Checking Your Stage 3 Classes Against MVC
Since we are in Tutorial/Socratic Mode, let's apply this boundary rule to the Stage 3 features you are building:

Happy Hour Display & Input: Suppose HappyHour has 2 turns remaining, and the manager needs to see "Happy Hour: ACTIVE (2 turns left)" and be prompted whether to activate it at the start of the turn.

Which class tracks the remaining turns (int remainingTurns), which class decides when to ask the player, and which class actually executes System.out.println(...) and scanner.nextLine()?   
UML-ClassDiagram - 2026S1.pdf

Testing Advantage (tests/): Why does keeping all game rules and state out of ConsoleUI make it much easier for you to write JUnit tests (or automated input-script tests like stage3-victory.txt) for HappyHour and Host?

Mini Exercise
Look at your HappyHour.java, Host.java, and ConsoleUI.java in your IDE:

Check if any entity class (HappyHour, Host, Customer, Order) currently contains System.out.println or Scanner.

Write down the exact method signature you will add to ConsoleUI to display the Happy Hour status and prompt the manager for activation—ensuring ConsoleUI only returns the player's choice to GameController rather than mutating HappyHour itself.   
UML-ClassDiagram - 2026S1.pdf

In runHostPhase() how should I end the phase if the Host does not seat any customers? Do I need to return each scenario with a return statement

In Tutorial/Socratic Mode, let's look at control flow and return types in Java before you decide how runHostPhase() should exit when the Host does not seat anyone.

1. Does runHostPhase() Need a return Statement for Each Scenario?
   It depends entirely on what runHostPhase() returns (its method signature) and whether anything runs after the seating check inside that method:

Case A: private void runHostPhase() (returns nothing)

In a void method, Java automatically finishes and returns to the caller (GameController's turn loop) as soon as execution reaches the closing brace }.

You only use an early return; (called a guard clause) if there is logic lower down in runHostPhase() that you want to skip—for example, if waitingQueue.isEmpty() or there are no free tables, or if the manager chooses "Skip" / "Wait".   
SC2002_Restaurant_Rush_Group_Assignment.docx

If you use an if / else if / else structure with no code after it, execution naturally falls out of the bottom of the method without needing a return; inside every branch.

Case B: private boolean runHostPhase() (or returning a status/summary)

You would only return a value (e.g., true if someone was seated, false otherwise) if the caller (GameController) needs that result to decide what to do next (such as logging a message via ConsoleUI).

2. Socratic Check-In: Tracing the No-Seating Scenarios
   In Stage 3, the Host seats at most one queued customer per turn, in addition to the Waiter's task. Think about the three scenarios where runHostPhase() ends without seating a customer:   
   SC2002_Restaurant_Rush_Group_Assignment.docx

Scenario 1 (Impossible preconditions): Either the waiting queue is empty OR all tables are occupied. Should GameController even prompt the user for a Host choice in this case, or should it exit runHostPhase() immediately via a guard clause (if (waitingQueue.isEmpty() || !hasFreeTable()) { return; })?   
SC2002_Restaurant_Rush_Group_Assignment.docx

Scenario 2 (Manager deliberately skips): There is a queued customer and a free table, so ConsoleUI prompts the manager, and the manager chooses not to use the Host this turn.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Scenario 3 (Invalid choice): Remember Section 3.4 of the brief: "An invalid choice must explain why it failed and let the manager choose again, without using up the staff task." If the manager picks an occupied table or non-existent customer, should runHostPhase() return (ending the phase early), or stay inside a while loop until a valid choice (or explicit Skip) is entered?   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

Mini Design Exercise
Write down the exact method signature you chose for runHostPhase() (is it void or does it return a type?) and list the 2–3 conditions under which the Host does not seat anyone.

Paste your draft of runHostPhase() (even if it's pseudocode) and show how you handle Scenario 1 (no queue/no free table) versus Scenario 3 (invalid input) so the game never crashes or silently skips a valid turn.   
SC2002_Restaurant_Rush_Group_Assignment.docx

Does each commit n Github compile on its own, I am abit confused on how Github works

No, GitHub does not automatically compile every commit on its own—by default, GitHub is just a remote storage and version-control server for your text files (.java, .md, .txt). Unless someone on your team explicitly sets up an automated Continuous Integration (CI) workflow (like GitHub Actions), GitHub will happily let you push broken or half-finished Java code without running javac.   
SC2002_Restaurant_Rush_Group_Assignment.docx
+ 1

That is actually a superpower once you understand how Git branches, commits, and tags work together for your assignment:

1. How GitHub Stores Your Code (Snapshots, Not Auto-Compiled Builds)
   Commits are saved snapshots: Every time you run git commit and git push, GitHub saves a snapshot of your repository at that exact moment. On your personal branch (<your-name>-stage3), it is completely normal to commit work-in-progress snapshots while you are building a feature—even if you haven't finished wiring everything together yet.

Pull Requests (PRs) are your team's quality gate: Before merging your personal branch into main, you (and your teammates) compile and run the project locally on your computer (javac / IDE run) to verify that nothing broke.

Tags (stage-1, stage-2, stage-3) mark the runnable milestones: For your SC2002 submission, the rubric specifically requires that each stage be saved as a runnable tagged commit (stage-1, stage-2, and stage-3). Think of main + your stage tags as the "guaranteed to compile and run" checkpoints, while your personal branch is your workspace.   
SC2002_Group_Project_Briefing.pdf
+ 3

2. Why This Matters for Your Group's Workflow
   Because GitHub doesn't auto-compile for you, here is the simple habit that prevents "broken main" headaches in a 4-person group:

Commit often on your personal branch: Push small steps (e.g., HappyHour.java done, then Host.java done) to your own branch so your work is backed up.   
SC2002_Group_Project_Briefing.pdf

Compile & run your Stage 2/3 test scripts before merging a PR: Always run Game and your tests/ files locally after merging origin/main into your branch and before merging your PR into main.   
OODP_Lab_Manual(1).pdf

Only tag stage-3 once main compiles and passes all tests: That guarantees the graders can check out stage-1, stage-2, or stage-3 and run each one cleanly.   
SC2002_Group_Project_Briefing.pdf
+ 1

Quick Check-In
To make sure your mental model of GitHub is 100% solid before your next merge:

Suppose you push a commit with a syntax error to your personal branch (<your-name>-stage3). Does that affect your teammates working on main or on their own branches?

When a grader later clicks on the stage-3 tag in your repository, which branch or commit are they inspecting?   
SC2002_Group_Project_Briefing.pdf
+ 1

For the AI History component of this assignment, do I export it as a doc or md file then upload to repo? I am super confused on this step so please analyse the documents and explain to me

Export it as Markdown (.md) files and upload them into your personal branch on your group's GitHub repository—not as .doc/.docx files.   
SC2002_Group_Project_Briefing.pdf

Here is the exact breakdown of what the assignment guidelines require for the AI History component:   
SC2002_Group_Project_Briefing.pdf

1. File Format & Folder Structure
   According to the GitHub evidence for the group and each member guidelines, your files must be saved as .md files inside an ai-history/ directory structured by stage on your own personal branch:   
   SC2002_Group_Project_Briefing.pdf

ai-history/stage-1/chat-01.md   
SC2002_Group_Project_Briefing.pdf

ai-history/stage-2/chat-01.md   
SC2002_Group_Project_Briefing.pdf

ai-history/stage-3/chat-01.md (and chat-02.md, etc., if you had multiple separate chat sessions for Stage 3)   
SC2002_Group_Project_Briefing.pdf

(Why .md instead of .doc? GitHub renders Markdown files natively in the browser so graders can read your chat logs directly on your branch without downloading binary Word documents.)

2. What Must Be Inside Each .md File
   Under AI use and your communication history, the rules specify:   
   SC2002_Group_Project_Briefing.pdf

Full, unedited conversation: Include all project-related prompts, AI replies, and follow-up exchanges.   
SC2002_Group_Project_Briefing.pdf

Chronological order: Keep the full conversation in order—a summary alone is insufficient.   
SC2002_Group_Project_Briefing.pdf

Verification: You must understand and verify the work you submit.   
SC2002_Group_Project_Briefing.pdf

3. Where to Upload & Link Everything
   Personal Branch Only (Do Not Delete): Upload your ai-history/ folder to your own personal branch in the group GitHub repository (e.g., <your-name>-stage3), not just merged and deleted. Each group member keeps their own AI chats in their own branch.   
   SC2002_Group_Project_Briefing.pdf
+ 1

Update the Group README.md: In the main repository's README.md, add direct links to every stage tag (stage-1, stage-2, stage-3) and to every member's AI history on their respective branches.   
SC2002_Group_Project_Briefing.pdf
+ 1

Submission Archive (LABGroup_AssignmentGrp.zip): In addition to the GitHub links, each member must also submit a signed Declaration on Use of GAI Assistance (using the official course form) alongside the group's Declaration of Original Work in the final submission ZIP.   
SC2002_Group_Project_Briefing.pdf
+ 1

Quick Check-In
Before you push your Stage 3 AI history to GitHub:

Have you checked your personal branch on GitHub to confirm that your ai-history/stage-3/ folder is visible there and linked inside the main README.md?   
SC2002_Group_Project_Briefing.pdf
+ 1

Do you have your separate Declaration on Use of GAI Assistance course form ready for your group's final ZIP submission?   
SC2002_Restaurant_Rush_Group_Assignment.docx

How do I export this chat as an MD file. I have the repository cloned inside my Intellij on my windows, can you guide me step by step on how to do so? Thank you

To get this chat saved as a Markdown (.md) file inside your cloned repository on IntelliJ (Windows) and pushed to your personal branch on GitHub, follow these steps:   
SC2002_Group_Project_Briefing.pdf

Step 1: Copy the Full Chat Conversation
Gemini web chats do not have a built-in "Export to .md" button, so the cleanest way to preserve the exact, unedited chronological conversation required by the assignment is to copy it directly:   
SC2002_Group_Project_Briefing.pdf

Scroll up to the very first prompt of this chat session.

Highlight everything from your first message down to the bottom of the page, then press Ctrl + C to copy. (Alternatively, click the Share & export icon below the final response if available, or copy each prompt and reply in order so none of your messages are missed.)   
SC2002_Group_Project_Briefing.pdf

Step 2: Create the .md File Inside IntelliJ IDEA
Open your cloned sc2002_group_assignment project in IntelliJ IDEA.

First, confirm you are on your personal branch (check the bottom-right corner or top-left Git widget in IntelliJ—it should show your personal branch name, not main).   
SC2002_Group_Project_Briefing.pdf

In the Project tool window on the left, right-click the root project folder (sc2002_group_assignment):   
chuanhhua/sc2002_group_assignment

Select New
→
→ Directory and name it ai-history.   
SC2002_Group_Project_Briefing.pdf

Right-click ai-history, select New
→
→ Directory, and name it stage-3.   
SC2002_Group_Project_Briefing.pdf

Right-click stage-3, select New
→
→ File, and name it chat-01.md (so the path matches ai-history/stage-3/chat-01.md).   
SC2002_Group_Project_Briefing.pdf
+ 1

Paste (Ctrl + V) your copied conversation into chat-01.md.

Format it cleanly with Markdown headings (e.g., ## User and ## Gemini) so each prompt and reply is clearly separated and easy to read on GitHub. Press Ctrl + S to save.   
SC2002_Group_Project_Briefing.pdf

Step 3: Commit & Push to Your Personal Branch on GitHub
In IntelliJ IDEA, open the Commit tool window on the left sidebar (or press Ctrl + K).

Under Changes / Unversioned Files, tick the checkbox next to ai-history/stage-3/chat-01.md.   
SC2002_Group_Project_Briefing.pdf

Enter a clear commit message, such as:Add Stage 3 AI communication history (chat-01.md)

Click Commit and Push... (or press Ctrl + Alt + K) and confirm pushing to your personal branch on origin.   
SC2002_Group_Project_Briefing.pdf

Step 4: Verify on GitHub & Link in README.md
Open your group's repository on GitHub in your browser, switch the branch dropdown from main to your personal branch, and click into ai-history/stage-3/chat-01.md to verify that the full conversation renders properly.   
SC2002_Group_Project_Briefing.pdf

Copy that page's URL from your browser's address bar and add it to the main repository's README.md under your name (as required: "README: link to every stage and every member's AI history").   
SC2002_Group_Project_Briefing.pdf
+ 1

Quick Check-In
Once you push chat-01.md, check these two details before moving on:   
SC2002_Group_Project_Briefing.pdf

Did you keep both your prompts and the AI replies in chronological order without deleting or summarizing parts of the exchange?   
SC2002_Group_Project_Briefing.pdf

If you also have Stage 1 or Stage 2 chats from earlier in the project, have you placed them in ai-history/stage-1/ and ai-history/stage-2/ on your personal branch as well?   
SC2002_Group_Project_Briefing.pdf