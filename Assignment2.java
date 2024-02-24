import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;


class Labyrinth {
    private int numGuests;
    private Lock lock;
    private boolean cupcake = true;
    private AtomicInteger cupcakesEaten;
    private boolean announceToMinotaur;

    public Labyrinth(int numGuests) 
    {
        this.numGuests = numGuests;
        this.lock = new ReentrantLock();
        this.cupcakesEaten = new AtomicInteger(0);
    }

    public boolean hasEaterAnnounced()
    {
        return announceToMinotaur;
    }

    // only one guest (eater) eats cupcakes, and he keeps track of how many he eats.
    // other guests can only request 1 cupcake, and only request if cupcake is missing.
    // Once the eater guest has ate n cupcakes it means n guests have entered (including eater),
    // since they each put 1 cupcake down.
    public boolean enterLabyrinth(boolean eater, boolean placedCupcake) 
    {
        lock.lock();

        // if a guest is the eater and sees a cupcake, eat cupcake and keep track.
        if (eater && cupcake)
        {
            cupcake = !cupcake;
            cupcakesEaten.incrementAndGet();
            if (cupcakesEaten.get() == numGuests)
            {
                announceToMinotaur = true;

                System.out.println("All guests have entered the labyrinth at least once.");
            }
            lock.unlock();
            return false;
        }
        // if a guest is a non-eater and there is no cupcake and they havent placed their 
        // 1 cupcake, place cupcake down
        else if (!eater && !cupcake && !placedCupcake)
        {
            cupcake = !cupcake;
            lock.unlock();
            return true;
        }
        
        lock.unlock();

        // if a guest has already placed a cupcake, do nothing regardless if there is cupcake
        if (placedCupcake)
        {
            return true;
        }

        return false;
    }
}

class Guest extends Thread {
    private Labyrinth labyrinth;
    private boolean eater;
    private boolean placedCupcake;

    public Guest(Labyrinth labyrinth, boolean eater)
    {
        this.labyrinth = labyrinth;
        this.eater = eater;
        this.placedCupcake = false;
    }

    public void run() 
    {
        // guests enter the labyrinth until the eater announces everyone has gone
        while (!labyrinth.hasEaterAnnounced())
        {
            placedCupcake = labyrinth.enterLabyrinth(eater, placedCupcake);
        }
    }
}

// program 2

class Showroom 
{ 
    // true represents "AVAILABLE", false represents "BUSY"
    private AtomicBoolean sign = new AtomicBoolean(true);

    public synchronized boolean enterShowroom(int guestId) 
    {
        // if the showroom is full, function returns false so it can be called again
        if (!sign.get())
        {
            return false;
        }
        System.out.println("Guest " + guestId + " entered the showroom.");
        sign.set(false);

        return true;
    }

    // once the guest exits the showroom, the showroom sign is set to available
    public synchronized void exitShowroom(int guestId) 
    {
        System.out.println("Guest " + guestId + " exited the showroom.");
        sign.set(true);
    }
}

class CrystalGuest extends Thread 
{
    private int guestId;
    private Showroom showroom;

    public CrystalGuest(int guestId, Showroom showroom) 
    {
        this.guestId = guestId;
        this.showroom = showroom;
    }

    @Override
    public void run() {
        showroom.enterShowroom(guestId);
        showroom.exitShowroom(guestId);
    }
}

public class Assignment2 
{
    public static void main(String[] args) 
    {
        // program 1
        int numGuests = 15; 
        
        Labyrinth labyrinth = new Labyrinth(numGuests);
        Guest[] guests = new Guest[numGuests];

        for (int i = 0; i < numGuests; i++) 
        {
            if (i == (numGuests - 1))
            {
                guests[i] = new Guest(labyrinth, true);
            }
            else
            {
                guests[i] = new Guest(labyrinth, false);
            }
            guests[i].start();
        }

        for (Guest thread : guests) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // program 2
        
        Showroom room = new Showroom();
        CrystalGuest[] crystalGuests = new CrystalGuest[numGuests];

        for (int i = 0; i < numGuests; i++) 
        {
            crystalGuests[i] = new CrystalGuest(i, room);
            crystalGuests[i].start();
        }

        for (CrystalGuest thread : crystalGuests) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}