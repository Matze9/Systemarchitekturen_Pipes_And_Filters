package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullPerspectiveProjectionFilter implements PullFilter<Pair<Face, Color>, Pair<Face, Color>>{


    PipelineData pd;
    PullPipe<Pair<Face, Color>> incomingPipe;

    public PullPerspectiveProjectionFilter(PipelineData pd){
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {


        Pair<Face, Color> data = incomingPipe.read();


        if (data != null) {

            Face face = data.fst();

            if (face != null && face.getV1().equals(Vec4.VEC4_ZERO)){

                return data;
            }else if(face != null) {

                Mat4 projectionTransform = pd.getProjTransform();
                Face projectedFace = new Face(
                        projectionTransform.multiply(face.getV1()),
                        projectionTransform.multiply(face.getV2()),
                        projectionTransform.multiply(face.getV3()),
                        projectionTransform.multiply(face.getN1()),
                        projectionTransform.multiply(face.getN2()),
                        projectionTransform.multiply(face.getN3())
                );


                return (new Pair(projectedFace, data.snd()));
            }
        }
        return null;
    }

    @Override
    public void setIncomingPipe(PullPipe<Pair<Face, Color>> pipe) {
        this.incomingPipe = pipe;

    }
}
