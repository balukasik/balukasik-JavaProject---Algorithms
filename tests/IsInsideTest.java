import IsInside.IsInside;
import org.junit.jupiter.api.Test;

import Data.Dane;
import Data.Pacjent;
import Data.Szpital;
import Jarvis.Jarvis;
import org.junit.Assert;


import java.util.*;



class IsInsideTest {

    @Test
    public void isInside() {

        Dane.read("data/daneTestowe.txt");
        Dane.readPacjent("data/daneTestowePacjenci.txt");

        Jarvis jarvis=new Jarvis();
        Szpital[] result= jarvis.convexHull().toArray(new Szpital[0]);

        Pacjent pacjent1=new Pacjent(0,0,0,"a");
        Pacjent pacjent2=new Pacjent(0,400,400,"a");

        IsInside isInside=new IsInside();
        boolean result1=isInside.isInside(result,pacjent1);
        boolean result2=isInside.isInside(result,pacjent2);

        boolean expect1=false;
        boolean expect2=false;

        Assert.assertEquals(expect1,result1);
        Assert.assertEquals(expect2,result2);
    }

}