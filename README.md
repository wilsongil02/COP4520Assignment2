
To compile Assignment2.java, the command 'javac Assignment2.java' must be used, and to run the program, 'java Assignment2' must be used. 

For problem 1, my approach began by creating a class that would extend the Thread class, as I wanted to begin writing the code that every thread would follow, to represent every guest entering the labyrynth. I then drew out a couple of approaches, and eventually landed on an approach that works by only one guest eating cupcakes, and that one guest keeping track of how many he cupcakes eats. The other guests are not allowed to eat cupcakes, and are only allowed to request 1 cupcake if there is no cupcake when they enter the laborynth. This means that by the time the "eater" guest has eaten n cupcakes, it means n guests have entered the laborynth (including the eater), since each of the other guests have requested 1 cupcake and the eater ate his own cupcake too. The way I tested the correctness of the program was I set the number of guests to a small number, like 5, and I temporarily set each guest up with an ID number, so I could manually check through print statements that each guest entered the laborynth.

Now for problem 2, I also began by making a class that extends the Thread class, to represent every guest entering the showroom. Problem 2 had 3 strategies, each with their pros and cons. The first strategy was the open door strategy, which had the advantage that any guest could walk up to the door at any time to check if its open, but had the disadvantage that it can cause a huge crowd is many people go at once to check. The second approach was the sign approach, where the door to showroom has a sign with either "AVAILABLE" or "BUSY", which has the advantage that anyone nearby can check the sign without walking up to the door, but has the disadvantage that a crowd could still form if many check at the same time. The third approach was the queue, where guests form a gueue by lining up to enter, which has the advantage that everyone neatly lines up and waits their turn to enter, but has the disadvantage that now everyone is forced to wait in line for a long time to enter, when they could be doing other things. I chose approach 2, and what I did was have a sign boolean that guests can modify when they enter and leave the showroom, and they can only enter if the room is available. The way I tested correctness was with guest ID numbers and print statements, which allowed me to manually check that each guest would enter and leave the showroom before the next guest could enter. Both problems used multi threading, so both are very efficient, since the tasks are split among threads.