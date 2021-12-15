package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import com.hackoeur.jglm.Vec4;

public class Source implements PullFilter <Face, Face>{

    private Model model;
    private int index;

//    public Source (Model model){
//        this.model = model;
//    }


    @Override
    public Face read() {
       if (index < model.getFaces().size()){
           Face face =  model.getFaces().get(index);
           index += 1;
           return face;
       }

        return new Face(Vec4.VEC4_ZERO, Vec4.VEC4_ZERO, Vec4.VEC4_ZERO, Vec4.VEC4_ZERO, Vec4.VEC4_ZERO, Vec4.VEC4_ZERO);
    }

    @Override
    public void setIncomingPipe(PullPipe<Face> pipe) {

    }


    public void setModel(Model model) {
        this.model = model;
        this.index = 0;
    }
}
