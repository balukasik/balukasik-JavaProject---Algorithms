

import Data.Dane;
import Data.Pacjent;
import Data.Szpital;
import Jarvis.Jarvis;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class JarvisTest {

    @Test
    public void convexHull() {
        Dane.read("data/daneTestowe.txt");
        Dane.readPacjent("data/daneTestowePacjenci.txt");

        Jarvis jarvis=new Jarvis();
        List<Szpital> result=jarvis.convexHull();

        int resultArray[]=new int[result.size()];
        for(int i=0;i<result.size();i++){
            resultArray[i]=result.get(i).getId();
        }

        int expectArray[] = {6,1,5,3,4};

        Assert.assertArrayEquals(expectArray,resultArray);




    }

    @Test
    public void findNearest() {
        Dane.read("data/daneTestowe.txt");

        Pacjent pacjent1=new Pacjent(0,0,0,"a");
        Pacjent pacjent2=new Pacjent(0,400,400,"a");


        Szpital nearest1 = Jarvis.findNearest(pacjent1);
        Szpital nearest2 = Jarvis.findNearest(pacjent2);



        int expect1=1;
        int expect2=3;

        Assert.assertEquals(expect1,nearest1.getId());
        Assert.assertEquals(expect2,nearest2.getId());


    }
}