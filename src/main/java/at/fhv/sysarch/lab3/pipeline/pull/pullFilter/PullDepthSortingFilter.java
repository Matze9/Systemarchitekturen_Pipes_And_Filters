package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;

import java.util.LinkedList;

public class PullDepthSortingFilter implements PullFilter<Face, Face>{

    private LinkedList<Face> faces;
    private PullPipe<Face> incomingPipe;



    @Override
    public Face read() {

        Face face = incomingPipe.read();




        return null;
    }

    @Override
    public void setIncomingPipe(PullPipe<Face> pipe) {
        this.incomingPipe = pipe;

    }
}
