package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import com.hackoeur.jglm.Vec4;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class PullDepthSortingFilter implements PullFilter<Face, Face>{

    private LinkedList<Face> faces = new LinkedList<>();
    private boolean loadFaces = true;
    private PullPipe<Face> incomingPipe;

    private Comparator<Face> comparator = new Comparator<Face>() {

        @Override
        public int compare(Face f1, Face f2) {
            float f1Z = (f1.getV1().getZ() + f1.getV2().getZ() + f1.getV3().getZ()) / 3;
            float f2Z = (f2.getV1().getZ() + f2.getV2().getZ() + f2.getV3().getZ()) / 3;

            if (f1Z > f2Z){
                return 1;
            } else {
                return -1;
            }
        }
    };


    @Override
    public Face read() {

        while (loadFaces){
            Face face = incomingPipe.read();
            if(face != null && face.getV1().equals(Vec4.VEC4_ZERO)){
                loadFaces = false;
            }
            if (face != null){
                faces.add(face);
                Collections.sort(faces, comparator);
            }
        }

        if (faces.size() <= 1) {
            loadFaces = true;
        }
        return faces.poll();

    }

    @Override
    public void setIncomingPipe(PullPipe<Face> pipe) {
        this.incomingPipe = pipe;

    }
}
