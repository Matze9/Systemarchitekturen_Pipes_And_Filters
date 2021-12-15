package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullScreenSpaceTransformation implements PullFilter<Pair<Face, Color>, Pair<Face, Color>>{

    PipelineData pd;
    PullPipe<Pair<Face, Color>> incomingPipe;

    public PullScreenSpaceTransformation(PipelineData pd){
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {



        Pair<Face, Color> data = incomingPipe.read();

        if(data != null) {


            Face face = data.fst();

            if (face != null && face.getV1().equals(Vec4.VEC4_ZERO)){
                System.out.println("Screenspacetransformation returns END FACE Pair + face");
                return data;
            } else if (face != null) {

                //Divide with W - Can only use Multiply, so multiply with 1/W
                Face newFace = new Face(
                        face.getV1().multiply((1 / face.getV1().getW())),
                        face.getV2().multiply((1 / face.getV2().getW())),
                        face.getV3().multiply((1 / face.getV3().getW())),
                        face.getN1().multiply((1 / face.getN1().getW())),
                        face.getN2().multiply((1 / face.getN2().getW())),
                        face.getN3().multiply((1 / face.getN3().getW()))
                );


                return (new Pair<Face, Color>(new Face(pd.getViewportTransform().multiply(newFace.getV1()),
                        pd.getViewportTransform().multiply(newFace.getV2()),
                        pd.getViewportTransform().multiply(newFace.getV3()),
                        pd.getViewportTransform().multiply(newFace.getN1()),
                        pd.getViewportTransform().multiply(newFace.getN2()),
                        pd.getViewportTransform().multiply(newFace.getN3())), data.snd()

                ));
            }
        }
        return null;
    }

    @Override
    public void setIncomingPipe(PullPipe<Pair<Face, Color>> pipe) {
        this.incomingPipe = pipe;

    }
}
