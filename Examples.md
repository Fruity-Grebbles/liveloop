#Some examples of codes using the api

# Introduction #

Example of use of the api


# Codes #

1. Live loop exemple
use the class LiveLoop

2. A Simple Exemple
public class SimpleTest {
> public static void main(String[.md](.md) args) throws Exception {
> > MidiConnect.listDevices();
> > ClassicInput in=new ClassicInput(10);
> > ClassicOut out=new ClassicOut(13);
> > in.addReceiver(out);


> }
> //example using the PC keyboard
> public static void main2(String[.md](.md) args) throws Exception {
> > MidiConnect.listDevices();
> > DumpKB in=new DumpKB(100,0);
> > ClassicOut out=new ClassicOut(13);
> > in.addReceiver(out);


> }

}