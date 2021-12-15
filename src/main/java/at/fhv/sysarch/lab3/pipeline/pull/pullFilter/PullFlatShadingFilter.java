package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullFlatShadingFilter implements PullFilter<Pair<Face, Color>, Pair<Face, Color>>{

    PipelineData pd;
    PullPipe<Pair<Face, Color>> incomingPipe;

    public PullFlatShadingFilter (PipelineData pd){
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {

        Pair<Face, Color> data = incomingPipe.read();

        if (data != null) {

            Face face = data.fst();

            if (face != null && face.getV1().equals(Vec4.VEC4_ZERO)){

                return data;
            } else if (face != null) {

                float dotProduct = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());
                if (dotProduct <= 0) {

                    return (new Pair<>(face, Color.BLACK));
                }

//        outgoingPipe.write(new Pair<>(face, data.snd().deriveColor(0, 1, dotProduct, 1)));
                return (new Pair<>(face, data.snd().deriveColor(0, 1, dotProduct, 1)));
            }
        }
        return null;

    }

    @Override
    public void setIncomingPipe(PullPipe<Pair<Face, Color>> pipe) {
        this.incomingPipe = pipe;
    }
}
