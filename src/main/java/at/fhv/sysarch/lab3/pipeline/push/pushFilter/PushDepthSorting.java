package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import com.hackoeur.jglm.Vec4;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class PushDepthSorting implements PushFilter<Face, Face>{


    PushPipe<Face> outgoingPipe;

    LinkedList<Face> faces = new LinkedList<>();

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
    public void write(Face data) {

        if (!data.getV1().equals(Vec4.VEC4_ZERO)){
            faces.add(data);
        } else {
            Collections.sort(faces, comparator);
            faces.forEach(face -> {
                outgoingPipe.write(face);
            });
            faces = new LinkedList<>();
        }

    }

    @Override
    public void setOutgoingPipe(PushPipe<Face> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }
}
