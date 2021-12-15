package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import javafx.scene.paint.Color;

public class PullColorFilter implements PullFilter<Pair<Face, Color>, Face>{

    PipelineData pd;
    PullPipe<Face> incomingPipe;

    public PullColorFilter (PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {

        Face face = incomingPipe.read();

        if (face != null) {

            return (new Pair<Face, Color>(face, pd.getModelColor()));
        } else {
            return null;
        }

    }

    @Override
    public void setIncomingPipe(PullPipe<Face> pipe) {
        this.incomingPipe = pipe;

    }
}
