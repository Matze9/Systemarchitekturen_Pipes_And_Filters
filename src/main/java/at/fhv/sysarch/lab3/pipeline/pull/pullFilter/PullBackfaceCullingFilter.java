package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import com.hackoeur.jglm.Vec4;

public class PullBackfaceCullingFilter implements PullFilter<Face, Face>{

    PullPipe<Face> incomingPipe;

    @Override
    public Face read() {

        Face face = incomingPipe.read();

        if (face.getV1().equals(Vec4.VEC4_ZERO)){

            return face;
        }

        if(face != null) {

            if (face.getV1().dot(face.getN1()) < 0) {

                return face;
            }
        }
        return null;
    }

    @Override
    public void setIncomingPipe(PullPipe pipe) {
        this.incomingPipe = pipe;
    }
}
