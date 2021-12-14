package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;

public interface PullFilter <T , S>{

    T read();

    void setIncomingPipe(PullPipe<S> pipe);

}
