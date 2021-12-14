package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import javafx.scene.paint.Color;


public class PushColorFilter implements PushFilter<Face, Pair<Face, Color>>{

    private PushPipe <Pair<Face, Color>> outgoingPipe;
    private PipelineData pd;

    public PushColorFilter (PipelineData pd){
        this.pd = pd;
    }

    @Override
    public void write(Face data) {

        outgoingPipe.write(new Pair<Face, Color>(data, pd.getModelColor()));

    }

    @Override
    public void setOutgoingPipe(PushPipe<Pair<Face, Color>> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }
}
