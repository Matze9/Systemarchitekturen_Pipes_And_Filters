package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import javafx.scene.paint.Color;

public class PushFlatShadingFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>>{

    private PushPipe <Pair<Face, Color>> outgoingPipe;
    private PipelineData pd;

    public PushFlatShadingFilter (PipelineData pd){
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> data) {

        Face face = data.fst();

        float dotProduct = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());
        if (dotProduct <= 0) {
            outgoingPipe.write(new Pair<>(face, Color.BLACK));
        }

//        outgoingPipe.write(new Pair<>(face, data.snd().deriveColor(0, 1, dotProduct, 1)));
        outgoingPipe.write(new Pair<>(face, data.snd().deriveColor(0, 1, dotProduct, 1)));

    }

    @Override
    public void setOutgoingPipe(PushPipe<Pair<Face, Color>> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;

    }
}
