package at.fhv.sysarch.lab3.pipeline.pull.pullPipe;

import at.fhv.sysarch.lab3.pipeline.pull.pullFilter.PullFilter;

public class PullPipeImpl<T> implements PullPipe<T>{

    PullFilter<T, ?> pullFilter;

    public PullPipeImpl(PullFilter<T,?> pullFilter){
        this.pullFilter = pullFilter;
    }

    @Override
    public T read() {
        return pullFilter.read();
    }
}
