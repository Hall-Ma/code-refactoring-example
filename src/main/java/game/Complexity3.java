package game;

public class Complexity3 {

    public boolean IsLeapYear(int y) {
        boolean response = false;
        if ((y >= 1000) && (y <= 9999)) {
            //if it is divisible by 400 e.g. 1600, 2000, then all other checks are useless. we can return from here
            if (y % 400 == 0)
                response = true;
                //for years like 1700, 1900, 2100 etc. which are not leap years
            else if ((y % 100 == 0) && (y % 400 != 0))
                response = false;
                //finally, if the above two fails, just check if it divisible by 4 for years like 1980,1996 etc
            else if (y % 4 == 0)
                response = true;
        } else {
            response = false;
            System.out.println("The argument value should be inside 1000 and 9999 ");
        }
        return response;
    }
}
