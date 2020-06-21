package Model.Request;

import java.util.ArrayList;

public class GetArrayList extends GeneralRequest {
    private static final long serialVersionUID = 7L;
    ArrayList<String> arrayList;
    public GetArrayList(ArrayList<String> arrayList){
        this.arrayList = arrayList;
    }
    public ArrayList<String> getArrayList(){
        return arrayList;
    }
}
