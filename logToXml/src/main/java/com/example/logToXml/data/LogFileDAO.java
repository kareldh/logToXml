package com.example.logToXml.data;

import com.example.logToXml.model.Rendering;
import org.springframework.stereotype.Component;

import java.util.*;

public class LogFileDAO {
    /**
     * maps the document id concatenated with "-" and the page number on the rendering object
     */
    private Map<String,Rendering> docToRendering;

    /**
     * maps the thread id on the document id, concatenated with "-" and the page number
     */
    private Map<Integer,String> threadToDoc;

    /**
     * maps unique return id on a set of thread id's (because duplicate renderings are possible)
     */
    private Map<String,Set<Integer>> returnToThread;

    /**
     * counts the startRendering results without startRendering
     */
    private int resultWithoutStart;

    /**
     * counts the getRenderings without startRendering
     */
    private int getWithoutStart;

    public int getResultWithoutStart() {
        return resultWithoutStart;
    }

    public int getGetWithoutStart() {
        return getWithoutStart;
    }

    public int getAmountOfDoubleRenderings(){
        int amount = 0;
        for (String uniqueId:returnToThread.keySet()) {
            if(returnToThread.get(uniqueId).size()>1){
                amount++;
            }
        }
        int test = (int) returnToThread.keySet().stream().filter(s -> returnToThread.get(s).size()>0).count();
        return amount;
    }

    public int getAmountOfStartRenderingsWithoutGet(){
        return (int) docToRendering.values().stream().filter(rendering -> rendering.getGets().isEmpty()).count();
    }

    public LogFileDAO(){
        docToRendering = new HashMap<>();
        threadToDoc = new HashMap<>();
        returnToThread = new HashMap<>();
        resultWithoutStart = 0;
        getWithoutStart = 0;
    }

    public Set<Rendering> getRenderings(){
        return new HashSet<>(docToRendering.values());
    }

    public void addStartRendering(int docId,int page,int threadId, Date date){
        if(!docToRendering.containsKey(docId+"-"+page)){
            Rendering r = new Rendering();
            r.setDocument(docId);
            r.addStartRendering(date);
            docToRendering.put(docId+"-"+page,r);
            threadToDoc.put(threadId,docId+"-"+page);
        }
        else{
            docToRendering.get(docId+"-"+page).addStartRendering(date);
        }
    }

    public void addGetRendering(String uniqueId, Date date){
        if(returnToThread.containsKey(uniqueId)) {
            for (int threadId:returnToThread.get(uniqueId)) {
                if(threadToDoc.containsKey(threadId) && docToRendering.containsKey(threadToDoc.get(threadId))){
                    docToRendering.get(threadToDoc.get(threadId)).addGetRendering(date);
                    docToRendering.get(threadToDoc.get(threadId)).setUid(uniqueId);
                }
            }
        }
        else{
            getWithoutStart++;
        }
    }

    public void addUniqueReturnValue(String returnId, int threadId){
        if(!returnToThread.containsKey(returnId)){
            returnToThread.put(returnId,new HashSet<>());
        }
        returnToThread.get(returnId).add(threadId);
        if(threadToDoc.containsKey(threadId)){
            docToRendering.get(threadToDoc.get(threadId)).setUid(returnId);
        }
        else{
            resultWithoutStart++;
        }
    }
}
